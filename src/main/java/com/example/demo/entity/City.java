package com.example.demo.entity;

public class City {
    private Long id;
    private String cityName;
    private String countryName;
    private String phoneCode;
    private String signOfTheCapital;
    private int countPerson;

    public City() {
        this.cityName="";
        this.countryName="";
        this.phoneCode="";
        this.signOfTheCapital="";
        this.countPerson=0;
    }

    public City(Long id, String cityName, String countryName, String phoneCode, String signOfTheCapital, int countPerson) {
        this.id = id;
        this.cityName = cityName;
        this.countryName = countryName;
        this.phoneCode = phoneCode;
        this.signOfTheCapital = signOfTheCapital;
        this.countPerson = countPerson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getSignOfTheCapital() {
        return signOfTheCapital;
    }

    public void setSignOfTheCapital(String signOfTheCapital) {
        signOfTheCapital = signOfTheCapital;
    }

    public int getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(int countPerson) {
        this.countPerson = countPerson;
    }
}
