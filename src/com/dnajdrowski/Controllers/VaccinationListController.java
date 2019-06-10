package com.dnajdrowski.Controllers;

import com.dnajdrowski.Classes.Vaccination;
import com.dnajdrowski.DataStructure.DataVariables;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

import static com.dnajdrowski.DataStructure.Functions.deleteVaccination;
import static com.dnajdrowski.DataStructure.Functions.getVaccinations;

public class VaccinationListController {

    @FXML
    private TableView<Vaccination> tableViewVaccinations;

    public void initialize() {

        getVaccinations(DataVariables.email, tableViewVaccinations);
        tableViewVaccinations.setVisible(false);

        ContextMenu contextMenuVaccination = new ContextMenu();
        MenuItem deleteVaccination = new MenuItem("Odwołaj szczepienie");
        deleteVaccination.setOnAction(event ->
                deleteVaccination(tableViewVaccinations.getSelectionModel().getSelectedItem(), tableViewVaccinations));

        contextMenuVaccination.getItems().setAll(deleteVaccination);
        tableViewVaccinations.setContextMenu(contextMenuVaccination);
    }
}
