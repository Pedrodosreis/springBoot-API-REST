package com.pedrodosreis.restrecipes;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends CrudRepository<Person, Long> {

	List<Person> findByName(String name);
	
	List<Person> findByAge(int age);

}
