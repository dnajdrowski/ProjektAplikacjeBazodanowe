package com.dnajdrowski.Controllers;

import com.dnajdrowski.data.DataVariables;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;



public class loginController {


    @FXML
    private GridPane loginGridPane;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField emailTextField;

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
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String message = "";


        if (isValidEmail(email) && isValidPassword(password)) {
            loginLabel.setStyle("-fx-text-fill: green");
            message = "Correct data! Please click button below to log in.";
            loginButton.setDisable(false);
        } else {
            loginLabel.setStyle("-fx-text-fill: firebrick");
            if ((email.isEmpty() && password.isEmpty()) || (!isValidEmail(email) && !isValidEmail(password))) {
                message = "Please enter your e-mail and password";
            } else if (!isValidEmail(email) && isValidEmail(password)) {
                message = "Please enter your e-mail adress";
            } else if (isValidEmail(email) && !isValidEmail(password)) {
                message = "Please enter your password";
            }
            loginButton.setDisable(true);
        }
        loginLabel.setText(message);

    }

    private boolean isValidEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private boolean isValidPassword(String password) {
        boolean result = true;
        if(!DataVariables.passwordPattern.matcher(passwordField.getText()).matches()) {
            result = false;
        }
        return result;
    }
}

