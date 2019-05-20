package com.dnajdrowski.Classes;

public class Medicine {
    private int id;
    private String name;
    private String dose;
    private String form;

    public Medicine(int id, String name, String dose, String form) {
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.form = form;
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

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}
