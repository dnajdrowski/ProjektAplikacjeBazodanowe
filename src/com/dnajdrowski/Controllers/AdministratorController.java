package com.dnajdrowski.Controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Arrays;

import static com.dnajdrowski.DataStructure.Functions.*;
import static com.dnajdrowski.DataStructure.Functions.keepButtonFocused;

public class AdministratorController {

    @FXML
    private Button btnAppointments, btnVaccinations, btnUsers, btnPets, btnLogout, btnOther;

    @FXML
    private HBox panelHBox;

    private ArrayList<Button> buttons;

    public void initialize() {
        buttons = new ArrayList<>(Arrays.asList(btnAppointments,btnVaccinations,btnUsers,btnPets,btnOther,btnLogout));
    }


    public void checkActivity(Event e) {
        if(e.getSource() == btnAppointments) {
            setAnchorPane(panelHBox, "appointmentList");
            keepButtonFocused(btnAppointments, buttons);
        } else if (e.getSource() == btnVaccinations) {
            setAnchorPane(panelHBox, "vaccinationList");
            keepButtonFocused(btnVaccinations, buttons);
        }else if (e.getSource() == btnUsers) {
            setAnchorPane(panelHBox, "userList");
            keepButtonFocused(btnUsers, buttons);
        }else if (e.getSource() == btnPets) {
            setAnchorPane(panelHBox, "petList");
            keepButtonFocused(btnPets, buttons);
        } else if (e.getSource() == btnOther) {
                setAnchorPane(panelHBox, "adminPanel");
                keepButtonFocused(btnOther, buttons);
        }else if (e.getSource() == btnLogout) {
            logout(btnAppointments);
            keepButtonFocused(btnAppointments, buttons);
        }
    }
}
