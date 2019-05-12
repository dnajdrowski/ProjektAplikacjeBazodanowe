package com.dnajdrowski.Controllers;

import com.dnajdrowski.Main;
import com.dnajdrowski.data.DataVariables;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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

    @FXML
    private Button registerButton;


    public void initialize() {
        Platform.runLater(() -> loginGridPane.requestFocus());
        loginButton.setDisable(false);
        emailTextField.setText("bartol04@gmail.com");
        passwordField.setText("Danielek9898!!");
    }

    @FXML
    public void checkUser() {
        new Thread(() -> {
            checkUserType(DataVariables.TABLE_WLASCICIEL, "userpanel");
            checkUserType(DataVariables.TABLE_LEKARZ, "doctorpanel");
            checkUserType(DataVariables.TABLE_RECEPCJONISTA, "receptionistpanel");
            if(Main.stage.getWidth() != 800) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(Main.stage);
                    alert.setResizable(false);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("Brak użytkownika");
                    alert.setContentText("Użytkownik o podanych danych nie istnieje!\nWprowadź prawidłowe dane.");
                    alert.getDialogPane().setStyle("-fx-font-size: 14; -fx-font-family: 'Times New Roman Bold'");
                    alert.setHeaderText(null);
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


        if (isValidEmail(email) && isValidPassword()) {
            loginLabel.setStyle("-fx-text-fill: green");
            message = "Prawidłowe dane! Kliknij przycisk, aby zalogować.";
            loginButton.setDisable(false);
        } else {
            loginLabel.setStyle("-fx-text-fill: firebrick");
            if ((email.isEmpty() && password.isEmpty()) || (!isValidEmail(email) && !isValidEmail(password))) {
                message = "Podaj poprawny email oraz hasło";
            } else if (!isValidEmail(email) && isValidEmail(password)) {
                message = "Podaj poprawny email";
            } else if (isValidEmail(email) && !isValidEmail(password)) {
                message = "Podaj poprawne hasło";
            }
            loginButton.setDisable(true);
        }
        loginLabel.setText(message);

    }

    //sprawdzenie poprawnosci maila
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

    //sprawdzenie poprawnosci hasla
    private boolean isValidPassword() {
        boolean result = true;
        if (!DataVariables.passwordPattern.matcher(passwordField.getText()).matches()) {
            result = false;
        }
        return result;
    }


    //sprawdzenie, czy uzytkownik jaki uzytkownik sie loguje oraz poprawnosc danych logowania
    private void checkUserType(String tableName, String panel) {
        try {
            PreparedStatement statement = Main.con.prepareStatement(DataVariables.checkWlasiciel.replace("$tableName",
                                            tableName));
            statement.setString(1, emailTextField.getText());
            ResultSet result = statement.executeQuery();
            if(result.next() && result.getString("password").equals(passwordField.getText())){
                Main.setRoot(panel);
                Main.stage.setWidth(800);
                Main.stage.setHeight(600);
                Main.stage.centerOnScreen();
                Platform.runLater(()->{
                    try {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initOwner(Main.stage);
                        alert.setResizable(false);
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.setTitle("Pomyślnie zalogowano");
                        alert.getDialogPane().setStyle("-fx-font-size: 16; -fx-font-family: 'Times New Roman Bold'");
                        alert.setHeaderText(null);
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

