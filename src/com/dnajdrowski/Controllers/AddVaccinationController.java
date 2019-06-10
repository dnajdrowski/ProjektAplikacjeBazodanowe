package com.dnajdrowski.Controllers;

import com.dnajdrowski.Classes.*;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.DataStructure.Functions;
import com.dnajdrowski.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.SQLException;

public class AddVaccinationController {

    @FXML
    private DatePicker datePickerVaccination;


    @FXML
    private ChoiceBox<Doctor> doctorVaccinationChoiceBox;

    @FXML
    private ChoiceBox<Pet> petVaccinationChoiceBox;

    @FXML
    private ChoiceBox<VaccinationType> vaccinationTypeChoiceBox;

    public void initialize() {

        datePickerVaccination.setConverter(new MyStringConverter());

        User user = UserController.userList.get(0);

        Functions.getDoctors(null, doctorVaccinationChoiceBox);
        Functions.getPets(user.getId(), null, petVaccinationChoiceBox);
        Functions.getVaccinationTypes(vaccinationTypeChoiceBox);
    }

    @FXML
    public void addVaccination() {
        String errorMessage = "";
        char dotChar = '\u25CF';
        boolean isValidDate = DataVariables.isValidToRegex(datePickerVaccination.getEditor().getText(), DataVariables.datePattern);

        if (!isValidDate) {
            errorMessage += dotChar + "data szczepienia\n";
        }

        if (petVaccinationChoiceBox.getValue() == null) {
            errorMessage += dotChar + "pupil\n";
        }

        if (doctorVaccinationChoiceBox.getValue() == null) {
            errorMessage += dotChar + "lekarz\n";
        }

        if (vaccinationTypeChoiceBox.getValue() == null) {
            errorMessage += dotChar + "typ szczepienia\n";
        }

        errorMessage = errorMessage.trim();

        if (errorMessage.isEmpty()) {
            new Thread(() -> {
                try (PreparedStatement insertVaccination = Main.con.prepareStatement(DataVariables.INSERT_VACCINATION)) {

                    insertVaccination .setDate(1, Date.valueOf(datePickerVaccination.getEditor().getText()));
                    insertVaccination .setInt(2, petVaccinationChoiceBox.getSelectionModel().getSelectedItem().getId());
                    insertVaccination .setInt(3, doctorVaccinationChoiceBox.getSelectionModel().getSelectedItem().getId());
                    insertVaccination .setInt(4, vaccinationTypeChoiceBox.getSelectionModel().getSelectedItem().getId());

                    int result = insertVaccination .executeUpdate();
                    if (result == 1) {
                        Platform.runLater(() -> {
                            Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                            DataVariables.setAlert(informationAlert, "Zapisanie na szczepienie", "Pomyślnie zapisano" +
                                    " na szczepienie " + petVaccinationChoiceBox.getSelectionModel().getSelectedItem() + ".");
                            informationAlert.show();
                            clearData();
                        });
                    } else {
                        Platform.runLater(() -> {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            DataVariables.setAlert(errorAlert, "Zapiasanie na szczepienie", "Nie udało się zapisać na szczepienie." +
                                    "Coś poszło nie tak!");
                            errorAlert.show();
                            clearData();
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
    public void clearData() {
        datePickerVaccination.getEditor().clear();
        doctorVaccinationChoiceBox.getSelectionModel().clearSelection();
        petVaccinationChoiceBox.getSelectionModel().clearSelection();
        vaccinationTypeChoiceBox.getSelectionModel().clearSelection();
    }
}
