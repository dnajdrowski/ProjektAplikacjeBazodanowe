package com.dnajdrowski.Controllers;

import com.dnajdrowski.Classes.Doctor;
import com.dnajdrowski.Classes.MyStringConverter;
import com.dnajdrowski.Classes.Pet;
import com.dnajdrowski.Classes.User;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.DataStructure.Functions;
import com.dnajdrowski.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;

public class AddAppointmentController {

    @FXML
    private DatePicker datePickerAppointment;

    @FXML
    private ChoiceBox<String> purposeChoiceBox;

    @FXML
    private ChoiceBox<Pet> petAppointmentChoiceBox;

    @FXML
    private ChoiceBox<Doctor> doctorAppointmentChoiceBox;

    private List<String> purposeTypes = Arrays.asList("Wizyta kontrolna", "Dolegliwosc");


    public void initialize() {

        datePickerAppointment.setConverter(new MyStringConverter());

        purposeChoiceBox.getItems().setAll(purposeTypes);

        User user = UserController.userList.get(0);

        Functions.getDoctors(null, doctorAppointmentChoiceBox);
        Functions.getPets(user.getId(), null, petAppointmentChoiceBox);

    }

    @FXML
    public void addAppointment() {
        String errorMessage = "";
        char dotChar = '\u25CF';
        boolean isValidDate = DataVariables.isValidToRegex(datePickerAppointment.getEditor().getText(), DataVariables.datePattern);

        if (!isValidDate) {
            errorMessage += dotChar + "data wizyty\n";
        }

        if (petAppointmentChoiceBox.getValue() == null) {
            errorMessage += dotChar + "pupil\n";
        }

        if (doctorAppointmentChoiceBox.getValue() == null) {
            errorMessage += dotChar + "lekarz\n";
        }

        if (purposeChoiceBox.getValue() == null) {
            errorMessage += dotChar + "cel wizyty\n";
        }

        errorMessage = errorMessage.trim();

        if (errorMessage.isEmpty()) {
            new Thread(() -> {
                try (PreparedStatement insertAppointment = Main.con.prepareStatement(DataVariables.INSERT_APPOINTMENT)) {

                    insertAppointment.setDate(1, Date.valueOf(datePickerAppointment.getEditor().getText()));
                    insertAppointment.setInt(2, petAppointmentChoiceBox.getSelectionModel().getSelectedItem().getId());
                    insertAppointment.setInt(3, doctorAppointmentChoiceBox.getSelectionModel().getSelectedItem().getId());
                    insertAppointment.setString(4, purposeChoiceBox.getSelectionModel().getSelectedItem());

                    int result = insertAppointment.executeUpdate();
                    if (result == 1) {
                        Platform.runLater(() -> {
                            Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                            DataVariables.setAlert(informationAlert, "Zapisanie na wizytę", "Pomyślnie zapisano" +
                                    " na wizytę " + petAppointmentChoiceBox.getSelectionModel().getSelectedItem() + ".");
                            informationAlert.show();
                            clearData();
                        });
                    } else {
                        Platform.runLater(() -> {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            DataVariables.setAlert(errorAlert, "Zapiasanie na wizytę", "Nie udało się zapisać na wizytę." +
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
    private void clearData() {
        datePickerAppointment.getEditor().clear();
        petAppointmentChoiceBox.getSelectionModel().clearSelection();
        doctorAppointmentChoiceBox.getSelectionModel().clearSelection();
        purposeChoiceBox.getSelectionModel().clearSelection();
    }
}
