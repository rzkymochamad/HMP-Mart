package com.mg.shopping.objectutil;

public class GeocodeObject {

    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;
    private String countryCode;


    public String getAddress() {
        return address;
    }

    public GeocodeObject setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public GeocodeObject setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public GeocodeObject setState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public GeocodeObject setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public GeocodeObject setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getKnownName() {
        return knownName;
    }

    public GeocodeObject setKnownName(String knownName) {
        this.knownName = knownName;
        return this;
    }


    public String getCountryCode() {
        return countryCode;
    }

    public GeocodeObject setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    @Override
    public String toString() {
        return "GeocodeObject{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", knownName='" + knownName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
