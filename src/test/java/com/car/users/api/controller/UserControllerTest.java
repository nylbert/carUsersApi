package com.car.users.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.dto.UserDTO;
import com.car.users.api.infra.exception.DuplicatedFieldException;
import com.car.users.api.infra.exception.InvalidFieldException;
import com.car.users.api.infra.exception.RequiredFieldException;
import com.car.users.api.service.IUserService;

class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;
    private CarDTO carDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        carDTO = new CarDTO();
        carDTO.setId(1);
        carDTO.setYear(2020);
        carDTO.setLicensePlate("XYZ1234");
        carDTO.setModel("Test Model");
        carDTO.setColor("Blue");

        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setBirthday(new Date());
        userDTO.setLogin("johnDoe");
        userDTO.setPassword("password");
        userDTO.setPhone("1234567890");
        userDTO.setCars(Arrays.asList(carDTO));
    }
    
    @Test
    void listUsers_Success() {
        when(userService.find()).thenReturn(Arrays.asList(userDTO));

        ResponseEntity<List<UserDTO>> response = userController.listUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(userDTO.getEmail(), response.getBody().get(0).getEmail());
    }
    
    @Test
    void createUser_Success() {
        when(userService.insert(any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void listById_Success() {
        when(userService.find(anyInt())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.listById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDTO.getId(), response.getBody().getId());
    }

    @Test
    void updateUser_Success() {
        when(userService.update(anyInt(), any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.updateUser(1, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDTO.getEmail(), response.getBody().getEmail());
    }
    
    @Test
    void createUser_DuplicatedField() {
        when(userService.insert(any(UserDTO.class))).thenThrow(new DuplicatedFieldException("email"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userController.createUser(new UserDTO());
        });

        assertTrue(exception instanceof DuplicatedFieldException);
    }
    
    @Test
    void deleteById_Success() {
        doNothing().when(userService).delete(anyInt());

        ResponseEntity<?> response = userController.deleteById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).delete(1);
    }

    @Test
    void updateUser_DuplicatedField() {
        when(userService.update(anyInt(), any(UserDTO.class))).thenThrow(new DuplicatedFieldException("login"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userController.updateUser(1, new UserDTO());
        });

        assertTrue(exception instanceof DuplicatedFieldException);
    }

    @Test
    void createUser_InvalidField() {
        when(userService.insert(any(UserDTO.class))).thenThrow(new InvalidFieldException(Arrays.asList("email", "phone")));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userController.createUser(new UserDTO());
        });

        assertTrue(exception instanceof InvalidFieldException);
    }

    @Test
    void updateUser_InvalidField() {
        when(userService.update(anyInt(), any(UserDTO.class))).thenThrow(new InvalidFieldException(Arrays.asList("firstName")));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userController.updateUser(1, new UserDTO());
        });

        assertTrue(exception instanceof InvalidFieldException);
    }
    
    @Test
    void createUser_RequiredField() {
        when(userService.insert(any(UserDTO.class))).thenThrow(new RequiredFieldException(Arrays.asList("firstName", "lastName")));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userController.createUser(new UserDTO());
        });

        assertTrue(exception instanceof RequiredFieldException);
    }

    @Test
    void updateUser_RequiredField() {
        when(userService.update(anyInt(), any(UserDTO.class))).thenThrow(new RequiredFieldException(Arrays.asList("email")));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userController.updateUser(1, new UserDTO());
        });

        assertTrue(exception instanceof RequiredFieldException);
    }
    
    @Test
    void uploadUserImage_Success() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", "<<jpeg data>>".getBytes());
        
        doNothing().when(userService).updateUserImage(anyInt(), any(MultipartFile.class));

        ResponseEntity<?> response = userController.uploadUserImage(1, imageFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).updateUserImage(1, imageFile);
    }

    @Test
    void uploadUserImage_WhenIOException_ShouldReturnBadRequest() throws Exception {
        MockMultipartFile mockImageFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes());
        
        doThrow(IOException.class).when(userService).updateUserImage(any(Integer.class), any(MultipartFile.class));

        ResponseEntity<?> response = userController.uploadUserImage(1, mockImageFile);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Could not upload image", response.getBody());
    }
}

