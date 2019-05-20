package com.dnajdrowski.Classes;
import java.sql.Date;

public class Vaccination {
    private int id;
    private Date vaccinationDate;
    private Pet pet;
    private Doctor doctor;
    private VaccinationType vaccinationType;
    private String petName;
    private String doctorFullName;
    private String vaccinationTypeString;
    private double price;
    private String ownerName;

    public Vaccination(int id, Date vaccinationDate, Pet pet, Doctor doctor, VaccinationType vaccinationType) {
        this.id = id;
        this.vaccinationDate = vaccinationDate;
        this.pet = pet;
        this.doctor = doctor;
        this.vaccinationType = vaccinationType;
        this.petName = pet.getName();
        this.doctorFullName = doctor.getFirstName() + " " + doctor.getLastName();
        this.vaccinationTypeString = vaccinationType.getName();
        this.price = vaccinationType.getPrice();
        this.ownerName = pet.getUser().getFirstName() + " " + pet.getUser().getLastName();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getVaccinationTypeString() {
        return vaccinationTypeString;
    }

    public void setVaccinationTypeString(String vaccinationTypeString) {
        this.vaccinationTypeString = vaccinationTypeString;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public Date getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(Date vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
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

    public VaccinationType getVaccinationType() {
        return vaccinationType;
    }

    public void setVaccinationType(VaccinationType vaccinationType) {
        this.vaccinationType = vaccinationType;
    }
}
