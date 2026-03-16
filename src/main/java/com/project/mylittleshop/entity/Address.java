package com.project.mylittleshop.entity;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {
    private String country;
    private String city;
    private String street;
    private String postCode;
    
    public Address(String country, String city, String street, String postCode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.postCode = postCode;
    }
    public Address() {
    
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) && Objects.equals(city,
                address.city) && Objects.equals(street, address.street) && Objects.equals(postCode,
                address.postCode);
    }
    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, postCode);
    }
    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
