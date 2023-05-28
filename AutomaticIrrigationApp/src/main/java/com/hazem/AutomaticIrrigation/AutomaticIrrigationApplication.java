package com.hazem.AutomaticIrrigation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
public class AutomaticIrrigationApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Africa/Cairo"));
		SpringApplication.run(AutomaticIrrigationApplication.class, args);

	}

}
