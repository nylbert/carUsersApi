package com.car.users.api.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.dto.UserDTO;
import com.car.users.api.domain.mapper.UserMapper;
import com.car.users.api.domain.model.User;
import com.car.users.api.infra.exception.DuplicatedFieldException;
import com.car.users.api.infra.exception.InvalidFieldException;
import com.car.users.api.infra.exception.RequiredFieldException;
import com.car.users.api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ICarService carService;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private User user;
    private CarDTO carDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO(1, "Teste", "Api", "teste.api@gmail.com",  new Date(), "testeApi123", "VerySecret123*", "81995565482", null);
        user = UserMapper.INSTANCE.userDtoToUser(userDTO);
        
        carDTO = new CarDTO();
        carDTO.setId(1);
        carDTO.setModel("Tesla Model S");
        
        userDTO.setCars(List.of(carDTO));
    }
    
    @Test
    void insertUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.insert(userDTO);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void insertUser_ThrowsDuplicatedEmailException() {
        when(userRepository.countByEmail(anyString())).thenReturn(1l);

        assertThrows(DuplicatedFieldException.class, () -> userService.insert(userDTO));
    }

    @Test
    void insertUser_ThrowsDuplicatedLoginException() {
        when(userRepository.countByEmail(anyString())).thenReturn(0l);
        when(userRepository.countByLogin(anyString())).thenReturn(1l);

        assertThrows(DuplicatedFieldException.class, () -> userService.insert(userDTO));
    }
    
    @Test
    void findUserByLogin_Success() {
        when(userRepository.findByLogin(user.getLogin())).thenReturn(user);

        User result = userService.find(user.getLogin());

        assertNotNull(result);
        assertEquals(user.getLogin(), result.getLogin());
    }

    @Test
    void findUserByLogin_NotFound() {
        when(userRepository.findByLogin(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.find("unknownLogin"));
    }

    @Test
    void updateUser_Success() {
    	user.setId(1);
    	
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.update(user.getId(), userDTO);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_NotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.update(999, userDTO));
    }

    @Test
    void deleteUser_Success() {
    	user.setId(1);
    	
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));

        assertDoesNotThrow(() -> userService.delete(user.getId()));
        verify(userRepository).delete(any(User.class));
    }
    
    @Test
    void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(carService.find(user.getId())).thenReturn(Arrays.asList(carDTO));

        List<UserDTO> result = userService.find();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(user.getLogin(), result.get(0).getLogin());
        assertEquals(1, result.get(0).getCars().size());
    }

    @Test
    void testFindUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(carService.find(user.getId())).thenReturn(Arrays.asList(carDTO));

        UserDTO result = userService.find(user.getId());

        assertNotNull(result);
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(1, result.getCars().size());
    }

    @Test
    void testFindUserById_NotFound() {
        int userId = 999;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.find(userId));
    }

    @Test
    void insertUser_ThrowsRequiredException() {
    	userDTO = new UserDTO();

        assertThrows(RequiredFieldException.class, () -> userService.insert(userDTO));
    }
    
    @Test
    void insertUser_ThrowsInvalidFieldException() {
    	userDTO.setFirstName("123456789123456789123456789123456789123456789123456789123456");
    	userDTO.setLastName("123456789123456789123456789123456789123456789123456789123456");
    	userDTO.setEmail("testeEmail");
    	userDTO.setPhone("81 9997950800");
    	userDTO.setPassword("testePassoword");

        assertThrows(InvalidFieldException.class, () -> userService.insert(userDTO));
    }
    
    @Test
    void updateUser_CallsSaveOnRepository() {
        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        
        userService.update(user);

        verify(userRepository, times(1)).save(user);
    }
}


