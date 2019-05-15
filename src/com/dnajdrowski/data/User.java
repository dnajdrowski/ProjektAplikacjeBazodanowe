package com.dnajdrowski.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class User {
    private static final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private static final SimpleStringProperty firstName = new SimpleStringProperty("");
    private static final SimpleStringProperty lastName = new SimpleStringProperty("");
    private static final SimpleStringProperty email = new SimpleStringProperty("");
    private static final SimpleStringProperty password = new SimpleStringProperty("");
    private static final SimpleStringProperty phoneNumber = new SimpleStringProperty("");
    private static final SimpleStringProperty peselNumber = new SimpleStringProperty("");
    private Date birthDate;
    private Adress adress;

    public User(Date birthDate, Adress adress) {
        this.birthDate = birthDate;
        this.adress = adress;
    }

    public static int getId() {
        return id.get();
    }

    public static SimpleIntegerProperty idProperty() {
        return id;
    }

    public static void setId(int id) {
        User.id.set(id);
    }

    public static String getFirstName() {
        return firstName.get();
    }

    public static SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        User.firstName.set(firstName);
    }

    public static String getLastName() {
        return lastName.get();
    }

    public static SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        User.lastName.set(lastName);
    }

    public static String getEmail() {
        return email.get();
    }

    public static SimpleStringProperty emailProperty() {
        return email;
    }

    public static void setEmail(String email) {
        User.email.set(email);
    }

    public static String getPassword() {
        return password.get();
    }

    public static SimpleStringProperty passwordProperty() {
        return password;
    }

    public static void setPassword(String password) {
        User.password.set(password);
    }

    public static String getPhoneNumber() {
        return phoneNumber.get();
    }

    public static SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        User.phoneNumber.set(phoneNumber);
    }

    public static String getPeselNumber() {
        return peselNumber.get();
    }

    public static SimpleStringProperty peselNumberProperty() {
        return peselNumber;
    }

    public static void setPeselNumber(String peselNumber) {
        User.peselNumber.set(peselNumber);
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}