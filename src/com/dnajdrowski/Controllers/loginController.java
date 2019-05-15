package com.dnajdrowski.Controllers;

import com.dnajdrowski.Main;
import com.dnajdrowski.data.DataVariables;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        loginButton.setDisable(false);
        emailTextField.setText("pradeczek19@wp.pl");
        passwordField.setText("Prawdaprada19%");
        checkValidInputPattern();
    }

    @FXML
    public void checkUser() {
        new Thread(() -> {
            checkUserType(DataVariables.TABLE_WLASCICIEL, "userpanel");
            checkUserType(DataVariables.TABLE_LEKARZ, "doctorpanel");
            checkUserType(DataVariables.TABLE_RECEPCJONISTA, "receptionistpanel");
            if (Main.stage.getWidth() != 800) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    DataVariables.setAlert(alert, "Brak użytkownika",
                            "Użytkownik o podanych danych nie istnieje!\nWprowadź prawidłowe dane.");
                    alert.show();
                });
            }

        }).start();
    }


    //sprawdzenie, czy wpisane dane pasuja do regexow
    @FXML
    public void checkValidInputPattern() {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String message = "";
        boolean isValidEmail = DataVariables.isValidEmail(email);
        boolean isValidToRegex = DataVariables.isValidToRegex(passwordField.getText(), DataVariables.passwordPattern);


        if (isValidEmail && isValidToRegex) {
            loginLabel.setStyle("-fx-text-fill: green");
            message = "Prawidłowe dane! Kliknij przycisk, aby zalogować.";
            loginButton.setDisable(false);
        } else {
            loginLabel.setStyle("-fx-text-fill: firebrick");
            if ((email.isEmpty() && password.isEmpty()) || (!isValidEmail && !isValidToRegex)) {
                message = "Podaj poprawny email oraz hasło";
            } else if (!isValidEmail && isValidToRegex) {
                message = "Podaj poprawny email";
            } else if (isValidEmail && !isValidToRegex) {
                message = "Podaj poprawne hasło";
            }
            loginButton.setDisable(true);
        }
        loginLabel.setText(message);

    }


    //sprawdzenie, czy uzytkownik jaki uzytkownik sie loguje oraz poprawnosc danych logowania
    private void checkUserType(String tableName, String panel) {
        try {
            PreparedStatement statement = Main.con.prepareStatement(DataVariables.checkTableByEmail.replace("$tableName",
                    tableName));
            statement.setString(1, emailTextField.getText());
            ResultSet result = statement.executeQuery();
            if (result.next() && result.getString("password").equals(passwordField.getText())) {
                Main.setRoot(panel);
                Main.stage.setWidth(800);
                Main.stage.setHeight(600);
                Main.stage.centerOnScreen();
                Platform.runLater(() -> {
                    try {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Pomyślnie zalogowano",
                                "");
                        alert.setContentText("Witaj " + result.getString("imie") + " " +
                                result.getString("nazwisko") + "!\nZostałeś zalogowany jako " +
                                tableName + ".");
                        alert.show();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}

