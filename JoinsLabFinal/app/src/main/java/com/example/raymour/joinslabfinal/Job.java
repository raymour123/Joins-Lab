package com.example.raymour.joinslabfinal;

/**
 * Created by raymour on 7/18/16.
 */
public class Job {
    private String ssn;
    private String company;
    private String salary;
    private String experience;

    public Job(String ssn, String company, String salary, String experience){
        this.ssn = ssn;
        this.company = company;
        this.salary = salary;
        this.experience = experience;

    }

    public String getSsn() {return ssn;}

    public String getCompany() {
        return company;
    }

    public String getSalary() {
        return salary;
    }

    public String getExperience() {
        return experience;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }





}

