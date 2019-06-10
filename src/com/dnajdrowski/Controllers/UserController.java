package com.dnajdrowski.Controllers;

import static com.dnajdrowski.DataStructure.Functions.*;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Classes.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.*;


public class UserController {

    @FXML
    private Button btnAdd, btnMyPets, btnMyAppointments, btnAddAppointment,
            btnMyVaccinations, btnAddVaccination, btnLogout, btnMyDetails;

    @FXML
    private HBox panelHBox;

    static ObservableList<User> userList;

    private ArrayList<Button> buttons;


    public void initialize() {

        buttons = new ArrayList<>(Arrays.asList(btnAdd, btnMyPets, btnMyAppointments, btnAddAppointment,
                btnMyVaccinations, btnAddVaccination, btnLogout));

        userList = FXCollections.observableArrayList();

        Platform.runLater(()-> getUser(DataVariables.email, userList));

    }

    public void checkActivity(Event e) {

        if (e.getSource() == btnAdd) {
            setAnchorPane(panelHBox, "addPet");
            keepButtonFocused(btnAdd, buttons);
        } else if(e.getSource() == btnMyDetails) {
            showUserDetails(userList.get(0));
        } else if (e.getSource() == btnMyPets) {
            setAnchorPane(panelHBox, "petList");
            keepButtonFocused(btnMyPets, buttons);
        } else if (e.getSource() == btnMyAppointments) {
            setAnchorPane(panelHBox, "appointmentList");
            keepButtonFocused(btnMyAppointments, buttons);
        } else if (e.getSource() == btnAddAppointment) {
            setAnchorPane(panelHBox, "addAppointment");
            keepButtonFocused(btnAddAppointment, buttons);
        } else if (e.getSource() == btnMyVaccinations) {
            setAnchorPane(panelHBox, "vaccinationList");
            keepButtonFocused(btnMyVaccinations, buttons);
        } else if (e.getSource() == btnAddVaccination) {
            setAnchorPane(panelHBox, "addVaccination");
            keepButtonFocused(btnAddVaccination, buttons);
        } else if (e.getSource() == btnLogout) {
            logout(btnAdd);
            keepButtonFocused(btnAdd, buttons);
        }
    }
}



