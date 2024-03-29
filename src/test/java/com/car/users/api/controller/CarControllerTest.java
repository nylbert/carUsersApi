package com.car.users.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.model.User;
import com.car.users.api.service.ICarService;

class CarControllerTest {

    @Mock
    private ICarService carService;

    @InjectMocks
    private CarController carController;

    private CarDTO carDTO;
    private Integer userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = 1;

        carDTO = new CarDTO();
        carDTO.setId(1);
        carDTO.setYear(2020);
        carDTO.setLicensePlate("XYZ1234");
        carDTO.setModel("Test Model");
        carDTO.setColor("Blue");
        
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        User user = new User();
        user.setId(userId);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
    }
    
    @Test
    void listCars_Success() {
        when(carService.find(anyInt())).thenReturn(Arrays.asList(carDTO));

        ResponseEntity<List<CarDTO>> response = carController.listCars();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
    
    @Test
    void createCar_Success() {
        when(carService.insert(any(CarDTO.class), anyInt())).thenReturn(carDTO);

        ResponseEntity<CarDTO> response = carController.createCar(carDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void listCarsById_Success() {
        when(carService.find(anyInt(), anyInt())).thenReturn(carDTO);

        ResponseEntity<CarDTO> response = carController.listCarsById(carDTO.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteCarsById_Success() {
        ResponseEntity<String> response = carController.deleteCarsById(carDTO.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateCarById_Success() {
        when(carService.update(anyInt(), anyInt(), any(CarDTO.class))).thenReturn(carDTO);

        ResponseEntity<CarDTO> response = carController.updateCarById(carDTO.getId(), carDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void uploadCarImage_Success() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", "<<jpeg data>>".getBytes());
        
        doNothing().when(carService).updateCarImage(userId, imageFile);

        ResponseEntity<?> response = carController.uploadCarImage(1, imageFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(carService, times(1)).updateCarImage(1, imageFile);
    }

    @Test
    void uploadCarImage_WhenIOException_ShouldReturnBadRequest() throws Exception {
        MockMultipartFile mockImageFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes());
        
        doThrow(IOException.class).when(carService).updateCarImage(any(Integer.class), any(MultipartFile.class));

        ResponseEntity<?> response = carController.uploadCarImage(1, mockImageFile);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Could not upload image", response.getBody());
    }
}

