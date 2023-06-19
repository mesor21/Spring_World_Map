package com.example.demo.service;

import com.example.demo.entity.City;
import com.example.demo.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    public City getMode() {
        List<City> cities = cityRepository.getAllCity();
        List<Integer> counts = new ArrayList<>();
        for (City city : cities) {
            counts.add(city.getCountPerson());
        }
        int maxCount = 0;
        int modeIndex = -1;
        for (int i = 0; i < counts.size(); i++) {
            int count = counts.get(i);
            int frequency = Collections.frequency(counts, count);
            if (frequency > maxCount) {
                maxCount = frequency;
                modeIndex = i;
            }
        }
        if (modeIndex != -1) {
            return cities.get(modeIndex);
        }
        return null; // Return null if there is no mode
    }

    public double getMedian() {
        List<City> cities = cityRepository.getAllCity();
        List<Integer> counts = new ArrayList<>();
        for (City city : cities) {
            counts.add(city.getCountPerson());
        }
        Collections.sort(counts);
        int size = counts.size();
        if (size % 2 == 0) {
            int midIndex1 = size / 2 - 1;
            int midIndex2 = size / 2;
            int count1 = counts.get(midIndex1);
            int count2 = counts.get(midIndex2);
            return (count1 + count2) / 2.0;
        } else {
            int midIndex = size / 2;
            return counts.get(midIndex);
        }
    }
}
