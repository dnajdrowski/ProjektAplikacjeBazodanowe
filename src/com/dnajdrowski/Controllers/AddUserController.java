package com.dnajdrowski.Controllers;

import com.dnajdrowski.Classes.MyStringConverter;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserController {

    @FXML
    private TextField imieTextField, nazwiskoTextField, emailTextField,
            peselTextField, numberTextField, phoneTextField,
            cityTextField, codeTextField, streetTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker datePicker;

    public void initialize() {
        datePicker.setConverter(new MyStringConverter());

    }

    @FXML
    private void addUser() {
        String errorMessage = checkDetails();
        if (!errorMessage.isEmpty()) {
            errorMessage = "Poniższe dane zostały wprowadzone błędnie:\n" + errorMessage;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            DataVariables.setAlert(alert,"Błędne dane!", errorMessage);
            alert.show();
            return;
        }
        new Thread(() -> {
            try {
                PreparedStatement statement = Main.con.prepareStatement(DataVariables.checkUniqueValue);
                statement.setString(1, emailTextField.getText());
                boolean emailInDatabase = statement.executeQuery().next();
                statement.setString(1, peselTextField.getText());
                if ((emailInDatabase) || (statement.executeQuery().next())) {
                    Platform.runLater(() -> {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        DataVariables.setAlert(error ,"Błędne dane!","Użytkownik o takim adresie e-mail lub peselu już istnieje w systemie.");
                        error.show();
                    });
                } else {
                    PreparedStatement insertStatement = Main.con.prepareStatement(DataVariables.functionAddUser);
                    insertStatement.setString(1, imieTextField.getText());
                    insertStatement.setString(2, nazwiskoTextField.getText());
                    insertStatement.setString(3, emailTextField.getText());
                    insertStatement.setString(4, passwordField.getText());
                    insertStatement.setString(5, phoneTextField.getText());
                    insertStatement.setString(6, peselTextField.getText());
                    insertStatement.setDate(7, Date.valueOf(datePicker.getEditor().getText()));
                    insertStatement.setString(8, cityTextField.getText());
                    insertStatement.setString(9, codeTextField.getText());
                    insertStatement.setString(10, streetTextField.getText());
                    insertStatement.setString(11, numberTextField.getText());
                    insertStatement.executeQuery();
                    Platform.runLater(()-> {
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(success,"Dodanie właściciela", "Dodawanie właściciela " +
                                emailTextField.getText() + " zakończło się powodzeniem!");
                        success.show();
                        clearData();
                    });
                }
            } catch (SQLException e) {
                e.getMessage();
            }
        }).start();

    }

    @FXML
    private void clearData() {
        imieTextField.clear();
        nazwiskoTextField.clear();
        emailTextField.clear();
        peselTextField.clear();
        datePicker.getEditor().clear();
        passwordField.clear();
        numberTextField.clear();
        phoneTextField.clear();
        cityTextField.clear();
        streetTextField.clear();
        codeTextField.clear();
    }


    private String checkDetails() {
        String errorMessage = "";
        char dotChar = '\u25CF';

        boolean validEmail = DataVariables.isValidEmail(emailTextField.getText());
        boolean validPassword = DataVariables.isValidToRegex(passwordField.getText(), DataVariables.passwordPattern);
        boolean validPesel = DataVariables.isValidToRegex(peselTextField.getText(), DataVariables.peselPattern);
        boolean validPhone = DataVariables.isValidToRegex(phoneTextField.getText(), DataVariables.phonePattern);
        boolean validCode = DataVariables.isValidToRegex(codeTextField.getText(), DataVariables.codePattern);
        boolean validNumber = DataVariables.isValidToRegex(numberTextField.getText(), DataVariables.numberPattern);
        boolean validDate = DataVariables.isValidToRegex(datePicker.getEditor().getText(), DataVariables.datePattern);

        if (!validEmail) {
            errorMessage += dotChar + " e-mail\n";
        }
        if (!validPassword) {
            errorMessage += dotChar + " hasło\n";
        }
        if (!validPesel) {
            errorMessage += dotChar + " pesel\n";
        }
        if (!validPhone) {
            errorMessage += dotChar + " telefon\n";
        }
        if (!validCode) {
            errorMessage += dotChar + " kod pocztowy\n";
        }
        if (!validNumber) {
            errorMessage += dotChar + " numer domu\n";
        }
        if (!validDate) {
            errorMessage += dotChar + " data urodzenia\n";
        }
        if (imieTextField.getText().isEmpty()) {
            errorMessage += dotChar + " imie\n";
        }
        if (nazwiskoTextField.getText().isEmpty()) {
            errorMessage += dotChar + " nazwisko\n";
        }
        if (cityTextField.getText().isEmpty()) {
            errorMessage += dotChar + " miasto\n";
        }
        if (streetTextField.getText().isEmpty()) {
            errorMessage += dotChar + " ulica";
        }

        return errorMessage.trim();
    }
}
