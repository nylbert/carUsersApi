package com.car.users.api.domain.dto;

public record CarDTO(
		Integer id,
        Integer year,
        String licensePlate,
        String model,
        String color
) {}
