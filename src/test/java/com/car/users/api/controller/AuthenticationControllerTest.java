package com.car.users.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import com.car.users.api.domain.dto.AuthenticationDTO;
import com.car.users.api.domain.dto.LoginResponseDTO;
import com.car.users.api.domain.model.User;
import com.car.users.api.infra.security.JwtTokenService;
import com.car.users.api.service.IUserService;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private IUserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private AuthenticationDTO authenticationDTO;
    private User user;

    @BeforeEach
    void setUp() {
        authenticationDTO = new AuthenticationDTO("user", "password");
        user = new User();
        user.setLogin("user");
    }
    
    @Test
    void login_Success() {
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(user);
        when(jwtTokenService.generateToken(any(User.class))).thenReturn("token");
        doNothing().when(userService).update(any(User.class));

        ResponseEntity<LoginResponseDTO> response = authenticationController.login(authenticationDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("token", response.getBody().token());
        assertEquals(user.getUsername(), response.getBody().loggedUser());
    }

}
