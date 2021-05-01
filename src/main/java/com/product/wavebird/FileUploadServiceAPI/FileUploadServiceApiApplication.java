package com.product.wavebird.FileUploadServiceAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class FileUploadServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploadServiceApiApplication.class, args);
	}

}
