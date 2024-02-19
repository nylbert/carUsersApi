package com.car.users.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.car.users.api.domain.model.User;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private AuthorizationService authorizationService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("testeLogin");
        user.setPassword("password");
    }

    @Test
    void loadUserByUsername_ReturnsUserDetails() {
        when(userService.find("testeLogin")).thenReturn(user);

        UserDetails result = authorizationService.loadUserByUsername("testeLogin");

        assertNotNull(result);
        assertEquals("testeLogin", result.getUsername());
        assertEquals("password", result.getPassword());

        verify(userService).find("testeLogin");
    }

    @Test
    void loadUserByUsername_ThrowsExceptionWhenUserNotFound() {
        when(userService.find("unknownUser")).thenThrow(new UsernameNotFoundException("User not found"));

        assertThrows(UsernameNotFoundException.class, () -> authorizationService.loadUserByUsername("unknownUser"));

        verify(userService).find("unknownUser");
    }
}
