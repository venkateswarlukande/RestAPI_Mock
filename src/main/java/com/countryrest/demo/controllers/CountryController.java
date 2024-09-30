package com.countryrest.demo.controllers;


import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.countryrest.demo.beans.AddResponse;
import com.countryrest.demo.beans.Country;
import com.countryrest.demo.services.CountryService;

@Controller
@RestController
public class CountryController {
	
	@Autowired
	CountryService countryService;
	
	
	@GetMapping("/getcountries")
	public List<Country> getCountries(){
		return countryService.getAllCountries();
	}
	
//	@GetMapping("/getcountries/{id}")
//	public ResponseEntity<Country> getCountyById(@PathVariable(value="id") int id){
//		try {
//			Country country =  countryService.getCountryByID(id);
//			return new ResponseEntity<Country>(country,HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		
//	}
//	
	@GetMapping("/getcountries/{id}")
	public ResponseEntity<?> getCountryById(@PathVariable(value="id") int id) {
	   try {
	       Country country = countryService.getCountryByID(id);
	       if (country == null) {
	    	   
	    	   return new ResponseEntity<>("An error occurred while fetching the country", HttpStatus.INTERNAL_SERVER_ERROR);
	       }
	       return new ResponseEntity<>(country, HttpStatus.OK);
	       	
	   } catch (Exception e) {
		   return new ResponseEntity<>("Country not found with :"+id+"", HttpStatus.NOT_FOUND);
	   }
	}

	@GetMapping("/getcountries/countryname")
	public ResponseEntity<?> getCountyByName(@RequestParam(value="name") String countryName){
		try {
			Country countrybyname = countryService.getCountryByName(countryName);
			if (countrybyname == null) {
		    	   return new ResponseEntity<>("An error occurred while fetching the country", HttpStatus.INTERNAL_SERVER_ERROR);
		       }
			return new ResponseEntity<Country>(countrybyname,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("Not a valid country name :"+countryName+"", HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("/addcountry")
	//public Country addCountry(@RequestBody Country country) {
	public void addCountry(@RequestBody Country country) {	
		//return countryService.addCountry(country);
		 countryService.addCountry(country);
	}
	
	@PutMapping("/updatecountry/{id}")
	public ResponseEntity<?> updateCountry(@PathVariable(value="id") int id, @RequestBody Country country) {
		try {
			Country excistCountry = countryService.getCountryByID(id);
			if(excistCountry == null) {
				return new ResponseEntity<>("country is not null",HttpStatus.BAD_REQUEST);
			}
			excistCountry.setCountryName(country.getCountryName());
			excistCountry.setCountryCapital(country.getCountryCapital());
			
			Country updated_Country = countryService.updateCountry(excistCountry);
			return new ResponseEntity<Country>(updated_Country, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("your provided id: "+id+" is not available",HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@DeleteMapping("/deletecountry/{id}")
	public AddResponse deleteCountyById(@PathVariable(value="id") int id){
		return countryService.deleteCountry(id);
		
		
	}
	
	
	
}
