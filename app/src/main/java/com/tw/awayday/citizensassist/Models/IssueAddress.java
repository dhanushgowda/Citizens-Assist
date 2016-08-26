package com.tw.awayday.citizensassist.Models;

public class IssueAddress {
    private String addressLine;
    private String city;
    private String state;
    private String country;


    public IssueAddress setAddressLine(String addressLine) {
        this.addressLine = addressLine;
        return this;
    }

    public IssueAddress setCity(String city) {
        this.city = city;
        return this;
    }

    public IssueAddress setState(String state) {
        this.state = state;
        return this;
    }

    public IssueAddress setCountry(String country) {
        this.country = country;
        return this;
    }
    @Override
    public String toString() {
        return "AddressLine: " + addressLine + "\n" +
                "City: " + city + "\n" +
                "State: " + state + "\n" +
                "Country: " + country;
    }
}
