package com.example.demo.controller;

import com.example.demo.entity.City;
import com.example.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class CityController {
    @Autowired
    private CityService service;
    private String lastName = "Birukov";
    HttpHeaders headers = new HttpHeaders();

    public CityController() {
        headers.add("Last-Name",lastName);
    }

    @GetMapping("/")
    public ResponseEntity<List<City>> getCityList(){

        return ResponseEntity.ok().headers(headers).body(service.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<City> getById(@PathVariable String id){
        return ResponseEntity.ok().body(service.getById(Long.parseLong(id)));
    }
    @PostMapping("/")
    public ResponseEntity<List<City>> createNew(){
        service.saveNewCity();
        return ResponseEntity.ok().headers(headers).body(service.getAll());
    }
    @PutMapping("/")
    public void update(@RequestBody City city){
        service.update(city);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        if(service.delete(id)){
            ResponseEntity.ok().headers(headers).body("Process delete done success complete");
        }
        return ResponseEntity.badRequest().headers(headers).body("Error \nIndex out of range");
    }
    @GetMapping("/mode")
    public ResponseEntity<City> getMode(){
        return ResponseEntity.ok().headers(headers).body(service.getMode());
    }
    @GetMapping("/median")
    public ResponseEntity<Double> getMedian(){
        return ResponseEntity.ok().headers(headers).body(service.getMedian());
    }
    @GetMapping("/about")
    public String getAbout(){
        return "Бирюков Алексей Александрович\nУВА-211\nПредметная область: карта мира";
    }
}