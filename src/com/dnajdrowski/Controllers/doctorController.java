package com.dnajdrowski.Controllers;

import com.dnajdrowski.Classes.Appointment;
import com.dnajdrowski.Classes.Vaccination;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.DataStructure.Functions;
import com.mysql.cj.xdevapi.Table;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Arrays;

public class doctorController {

    @FXML
    private Button btnMyAppointments, btnAddAppointment, btnMyVaccinations, btnAddVaccination, btnLogout;

    @FXML
    private AnchorPane anchorAppointmentsList, anchorAddAppointment,
            anchorVaccinationsList, anchorAddVaccination;

    @FXML
    private TableView<Appointment> tableViewAppointments;

    @FXML
    private TableView<Vaccination> tableViewVaccinations;

    private ObservableList<Appointment> appointmentsList;

    private ObservableList<Vaccination> vaccinationsList;


    private ArrayList<Button> buttons;


    public void initialize() {
        buttons = new ArrayList<>(Arrays.asList(btnAddAppointment, btnAddVaccination,btnLogout,
                btnMyAppointments,btnMyVaccinations));

        appointmentsList = FXCollections.observableArrayList();
        vaccinationsList = FXCollections.observableArrayList();

        Platform.runLater(() -> {
            Functions.getAppointments(DataVariables.email, appointmentsList, tableViewAppointments);
            Functions.getVaccinations(DataVariables.email, vaccinationsList, tableViewVaccinations);
        });

    }
}
