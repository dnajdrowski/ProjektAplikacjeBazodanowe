package com.dnajdrowski.Controllers;

import com.dnajdrowski.Main;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Classes.MyStringConverter;
import com.dnajdrowski.Classes.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import static com.dnajdrowski.DataStructure.Functions.*;

public class receptionistController {


    @FXML
    private AnchorPane anchorAdd, anchorList;

    @FXML
    private Button btnAdd, btnList, btnLogout;

    @FXML
    private TextField imieTextField, nazwiskoTextField, emailTextField, peselTextField, numberTextField, phoneTextField,
            cityTextField, codeTextField, streetTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker datePicker;




    @FXML
    private TableView<User> tableView;

    private static ObservableList<User> userList;

    private ArrayList<Button> buttons;



    @FXML
    public void initialize() {
        datePicker.setConverter(new MyStringConverter());

        buttons = new ArrayList<>(Arrays.asList(btnAdd, btnLogout, btnList));
        userList = FXCollections.observableArrayList();
        btnAdd.fire();

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteContextItem = new MenuItem("Usuń");
        deleteContextItem.setOnAction( event -> {
            User user = tableView.getSelectionModel().getSelectedItem();
            deleteUser(user,tableView);
        });

        contextMenu.getItems().addAll(deleteContextItem);
        tableView.setContextMenu(contextMenu);

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

    @FXML
    public void checkActivity(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            anchorAdd.toFront();
            keepButtonFocused(btnAdd, buttons);
        } else if (e.getSource() == btnList) {
            anchorList.toFront();
            keepButtonFocused(btnList, buttons);
            getUsers(null, userList, tableView);
        } else if(e.getSource() == btnLogout) {
            logout(btnAdd);
            keepButtonFocused(btnAdd, buttons);
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



}
