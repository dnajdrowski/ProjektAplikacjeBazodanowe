package com.dnajdrowski.Controllers;

import com.dnajdrowski.Classes.*;
import com.dnajdrowski.DataStructure.Functions;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdministratorOtherController {

    @FXML
    private TableView medicinesListView, typesListView, vacctinationTypesListView, sicknessesListView;

    ObservableList<Medicine> medicines;
    ObservableList<VaccinationType> vaccinationTypes;
    ObservableList<Sickness>sicknesses;


    public void initialize() {
        medicines = FXCollections.observableArrayList();
        vaccinationTypes = FXCollections.observableArrayList();
        sicknesses = FXCollections.observableArrayList();

        ContextMenu medicineContextMenu = new ContextMenu();
        MenuItem deleteMedicine = new MenuItem("Usuń lekarstwo");
        ContextMenu typeContextMenu = new ContextMenu();
        MenuItem deleteTypes = new MenuItem("Usuń gatunek");
        ContextMenu vaccinationContextMenu = new ContextMenu();
        MenuItem deleteVaccinationType = new MenuItem("Usuń typ szczepienia");
        ContextMenu sicknessContextMenu = new ContextMenu();
        MenuItem deleteSickness = new MenuItem("Usuń chorobe");


        deleteMedicine.setOnAction(event -> Functions.deleteMedicine((Medicine) medicinesListView.getSelectionModel().getSelectedItem(), medicinesListView));
        deleteTypes.setOnAction(event -> Functions.deleteType((Type) typesListView.getSelectionModel().getSelectedItem(), typesListView));
        deleteVaccinationType.setOnAction(event -> Functions.deleteVaccinationType((VaccinationType) vacctinationTypesListView.getSelectionModel().getSelectedItem(), vacctinationTypesListView));
        deleteSickness.setOnAction(event -> Functions.deleteSicnkess((Sickness) sicknessesListView.getSelectionModel().getSelectedItem(), sicknessesListView));

        medicineContextMenu.getItems().setAll(deleteMedicine);
        typeContextMenu.getItems().setAll(deleteTypes);
        vaccinationContextMenu.getItems().setAll(deleteVaccinationType);
        sicknessContextMenu.getItems().setAll(deleteSickness);

        medicinesListView.setContextMenu(medicineContextMenu);
        typesListView.setContextMenu(typeContextMenu);
        vacctinationTypesListView.setContextMenu(vaccinationContextMenu);
        sicknessesListView.setContextMenu(sicknessContextMenu);




        Platform.runLater(()->{
            Functions.getMedicines(medicines);
            medicinesListView.setItems(medicines);
            Functions.getTypes(typesListView);
            Functions.getVaccinationTypes(vacctinationTypesListView);
            Functions.getSicknesses(sicknesses);
            sicknessesListView.setItems(sicknesses);
        });

    }
}
