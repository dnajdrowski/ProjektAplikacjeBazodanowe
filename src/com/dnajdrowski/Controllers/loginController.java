package com.dnajdrowski.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class loginController {

    @FXML
    private GridPane loginGridPane;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;


    public void initialize() {
        Platform.runLater(() -> loginGridPane.requestFocus());
        loginButton.setDisable(true);
    }

    @FXML
    public void checkData() {

    }
}
