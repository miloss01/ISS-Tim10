package com.ISSUberTim10.ISSUberTim10;

import com.ISSUberTim10.ISSUberTim10.controller.AppUserController;
import com.ISSUberTim10.ISSUberTim10.domain.Driver;
import com.ISSUberTim10.ISSUberTim10.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IssUberTim10Application implements CommandLineRunner {

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private AppUserController appUserController;

	public static void main(String[] args) {
		SpringApplication.run(IssUberTim10Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Driver d1 = new Driver();
		//d1.setId(1L);
		d1.setName("d1");
		appUserRepository.save(d1);

		Driver d2 = new Driver();
		//d2.setId(2L);
		d2.setName("d2");
		appUserRepository.save(d2);

		System.out.println("----------------Ukucani driveri");
		System.out.println(d1.getId());
		System.out.println(d2.getId());

		appUserController.createAll();
		System.out.println(appUserController.getAll());


	}
}
