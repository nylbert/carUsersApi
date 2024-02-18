package com.car.users.api.domain.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record LoggedUserDTO (
        String firstName,
        String lastName,
        String email,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "UTC") Date birthday,
        String login,
        String password,
        String phone,
        List<CarDTO> cars,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "UTC") LocalDate createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "UTC") LocalDate lastLogin
) {}