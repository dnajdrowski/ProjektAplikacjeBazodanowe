package com.dnajdrowski.Controllers;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Arrays;

import static com.dnajdrowski.DataStructure.Functions.*;

public class DoctorController {

    @FXML
    private Button btnMyAppointments, btnMyVaccinations, btnLogout;

    @FXML
    private HBox panelHBox;

    private ArrayList<Button> buttons;


    public void initialize() {
        buttons = new ArrayList<>(Arrays.asList(btnLogout, btnMyAppointments, btnMyVaccinations));


    }



    @FXML
    public void checkActivity(Event e) {
        if(e.getSource() == btnMyAppointments) {
            setAnchorPane(panelHBox, "appointmentList");
            keepButtonFocused(btnMyAppointments, buttons);
        } else if (e.getSource() == btnMyVaccinations) {
            setAnchorPane(panelHBox, "vaccinationList");
            keepButtonFocused(btnMyVaccinations, buttons);
        } else if (e.getSource() == btnLogout) {
            logout(btnMyAppointments);
            keepButtonFocused(btnMyAppointments, buttons);
        }
    }
}
