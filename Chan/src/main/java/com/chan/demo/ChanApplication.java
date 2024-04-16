package com.chan.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}) //데이터베이스 사용안할때 추가 (exclude={DataSourceAutoConfiguration.class})
public class ChanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChanApplication.class, args);
	}

}
