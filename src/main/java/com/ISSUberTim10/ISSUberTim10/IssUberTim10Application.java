package com.ISSUberTim10.ISSUberTim10;

import com.ISSUberTim10.ISSUberTim10.controller.AppUserController;
import com.ISSUberTim10.ISSUberTim10.domain.Driver;
import com.ISSUberTim10.ISSUberTim10.domain.Panic;
import com.ISSUberTim10.ISSUberTim10.domain.Passenger;
import com.ISSUberTim10.ISSUberTim10.domain.Ride;
import com.ISSUberTim10.ISSUberTim10.dto.RideDTO;
import com.ISSUberTim10.ISSUberTim10.repository.AppUserRepository;
import com.ISSUberTim10.ISSUberTim10.service.interfaces.IPanicService;
import com.ISSUberTim10.ISSUberTim10.service.interfaces.PanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class IssUberTim10Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IssUberTim10Application.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

	}
}
