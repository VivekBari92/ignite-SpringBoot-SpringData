/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.gridgain.demo.springdata.controller;

import java.net.URI;
import java.util.List;

import javax.cache.Cache;
import javax.cache.Cache.Entry;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.configuration.CacheConfiguration;
import org.gridgain.demo.springdata.DemoConfig;
import org.gridgain.demo.springdata.model.City;
import org.gridgain.demo.springdata.model.CityDTO;
import org.gridgain.demo.springdata.model.CityKey;
import org.gridgain.demo.springdata.model.Country;
import org.gridgain.demo.springdata.model.CountryDTO;
import org.gridgain.demo.springdata.service.WorldDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class WorldDatabaseController {
    @Autowired WorldDatabaseService service;
    @Autowired Ignite igniteInstance;

    
    @PostMapping("api/city")
    public ResponseEntity<Country> addCity(@RequestBody CityDTO c){
    	service.addCity(c);
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(c.getName()).toUri();
		return ResponseEntity.created(location).build();
    }
    
    
    @PutMapping("api/city")
    public ResponseEntity<Country> updateCity(@RequestBody CityDTO c){
    	service.updateCity(c);
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand(c.getName()).toUri();
		return ResponseEntity.created(location).build();
    }
    
    
    @DeleteMapping("/api/city/{id}/{countryCode}")
    public ResponseEntity<Country> deleteCity(@PathVariable Integer id, String countryCode){
    	service.deleteCity(id, countryCode);
    	 return ResponseEntity.ok(null);
    }
    
    
    @PostMapping("api/country")
    public ResponseEntity<Country> addCountry(@RequestBody Country c){
    	service.addCountry(c);
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(c.getCode2()).toUri();
		return ResponseEntity.created(location).build();
    }
    
    @PutMapping("api/country")
    public ResponseEntity<Country> updateCountryPopulation(@RequestBody Country c){
    	service.updateCountryPopulation(c);
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(c.getCode2()).toUri();
		return ResponseEntity.ok(null);
    }
    
    @GetMapping("/api/countries")
    public List<CountryDTO> getCountriesByPopulation(@RequestParam (value = "population", required = true) int population) {
        return service.getCountriesByPopulation(population);
    }
    
    @GetMapping("/api/allCities")
    public List<Entry<CityKey, City>> getAllCities( @RequestParam (value = "limit", required = true) Integer limit){
    	
    	return service.findAllCities(limit);

    	}
    
    @GetMapping("/api/city/{id}")
    public City getCityByd(@PathVariable Integer id){
    	
    	return service.findCityById(id);

    	}      
   
    @GetMapping("/api/allCountries")
    public List<Country>  getAllCountries( @RequestParam (value = "limit", required = true) Integer limit){
    	
    	return service.findAllCountries(limit);

    	}
    
    @GetMapping("/api/country/{code}")
    public Country getCountryByCode(@PathVariable String code){
    	
    	return service.findCountryByCode(code);

    	}  
    
    @DeleteMapping("/api/country/{code}")
    public ResponseEntity<Country> deleteCountryByCode(@PathVariable String code){
    	service.deleteCountryByCode(code);
    	
    	 return ResponseEntity.ok(null);
    }

    @GetMapping("/api/cities")
    public List<CityDTO> getCitiesByPopulation(@RequestParam (value = "population", required = true) int population) {
        return service.getCitiesByPopulation(population);
    }

    @GetMapping("/api/cities/mostPopulated")
    public List<List<?>> getMostPopulatedCities(@RequestParam (value = "limit", required = false) Integer limit) {
        return service.getMostPopulatedCities(limit);
    }

    @PutMapping("/api/cities/{id}")
    public CityDTO updateCityPopulation(@PathVariable Integer id, @RequestBody CityDTO cityDTO) {
        return service.updateCityPopulation(id, cityDTO.getPopulation());
    }

}
