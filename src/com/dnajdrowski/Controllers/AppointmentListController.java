package com.dnajdrowski.Controllers;

import com.dnajdrowski.Classes.Appointment;
import com.dnajdrowski.Classes.Medicine;
import com.dnajdrowski.Classes.Sickness;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;

import static com.dnajdrowski.DataStructure.Functions.*;
import static com.dnajdrowski.DataStructure.Functions.updateAppointment;

public class AppointmentListController {

    @FXML
    private TableView<Appointment> tableViewAppointments;

    private ObservableList<Sickness> sicknessesList;

    private ObservableList<Medicine> medicinesList;


    public void initialize() {
        tableViewAppointments.setVisible(false);

        ContextMenu contextMenuAppointment = new ContextMenu();
        MenuItem deleteAppointment = new MenuItem("Odwołaj wizytę");
        MenuItem addCost = new MenuItem("Dodaj cene wizyty");
        MenuItem addMedicine = new MenuItem("Dodaj lek");
        MenuItem addSickness = new MenuItem("Dodaj chorobe");
        deleteAppointment.setOnAction(event ->
                deleteAppointment(tableViewAppointments.getSelectionModel().getSelectedItem(), tableViewAppointments));
        addCost.setOnAction(event -> {
            Dialog dialog = new TextInputDialog();
            dialog.setTitle("Cena wizyty");
            dialog.setHeaderText("Podaj cene wizyty:");
            dialog.initOwner(Main.stage);

            Optional result = dialog.showAndWait();

            if(result.isPresent() && !result.get().toString().isEmpty()) {
                updateAppointment(tableViewAppointments.getSelectionModel().getSelectedItem(),
                        DataVariables.UPDATE_PRICE_APPOINTMENT, result.get().toString());
            }
        });
        addSickness.setOnAction(event -> {
            Dialog<Sickness> dialog = new ChoiceDialog<>(sicknessesList.get(0), sicknessesList);
            dialog.setTitle("Dodaj chorobe");
            dialog.setHeaderText("Wybierz chorobę:");
            dialog.initOwner(Main.stage);

            Optional<Sickness> result = dialog.showAndWait();
            if(result.isPresent() && !result.get().toString().isEmpty()) {
                updateAppointment(tableViewAppointments.getSelectionModel().getSelectedItem(),
                        DataVariables.UPDATE_SICKNESS_APPOINTMENT, result.get().getId() + "");
            }

        });
        addMedicine.setOnAction(event -> {
            Dialog<Medicine> dialog = new ChoiceDialog<>(medicinesList.get(0), medicinesList);
            dialog.setTitle("Dodaj lekarstwo");
            dialog.setHeaderText("Wybierz lekarstwo:");
            dialog.initOwner(Main.stage);

            Optional<Medicine> result = dialog.showAndWait();
            if(result.isPresent() && !result.get().toString().isEmpty()) {
                updateAppointment(tableViewAppointments.getSelectionModel().getSelectedItem(),
                        DataVariables.UPDATE_MEDICINE_APPOINTMENT, result.get().getId() + "");
            }
        });


        if(DataVariables.userType.equals("wlasciciel")) {
            contextMenuAppointment.getItems().setAll(deleteAppointment);
            tableViewAppointments.setContextMenu(contextMenuAppointment);
        } else if(DataVariables.userType.equals("lekarz")) {
            sicknessesList = FXCollections.observableArrayList();
            medicinesList = FXCollections.observableArrayList();
            getMedicines(medicinesList);
            getSicknesses(sicknessesList);
            contextMenuAppointment.getItems().setAll(deleteAppointment, addCost, addMedicine, addSickness);
            tableViewAppointments.setContextMenu(contextMenuAppointment);
        }

        getAppointments(DataVariables.email, tableViewAppointments);
    }
}
