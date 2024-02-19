package com.car.users.api.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.car.users.api.domain.dto.LoggedUserDTO;
import com.car.users.api.domain.dto.UserDTO;
import com.car.users.api.domain.model.User;
import com.car.users.api.service.IUserService;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private AccountController accountController;

    private User user;
    private UserDTO userDTO;
    private Integer userId;
    
    @BeforeEach
    void SecurityContext() {
        user = new User();
        userDTO = new UserDTO();
        userId = 1;

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        User user = new User();
        user.setId(userId);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
    }
    
    @Test
    void detailLoggedUser_Success() {
        when(userService.find(anyInt())).thenReturn(userDTO);

        ResponseEntity<LoggedUserDTO> response = accountController.detailLoggedUser();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getEmail(), response.getBody().email());
    }

}
