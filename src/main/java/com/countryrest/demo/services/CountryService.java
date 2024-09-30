package com.countryrest.demo.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.countryrest.demo.beans.AddResponse;
import com.countryrest.demo.beans.Country;
import com.countryrest.demo.repository.CountryRepository;

@Component
@Service

public class CountryService {
	
	@Autowired
	CountryRepository countryrep;
	
	public List<Country> getAllCountries() 
	{
		return countryrep.findAll();
	}
	
	public Country getCountryByID(int id) 
	{

		return countryrep.findById(id).get();
	}
	
	public Country getCountryByName(String countryName) throws Exception
	{
		Country country = null;
		try{
			List<Country> countries = countryrep.findAll();
			
			for(Country can:countries)
			{
				if(can.getCountryName().equalsIgnoreCase(countryName))
					country=can;
			}
			if(country == null) {
				throw new Exception();
			}
			return country;
		}catch (Exception e) {
			throw e;
		}
	
	}
	
	//public Country addCountry(Country country) 
	public void addCountry(Country country) 
	
	{
		country.setId(getMaxId());
		countryrep.save(country);
		//return country;
	}
	
	public int getMaxId() 
	{
		return countryrep.findAll().size()+1;
	}
	
	public Country updateCountry(Country country) 
	{
		
		countryrep.save(country);
		return country;
	}
	
	public AddResponse deleteCountry(int id) 
	{
		AddResponse res = new AddResponse();
		
		//if(countryrep.existsById(null))
		if (id == 0) { 
	        res.setMsg("Invalid request: Country ID cannot be null.");
	        return res;
	    }
	    if (!countryrep.existsById(id)) { 
	        res.setMsg("Country with ID " + id + " not found.");
	        return res;
	    }
	    try {
	        countryrep.deleteById(id);
	        res.setMsg("Country deleted successfully.");
	        res.setId(id);
	    } catch (Exception e) {
	        res.setMsg("Error deleting country: " + e.getMessage());
//			countryrep.deleteById(id);
//			res.setMsg("Country Deleted Succussfully.....");
//			res.setId(id);
//			 return res;
	    }
	    return res;
	}

}
