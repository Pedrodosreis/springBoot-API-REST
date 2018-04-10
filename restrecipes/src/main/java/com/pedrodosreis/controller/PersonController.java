package com.pedrodosreis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.pedrodosreis.restrecipes.Person;
import com.pedrodosreis.restrecipes.PersonRepository;

@RestController
public class PersonController {
	
	@Autowired
	PersonRepository repository;
		
	/**
	 * Retorna todos os resultados pela seguinte URL
	 * 
	 * http://localhost:8080/getallperson
	 * 
	 * 
	 */
	@RequestMapping("/person")
	public String getAllPeople() {		
		return new Gson().toJson(repository.findAll());
	}
	
	/**
	 * Retorna o resultado pela seguinte URL
	 * 
	 * http://localhost:8080/person/'name'
	 * 
	 */
	@RequestMapping("/person/{name}")
	public String getPeopleByName(@PathVariable("name") String name) {		
		return new Gson().toJson(repository.findByName(name));
	}
	
	/**
	 * Retorna o resultado pelo seguinte URL
	 * 
	 * http://localhost:8080/person?name='name'
	 * 
	 */
//	@RequestMapping("/person")
//	public String getRecipe(@RequestParam(value="name", defaultValue="World") String name) {		
//		return new Gson().toJson(repository.findByName(name));
//	}
	
	@RequestMapping(value="/person", method=RequestMethod.POST)
	public String addPerson(@RequestBody String string) {
		
		Person[] person = new Gson().fromJson(string, Person[].class);		
		repository.save(person[0]);
			
		return "";
	}
	
	@RequestMapping(value="/person", method=RequestMethod.PUT)
	public String updatePerson(@RequestBody String string) {
		
		Person[] person = new Gson().fromJson(string, Person[].class);		
		Person p = person[0];
		
		Optional<Person> oldPerson = repository.findById(p.getId());
		
		if(oldPerson.isPresent()) {
			oldPerson.get().setAge(p.getAge());
			oldPerson.get().setName(p.getName());
		}
		repository.save(oldPerson.get());
			
		return "";
	}
	
	/**
	 * remove uma pessoa atraves de uma requisição por REST
	 * 
	 * http://localhost:8080/person?name='name'
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/person/{name}", method=RequestMethod.DELETE)
	public String deletePerson(@PathVariable String name) {
		
		List<Person> person = repository.findByName(name);
		
		for (Person p : person) {
			repository.delete(p);
		}
		
		return "";
	}
	
	/**
	 * Remove todas as pessoas atraves de uma requisição DELETE de REST.
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value="/person", method=RequestMethod.DELETE)
	public String deleteAll() {
		
		Iterable<Person> person = repository.findAll();
		
		for (Person p : person) {
			repository.delete(p);
		}
		
		return "";
	}
	
	
	
	
}
