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

package org.gridgain.demo.springdata.service;

import java.util.ArrayList;
import java.util.List;
import javax.cache.Cache;
import javax.cache.Cache.Entry;

import org.gridgain.demo.springdata.dao.CityRepository;
import org.gridgain.demo.springdata.dao.CountryRepository;
import org.gridgain.demo.springdata.model.City;
import org.gridgain.demo.springdata.model.CityKey;
import org.gridgain.demo.springdata.model.Country;
import org.gridgain.demo.springdata.model.CityDTO;
import org.gridgain.demo.springdata.model.CountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorldDatabaseService {
    @Autowired CountryRepository countryDao;

    @Autowired CityRepository cityDao;

    public List<CountryDTO> getCountriesByPopulation(int population) {
    	
    	
        List<CountryDTO> countries = new ArrayList<>();

        for (Cache.Entry<String, Country> entry: countryDao.findByPopulationGreaterThanEqualOrderByPopulationDesc(population))
            countries.add(new CountryDTO(entry.getKey(), entry.getValue()));

        return countries;
    }

    

    
    public List<CityDTO> getCitiesByPopulation(int population) {
        List<CityDTO> cities = new ArrayList<>();

        for (Cache.Entry<CityKey, City> entry: cityDao.findAllByPopulationGreaterThanEqualOrderByPopulation(population))
            cities.add(new CityDTO(entry.getKey(), entry.getValue()));

        return cities;
    }

    public List<List<?>> getMostPopulatedCities(Integer limit) {
        List<List<?>> entries = cityDao.findMostPopulatedCities(limit == null ? 5 : limit);

        return entries;
    }

    public CityDTO updateCityPopulation(int cityId, int population) {
        Cache.Entry<CityKey, City> entry = cityDao.findById(cityId);

        entry.getValue().setPopulation(population);

        cityDao.save(entry.getKey(), entry.getValue());

        return new CityDTO(entry.getKey(), entry.getValue());
    }
    
    
  
    
    public City findCityById(int cityId) {
    	 Cache.Entry<CityKey, City> entry = cityDao.findById(cityId);
    	 if(entry != null) 
    		 return entry.getValue();
    	 return new City();
    }
    
    public List<Entry<CityKey, City>> findAllCities(int limit) {
    	//System.out.println("######################city = " + cityDao.findById(id));
    	return cityDao.findAllCities(limit);
    }

    
    public void deleteCountryByCode(String code) {
    	String countryCode = ""+code+"";
    	try {
    		countryDao.deleteByCode2(countryCode);
    		
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return;
		}
		
		
    }
    
    
    
	public Country findCountryByCode(String code) {
			
			
		String countryCode = ""+code+"";
		Entry<String, Country> entry = countryDao.findByCode(countryCode);
		
				//countryDao.findByCode2(code)
				//countryDao.findByCode(code)
				//countryDao.findByCode("ARE")
				//countryDao.findByName("Anguilla")
		
			Country  country = new Country();
			if(entry!=null) {
				country=  entry.getValue();	
			}
		 
	        return country;
		
	}

	public List<Country> findAllCountries(Integer limit) {
		 List<Country> countries = new ArrayList<>();

	        for (Cache.Entry<String, Country> entry: countryDao.findAllCountries(limit))
	            countries.add(entry.getValue());

	        
	        
	        return countries;
	}


	public Country addCountry(Country c) {
		return countryDao.save(c.getCode2(), c);
		
	}
	
	public Country updateCountryPopulation(Country c) {
		Cache.Entry<String, Country> entry = null;
		try {
			entry = countryDao.updatePopulationByCode2(c.getPopulation(), c.getCode2());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	return null;
		
	}
	

	public City addCity(CityDTO c) {
		City cityObj =  new City();
		cityObj.setDistrict(c.getDistrict());
		cityObj.setName(c.getName());
		cityObj.setPopulation(c.getPopulation());
	
		return cityDao.save(new CityKey(c.getId(), c.getCountryCode()), cityObj);
		
		
		
	}


	
	public City updateCity(CityDTO c) {
		City cityObj =  new City();
		cityObj.setDistrict(c.getDistrict());
		cityObj.setName(c.getName());
		cityObj.setPopulation(c.getPopulation());
	
		return cityDao.save(new CityKey(c.getId(), c.getCountryCode()), cityObj);
		
	}


	public void deleteCity(int id, String countryCode) {
		CityKey c =  new CityKey(id, countryCode);
		 cityDao.deleteById(c);
		
	}
}
