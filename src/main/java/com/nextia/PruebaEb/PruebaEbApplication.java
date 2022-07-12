package com.nextia.PruebaEb;

import com.nextia.PruebaEb.Business.Interfaces.FilesBusiness;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class PruebaEbApplication implements CommandLineRunner {
	@Resource
	FilesBusiness filesBusiness;
	public static void main(String[] args) {
		SpringApplication.run(PruebaEbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		filesBusiness.createDirectory();
	}
}
