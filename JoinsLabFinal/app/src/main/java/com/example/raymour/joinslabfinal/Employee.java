package com.example.raymour.joinslabfinal;

/**
 * Created by raymour on 7/18/16.
 */
public class Employee {
    private String ssn;
    private String firstName;
    private String lastName;
    private String yearOfBirth;
    private String homeCity;


    public Employee(String ssn, String firstName, String lastName, String yearOfBirth, String homeCity) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.homeCity = homeCity;
    }

    public String getSsn() {
        return ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getHomeCity() {
        return homeCity;
    }




}

