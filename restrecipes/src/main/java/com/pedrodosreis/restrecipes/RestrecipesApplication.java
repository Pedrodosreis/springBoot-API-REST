package com.pedrodosreis.restrecipes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.pedrodosreis.controller.PersonController;

@SpringBootApplication
@ComponentScan(basePackageClasses={PersonRepository.class, PersonController.class})
public class RestrecipesApplication {
	
	private static final Logger log = LoggerFactory.getLogger(RestrecipesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RestrecipesApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(PersonRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Person("Jack", 23));
			repository.save(new Person("Chloe", 23));
			repository.save(new Person("Kim", 14));
			repository.save(new Person("David", 13));
			repository.save(new Person("Michelle", 28));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Person customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			repository.findById(1L)
				.ifPresent(customer -> {
					log.info("Customer found with findById(1L):");
					log.info("--------------------------------");
					log.info(customer.toString());
					log.info("");
				});

			// fetch customers by last name
			log.info("Customer found with findByAge('14'):");
			log.info("--------------------------------------------");
			repository.findByAge(14).forEach(bauer -> {
				log.info(bauer.toString());
			});
			
			for (Person people : repository.findByAge(23)) {
			 	log.info(people.toString());
			 }
			log.info("");
		};
	}
	
	
}
