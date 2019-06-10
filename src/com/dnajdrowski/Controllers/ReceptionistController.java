package com.dnajdrowski.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.Arrays;

import static com.dnajdrowski.DataStructure.Functions.*;

public class ReceptionistController {


    @FXML
    private Button btnAdd, btnList, btnLogout;

    @FXML
    private HBox panelHBox;

    private ArrayList<Button> buttons;


    @FXML
    public void initialize() {

        buttons = new ArrayList<>(Arrays.asList(btnAdd, btnLogout, btnList));
        btnAdd.fire();
    }


    @FXML
    public void checkActivity(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            setAnchorPane(panelHBox, "addUser");
            keepButtonFocused(btnAdd, buttons);
        } else if (e.getSource() == btnList) {
            setAnchorPane(panelHBox, "userList");
            keepButtonFocused(btnList, buttons);
        } else if(e.getSource() == btnLogout) {
            logout(btnAdd);
            keepButtonFocused(btnAdd, buttons);
        }
    }

    
}
