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

package org.gridgain.demo.springdata.dao;

import java.util.List;
import javax.cache.Cache;
import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.Query;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;
import org.gridgain.demo.springdata.model.City;
import org.gridgain.demo.springdata.model.CityKey;
import org.gridgain.demo.springdata.model.Country;
import org.springframework.stereotype.Repository;

@RepositoryConfig (cacheName = "Country")
@Repository
public interface CountryRepository extends IgniteRepository<Country, String> {

    public List<Cache.Entry<String,Country>> findByPopulationGreaterThanEqualOrderByPopulationDesc(int population);
    
    
    //@Query("SELECT * FROM Country WHERE code2 = ?")
    public Cache.Entry<String,Country> findByCode2(String code);
    
    
    @Query("SELECT * FROM Country WHERE code2 = ?")
    public Cache.Entry<String,Country> findByCode(String code);
    
    public Cache.Entry<String,Country> findByName(String name);

    
    @Query("UPDATE Country SET population = ? WHERE code2 = ?")
    public Cache.Entry<String,Country> updatePopulationByCode2(Integer population, String code);
    
    @Query("DELETE FROM Country WHERE code2 = ?")
    public Cache.Entry<String,Country> deleteByCode2(String code);
    
    @Query("SELECT * FROM Country LIMIT ? ")
    public List<Cache.Entry<String,Country>> findAllCountries(int limit);
}
