package com.dnajdrowski.Classes;

public class Sickness {
    private int id;
    private String name;
    private String symptoms;
    private String treatment;

    public Sickness(int id, String name, String symptoms, String treatment) {
        this.id = id;
        this.name = name;
        this.symptoms = symptoms;
        this.treatment = treatment;
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

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
