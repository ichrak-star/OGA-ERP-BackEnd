package com.onegateafrica;

import com.onegateafrica.Entity.UserEntity;
import com.onegateafrica.dto.FileStorageProperties;
import com.onegateafrica.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);


	}

	@Autowired
	UserService userService;

	@PostConstruct
	public String initRh(){


		UserEntity rh = new UserEntity();
		rh.setId(1);
		rh.setEmail("mouihbi123");
		rh.setRole("Agent");
		rh.setDepartement("Rh");
		rh.setPassword("mouihbi");
		rh.setTelephone("21614264");
		rh.setUserName(rh.getEmail());

		rh.getUserName();
		UserEntity userExist = userService.getUserByUserName(rh.getUserName());


		if (userExist==null){
			userService.addUser(rh);

			return "new user add to database";
		}
		else return "this user already exist";


	}



}
