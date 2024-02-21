package com.car.users.api.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.model.Car;
import com.car.users.api.infra.exception.DuplicatedFieldException;
import com.car.users.api.infra.exception.InvalidFieldException;
import com.car.users.api.infra.exception.RequiredFieldException;
import com.car.users.api.repository.CarRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private CarDTO carDTO;
    private Car car;

    @BeforeEach
    void setUp() {
        carDTO = new CarDTO(1, 2020, "ABC-1234", "Black", "Model S", null);
        car = new Car(carDTO.getId(), carDTO.getYear(), carDTO.getLicensePlate(), carDTO.getModel(), carDTO.getColor(), 1, null);
    }
    
    @Test
    void insertCar_Success() {
        when(carRepository.countByLicensePlate(anyString())).thenReturn(0L);
        when(carRepository.save(any(Car.class))).thenReturn(car);

        CarDTO result = carService.insert(carDTO, 1);

        assertNotNull(result);
        assertEquals(carDTO.getLicensePlate(), result.getLicensePlate());
        verify(carRepository).save(any(Car.class));
    }

    @Test
    void findCarsByUserId_ReturnsEmptyListWhenNoCars() {
        when(carRepository.findByUserId(anyInt())).thenReturn(new ArrayList<>());

        List<CarDTO> result = carService.find(1);

        assertTrue(result.isEmpty());
    }

    @Test
    void findCarByIdAndUserId_Success() {
        when(carRepository.findByIdAndUserId(carDTO.getId(), 1)).thenReturn(car);

        CarDTO result = carService.find(carDTO.getId(), 1);

        assertNotNull(result);
        assertEquals(carDTO.getLicensePlate(), result.getLicensePlate());
    }

    @Test
    void updateCar_Success() {
        when(carRepository.findByIdAndUserId(carDTO.getId(), 1)).thenReturn(car);
        when(carRepository.countByLicensePlateAndIdNot(anyString(), anyInt())).thenReturn(0L);
        when(carRepository.save(any(Car.class))).thenReturn(car);

        CarDTO result = carService.update(carDTO.getId(), 1, carDTO);

        assertNotNull(result);
        assertEquals(carDTO.getModel(), result.getModel());
        verify(carRepository).save(any(Car.class));
    }
    
    @Test
    void deleteCarByIdAndUserId_Success() {
        when(carRepository.findByIdAndUserId(carDTO.getId(), 1)).thenReturn(car);
        doNothing().when(carRepository).delete(car);

        assertDoesNotThrow(() -> carService.delete(carDTO.getId(), 1));
        verify(carRepository).delete(car);
    }
    
    @Test
    void deleteCarUserId_Success() {
        when(carRepository.findByUserId(1)).thenReturn(List.of(car));
        doNothing().when(carRepository).delete(car);
        assertDoesNotThrow(() -> carService.delete(1));
        verify(carRepository).delete(car);
    }

    @Test
    void insertCar_ThrowsRequiredFieldException() {
        carDTO = new CarDTO();

        assertThrows(RequiredFieldException.class, () -> carService.insert(carDTO, 1));
    }

    @Test
    void insertCar_ThrowsDuplicatedFieldException() {
        when(carRepository.countByLicensePlate(carDTO.getLicensePlate())).thenReturn(1L);

        assertThrows(DuplicatedFieldException.class, () -> carService.insert(carDTO, 1));
    }

    @Test
    void insertCar_ThrowsInvalidFieldException() {
        carDTO.setLicensePlate("XXX1234");
        carDTO.setYear(12);

        assertThrows(InvalidFieldException.class, () -> carService.insert(carDTO, 1));
    }

    @Test
    void updateCarImage_ShouldCallSaveWithUpdatedImage() throws IOException {
        Integer carId = 1;
        byte[] imageData = "fake image data".getBytes();
        MultipartFile imageFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", imageData);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        carService.updateCarImage(carId, imageFile);

        verify(carRepository, times(1)).save(any(Car.class));
    }
    
    @Test
    void updateCarImage_ThrowsEntityNotFoundException() throws IOException {
        Integer carId = 1;
        byte[] imageData = "fake image data".getBytes();
        MultipartFile imageFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", imageData);

        when(carRepository.findById(carId)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> carService.updateCarImage(carId, imageFile));
    }
    
    @Test
    void deleteCars_ShouldInvokeDeleteAllOnRepository() {
        carService.delete();
        
        verify(carRepository, times(1)).deleteAll();
    }
}

