package com.dnajdrowski.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Adress {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty city = new SimpleStringProperty("");
    private final SimpleStringProperty postCode = new SimpleStringProperty("");
    private final SimpleStringProperty street = new SimpleStringProperty("");
    private final SimpleStringProperty houseNumber = new SimpleStringProperty("");

    public Adress() {
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getPostCode() {
        return postCode.get();
    }

    public SimpleStringProperty postCodeProperty() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode.set(postCode);
    }

    public String getStreet() {
        return street.get();
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getHouseNumber() {
        return houseNumber.get();
    }

    public SimpleStringProperty houseNumberProperty() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber.set(houseNumber);
    }
}
