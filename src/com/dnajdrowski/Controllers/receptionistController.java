package com.dnajdrowski.Controllers;

import com.dnajdrowski.Main;
import com.dnajdrowski.data.DataVariables;
import com.dnajdrowski.data.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class receptionistController {


    @FXML
    private AnchorPane anchorAdd, anchorList;

    @FXML
    private Button btnAdd, btnList;

    @FXML
    private TextField imieTextField, nazwiskoTextField, emailTextField, peselTextField, numberTextField, phoneTextField,
            cityTextField, codeTextField, streetTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker datePicker;

    private List<User> userList;



    @FXML
    public void initialize() {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }





    @FXML
    public void addUser() {
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
    public void getUsers() {

    }

    private String checkDetails() {
        char dotChar = '\u25CF';
        String errorMessage = "";

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

    @FXML
    public void checkActivity(ActionEvent event) {
        if (event.getSource() == btnAdd) {
            anchorAdd.toFront();
        } else if (event.getSource() == btnList) {
            anchorList.toFront();
        }
    }

    @FXML
    public void clearData() {
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

    @FXML
    public void logout() {
        try {
            ButtonType buttonYes = new ButtonType("Tak", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonNo = new ButtonType("Nie", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", buttonYes, buttonNo);
            DataVariables.setAlert(alert, "Wylogowanie","Czy na pewno chcesz się wylogować?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == buttonYes)) {
                Main.setRoot("login");
                Main.stage.setWidth(600);
                Main.stage.setHeight(800);
                Main.stage.centerOnScreen();
            } else {
                btnAdd.requestFocus();
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }


}
