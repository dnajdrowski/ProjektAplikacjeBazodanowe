package com.dnajdrowski.Controllers;

import com.dnajdrowski.Classes.MyStringConverter;
import com.dnajdrowski.Classes.Type;
import com.dnajdrowski.Classes.User;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.dnajdrowski.DataStructure.Functions.*;

public class AddPetController {

    @FXML
    private DatePicker datePickerPet;

    @FXML
    private ChoiceBox<Type> typeChoiceBox;

    @FXML
    private ChoiceBox<String> sexChoiceBox;

    @FXML
    private TextField nameTextField;

    private List<User> user = FXCollections.observableArrayList();

    private List<String> sexTypes = Arrays.asList("samiec", "samica");

    public void initialize() {
        sexChoiceBox.getItems().setAll(sexTypes);
        datePickerPet.setConverter(new MyStringConverter());


        getTypes(typeChoiceBox);
        getUser(DataVariables.email, user);

    }


    @FXML
    public void addPet() {
        String errorMessage = "";
        char dotChar = '\u25CF';
        boolean isValidDate = DataVariables.isValidToRegex(datePickerPet.getEditor().getText(), DataVariables.datePattern);

        if (nameTextField.getText().isEmpty()) {
            errorMessage += dotChar + "imie\n";
        }

        if (sexChoiceBox.getValue() == null) {
            errorMessage += dotChar + "płeć\n";
        }

        if (!isValidDate) {
            errorMessage += dotChar + "data urodzenia\n";
        }

        if (typeChoiceBox.getValue() == null) {
            errorMessage += dotChar + "gatunek\n";
        }

        errorMessage = errorMessage.trim();

        if (errorMessage.isEmpty()) {
            new Thread(() -> {
                try (PreparedStatement insertPetStatement = Main.con.prepareStatement(DataVariables.INSERT_PET)) {

                    insertPetStatement.setString(1, nameTextField.getText());
                    insertPetStatement.setString(2, sexChoiceBox.getValue());
                    insertPetStatement.setDate(3, Date.valueOf(datePickerPet.getEditor().getText()));
                    insertPetStatement.setInt(4, user.get(0).getId());
                    insertPetStatement.setInt(5, typeChoiceBox.getValue().getId());

                    int result = insertPetStatement.executeUpdate();
                    if (result == 1) {
                        Platform.runLater(() -> {
                            Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                            DataVariables.setAlert(informationAlert, "Dodawanie pupila", "Pomyślnie dodano pupila " +
                                    nameTextField.getText());
                            informationAlert.show();
                            clearAddPet();
                        });
                    } else {
                        Platform.runLater(() -> {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            DataVariables.setAlert(errorAlert, "Dodawanie pupila", "Nie udało się dodać pupila. " +
                                    "Coś poszło nie tak!");
                            errorAlert.show();
                            clearAddPet();
                        });
                    }

                } catch (SQLException e) {
                    e.getMessage();
                }
            }).start();
        } else {
            errorMessage = "Poniższe dane zawierają błąd:\n" + errorMessage;
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            DataVariables.setAlert(errorAlert, "Błędne dane", errorMessage);
            errorAlert.show();
        }

    }

    @FXML
    private void clearAddPet() {
        nameTextField.clear();
        datePickerPet.getEditor().clear();
        sexChoiceBox.getSelectionModel().clearSelection();
        typeChoiceBox.getSelectionModel().clearSelection();
    }
}
