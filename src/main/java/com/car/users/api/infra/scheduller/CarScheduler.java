package com.car.users.api.infra.scheduller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.car.users.api.service.ICarService;

@Component
public class CarScheduler {

    private ICarService carService;
    
    public CarScheduler(ICarService carService) {
		super();
		this.carService = carService;
	}

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledDeleteCars() {
        carService.delete();
    }
}
