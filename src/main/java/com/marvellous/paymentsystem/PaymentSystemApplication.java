package com.marvellous.paymentsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan("com.*")
//@EnableJpaRepositories("com.*")
//@ComponentScan(basePackages = { "com.*" })
public class PaymentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentSystemApplication.class, args);
	}

}
