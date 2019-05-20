package com.dnajdrowski.Classes;

import java.util.Date;


public class Pet {
    private int id;
    private String name;
    private String sex;
    private Date birthDate;
    private User user;
    private Type type;
    private String typeString;
    private String owner;

    public Pet(int id, String name, String sex, Date birthDate, Type type, User user) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthDate = birthDate;
        this.type = type;
        this.user = user;
        this.typeString = type.getName();
        this.owner = user.getFirstName() + " " + user.getLastName();

    }

    public Pet(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }
}
