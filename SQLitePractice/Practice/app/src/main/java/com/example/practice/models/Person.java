package com.example.practice.models;

public class Person {

    // OOP
    private int id;
    private String firstname, middlename, lastname, employmentStatus;
    private int age;
    private double salary;
    private String username, password;
    private String created_date, created_time;
    private String updated_date, updated_time;
    public Person() {
    }

    public Person(String firstname, String middlename, String lastname, String username, String password) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getFullname() {
        String fullname = getMiddlename() != null ? getFirstname() + " " + getMiddlename().substring(0,1).toUpperCase() + ". " + getLastname() : getFirstname() + " " + getLastname();
        return fullname;
    }

    public String getUpdatedDateTime() {
        return getUpdated_date() + " " + getUpdated_time();
    }

    public String getCreatedDateTime() {
        return getCreated_date() + " " + getCreated_time();
    }
    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

}
