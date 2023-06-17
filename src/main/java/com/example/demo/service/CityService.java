package com.example.demo.service;

import com.example.demo.entity.City;
import com.example.demo.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    public City getById(Long id){
        return cityRepository.getCityById(id);
    }
    public List<City> getAll(){
        return cityRepository.getAllCity();
    }
    public City update(City city){
        return cityRepository.updateCity(city);
    }
    public void delete(String id){
        cityRepository.deleteCity(Long.parseLong(id));
    }
    public void saveNewCity(){
        cityRepository.saveCity(new City());
    }
}
