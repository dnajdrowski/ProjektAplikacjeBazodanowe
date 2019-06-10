package com.dnajdrowski.Controllers;

import com.dnajdrowski.Main;
import com.dnajdrowski.DataStructure.DataVariables;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.ini4j.Ini;


import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {


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

    private Ini ini;


    public void initialize() throws IOException{
        Platform.runLater(() -> loginGridPane.requestFocus());
        loginButton.setDisable(false);
        emailTextField.setText("piotrkarsko1993@o2.pl");
        passwordField.setText("PiotrKarsko178!");
        checkValidInputPattern();
        ini = new Ini(new File("kliniczka.ini"));
    }

    @FXML
    public void checkUser() {
        new Thread(() -> {
            boolean isUser = checkUserType(DataVariables.TABLE_WLASCICIEL, "userpanel");
            boolean isDoctor = checkUserType(DataVariables.TABLE_LEKARZ, "doctorpanel");
            boolean isReceptionist = checkUserType(DataVariables.TABLE_RECEPCJONISTA, "receptionistpanel");
            boolean isAdmin = emailTextField.getText().equals(ini.get("ITEMS").get("login")) && passwordField.getText().equals(ini.get("ITEMS").get("password"));

            if(isAdmin) {
                try {
                    Main.setRoot("adminpanel");
                } catch (IOException e) {
                    e.getMessage();
                }
                Main.stage.setWidth(900);
                Main.stage.setHeight(600);
                Main.stage.centerOnScreen();
                Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Pomyślnie zalogowano",
                                "");
                        alert.setContentText("Witaj! Zostałeś zalogowany jako administrator!" );
                        DataVariables.userType = "administator";
                        alert.show();

                });
                return;
            }
            if (!(isUser || isDoctor || isReceptionist)) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    DataVariables.setAlert(alert, "Brak użytkownika",
                            "Użytkownik o podanych danych nie istnieje!\nWprowadź prawidłowe dane.");
                    alert.show();
                });
            }

        }).start();
    }

    @FXML
    public void checkValidInputPattern() {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String message = "";
        boolean isValidEmail = DataVariables.isValidEmail(email) || email.equals(ini.get("ITEMS").get("login"));
        boolean isValidToRegex = DataVariables.isValidToRegex(passwordField.getText(), DataVariables.passwordPattern) ||
                password.equals(ini.get("ITEMS").get("password"));


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

    private boolean checkUserType(String tableName, String panel) {
        boolean result = false;
        try {
            PreparedStatement statement = Main.con.prepareStatement(DataVariables.checkTableByEmail.replace("$tableName",
                    tableName));
            statement.setString(1, emailTextField.getText());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getString("password").equals(passwordField.getText())) {
                result = true;
                DataVariables.email = emailTextField.getText();
                Main.setRoot(panel);
                Main.stage.setWidth(900);
                Main.stage.setHeight(600);
                Main.stage.centerOnScreen();
                Platform.runLater(() -> {
                    try {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Pomyślnie zalogowano",
                                "");
                        alert.setContentText("Witaj " + resultSet.getString("imie") + " " +
                                resultSet.getString("nazwisko") + "!\nZostałeś zalogowany jako " +
                                tableName + ".");
                        DataVariables.userType = tableName;
                        alert.show();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

