package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.demo")
@EntityScan(basePackages = "com.example.demo")

public class Demo1Application {
@Autowired
ZamowienieRepository zamowienieRepository;
	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
		//zamowienieRepository.save(new Zamowienie(1,"a","b",1))

		//Connect db = new Connect();
		//db.connectDB();
		//db.closeconnection();
	//	Zamowienie zamowienie = new Zamowienie(1,"a","b",1);


	}

}
