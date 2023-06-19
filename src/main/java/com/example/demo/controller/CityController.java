package com.example.demo.controller;

import com.example.demo.entity.City;
import com.example.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class CityController {
    @Autowired
    private CityService service;

    @GetMapping("/")
    public List<City> getCityList(){
        return service.getAll();
    }
    @GetMapping("/{id}")
    public City getById(@PathVariable String id){
        return service.getById(Long.parseLong(id));
    }
    @PostMapping("/")
    public List<City> createNew(){
        service.saveNewCity();
        return service.getAll();
    }
    @PutMapping("/")
    public void update(@RequestBody City city){
        service.update(city);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }
    @GetMapping("/mode")
    public City getMode(){
        return service.getMode();
    }
    @GetMapping("/median")
    public double getMedian(){
        return service.getMedian();
    }
}