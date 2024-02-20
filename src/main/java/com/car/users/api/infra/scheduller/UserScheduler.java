package com.car.users.api.infra.scheduller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.car.users.api.service.IUserService;

@Component
public class UserScheduler {

    private IUserService userService;
    
    public UserScheduler(IUserService userService) {
		super();
		this.userService = userService;
	}

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledDeleteUsers() {
        userService.deleteUsers();
    }
}
