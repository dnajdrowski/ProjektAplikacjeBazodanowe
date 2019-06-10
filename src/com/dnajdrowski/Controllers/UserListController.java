package com.dnajdrowski.Controllers;

import com.dnajdrowski.Classes.User;
import com.dnajdrowski.DataStructure.Functions;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

import static com.dnajdrowski.DataStructure.Functions.deleteUser;
import static com.dnajdrowski.DataStructure.Functions.showUserDetails;

public class UserListController {

    @FXML
    private TableView<User> tableView;

    public void initialize() {
        Functions.getUsers(null, tableView);
        tableView.setVisible(false);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteUser = new MenuItem("Usuń właściciela");
        MenuItem showUserDetails = new MenuItem("Pokaż dane właściciela");
        deleteUser.setOnAction(event -> deleteUser(tableView.getSelectionModel().getSelectedItem(), tableView));
        showUserDetails.setOnAction(event -> showUserDetails(tableView.getSelectionModel().getSelectedItem()) );

        contextMenu.getItems().addAll(deleteUser, showUserDetails);
        tableView.setContextMenu(contextMenu);
    }

}
