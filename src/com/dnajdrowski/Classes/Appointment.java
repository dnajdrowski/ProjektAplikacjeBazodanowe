package com.dnajdrowski.Classes;


import java.sql.Date;

public class Appointment {
    private int id;
    private Pet pet;
    private Doctor doctor;
    private Sickness sickness;
    private Medicine medicine;
    private String  purpose;
    private double price;
    private Date appointmentDate;
    private String petName;
    private String doctorFullName;
    private String ownerName;

    public Appointment(int id, Pet pet, Doctor doctor, String purpose, double price, Date appointmentDate) {
        this.id = id;
        this.pet = pet;
        this.doctor = doctor;
        this.purpose = purpose;
        this.price = price;
        this.appointmentDate = appointmentDate;
        this.doctorFullName = doctor.getFirstName() + " " + doctor.getLastName();
        this.petName = pet.getName();
        this.ownerName = pet.getUser().getFirstName() + " " + pet.getUser().getLastName();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Sickness getSickness() {
        return sickness;
    }

    public void setSickness(Sickness sickness) {
        this.sickness = sickness;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
