package org.example.authservice;

import com.fasterxml.jackson.core.Base64Variant;
import org.example.authservice.entities.User;
import org.example.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthServiceApplication {



    public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}


}
