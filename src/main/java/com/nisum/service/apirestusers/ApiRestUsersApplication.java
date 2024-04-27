package com.nisum.service.apirestusers;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ApiRest Demo NISUM", description = "Esta api fue construida para guardar usuarios, segun el documento de reto expuesto"))
public class ApiRestUsersApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiRestUsersApplication.class, args);
	}

}
