package com.dnajdrowski.Classes;

import java.sql.Date;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String peselNumber;
    private Date birthDate;
    private Address address;


    public User(int id, String firstName, String lastName, String email, String password, String phoneNumber, String peselNumber, Date birthDate, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.peselNumber = peselNumber;
        this.birthDate = birthDate;
        this.address = address;
    }

    public User(int id, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPeselNumber() {
        return peselNumber;
    }

    public void setPeselNumber(String peselNumber) {
        this.peselNumber = peselNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
//    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
//    private final SimpleStringProperty firstName = new SimpleStringProperty("");
//    private final SimpleStringProperty lastName = new SimpleStringProperty("");
//    private final SimpleStringProperty email = new SimpleStringProperty("");
//    private final SimpleStringProperty password = new SimpleStringProperty("");
//    private final SimpleStringProperty phoneNumber = new SimpleStringProperty("");
//    private final SimpleStringProperty peselNumber = new SimpleStringProperty("");
//    private Date birthDate;
//    private Address address;
//
//    public User(int id,String firstName, String lastName, String email, String password, String phoneNumber, String peselNumber, Date birthDate, Address address) {
//        this.setId(id);
//        this.setFirstName(firstName);
//        this.setLastName(lastName);
//        this.setEmail(email);
//        this.setPassword(password);
//        this.setPhoneNumber(phoneNumber);
//        this.setPeselNumber(peselNumber);
//        this.birthDate = birthDate;
//        this.address = address;
//    }
//
//    public int getId() {
//        return id.get();
//    }
//
//    public SimpleIntegerProperty idProperty() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id.set(id);
//    }
//
//    public String getFirstName() {
//        return firstName.get();
//    }
//
//    public SimpleStringProperty firstNameProperty() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName.set(firstName);
//    }
//
//    public String getLastName() {
//        return lastName.get();
//    }
//
//    public SimpleStringProperty lastNameProperty() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName.set(lastName);
//    }
//
//    public String getEmail() {
//        return email.get();
//    }
//
//    public SimpleStringProperty emailProperty() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email.set(email);
//    }
//
//    public String getPassword() {
//        return password.get();
//    }
//
//    public SimpleStringProperty passwordProperty() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password.set(password);
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber.get();
//    }
//
//    public SimpleStringProperty phoneNumberProperty() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber.set(phoneNumber);
//    }
//
//    public String getPeselNumber() {
//        return peselNumber.get();
//    }
//
//    public SimpleStringProperty peselNumberProperty() {
//        return peselNumber;
//    }
//
//    public void setPeselNumber(String peselNumber) {
//        this.peselNumber.set(peselNumber);
//    }
//
//    public Date getBirthDate() {
//        return birthDate;
//    }
//
//    public void setBirthDate(Date birthDate) {
//        this.birthDate = birthDate;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }
}