package com.countryrest.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.countryrest.demo.beans.Country;
@EnableJpaRepositories
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
