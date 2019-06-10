package com.dnajdrowski.Controllers;

import com.dnajdrowski.Classes.Pet;
import com.dnajdrowski.Classes.User;
import com.dnajdrowski.DataStructure.DataVariables;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

import static com.dnajdrowski.DataStructure.Functions.deletePet;
import static com.dnajdrowski.DataStructure.Functions.getPets;

public class PetListController {

    @FXML
    private TableView<Pet> tableViewPets;

    public void initialize() {

        tableViewPets.setVisible(false);
        if(DataVariables.userType.equals("wlasciciel")) {
            User user = UserController.userList.get(0);
            getPets(user.getId(), tableViewPets, null);
        } else {
            getPets(-1, tableViewPets, null);
        }


        ContextMenu contextMenuPet = new ContextMenu();
        MenuItem deletePet = new MenuItem("Usuń pupila");
        deletePet.setOnAction(event -> deletePet(tableViewPets.getSelectionModel().getSelectedItem(),tableViewPets));

        contextMenuPet.getItems().setAll(deletePet);
        tableViewPets.setContextMenu(contextMenuPet);

    }
}
