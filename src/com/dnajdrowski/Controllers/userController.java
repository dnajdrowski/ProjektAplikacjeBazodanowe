package com.dnajdrowski.Controllers;

import static com.dnajdrowski.DataStructure.Functions.*;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Main;
import com.dnajdrowski.Classes.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class userController {

    @FXML
    private Button btnAdd, btnMyPets, btnMyAppointments, btnAddAppointment,
            btnMyVaccinations, btnAddVaccination, btnLogout;


    @FXML
    private DatePicker datePickerPet, datePickerAppointment, datePickerVaccination;

    @FXML
    private ChoiceBox<Type> typeChoiceBox;

    @FXML
    private ChoiceBox<String> sexChoiceBox, purposeChoiceBox;

    @FXML
    private ChoiceBox<Doctor> doctorAppointmentChoiceBox, doctorVaccinationChoiceBox;

    @FXML
    private ChoiceBox<Pet> petAppointmentChoiceBox, petVaccinationChoiceBox;

    @FXML
    private ChoiceBox<VaccinationType> vaccinationTypeChoiceBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private AnchorPane anchorAdd, anchorPetsList, anchorAppointmentsList, anchorAddAppointment,
            anchorVaccinationsList, anchorAddVaccination;

    @FXML
    private TableView<Pet> tableViewPets;

    @FXML
    private TableView<Appointment> tableViewAppointments;

    @FXML
    private TableView<Vaccination> tableViewVaccinations;

    private List<String> sexTypes = Arrays.asList("samiec", "samica");

    private List<String> purposeTypes = Arrays.asList("Wizyta kontrolna", "Dolegliwość");

    private ObservableList<Type> typesList;

    private ObservableList<Pet> petsList;

    private ObservableList<Appointment> appointmentsList;

    private ObservableList<Doctor> doctorsList;

    private ObservableList<Vaccination> vaccinationsList;

    private ObservableList<VaccinationType> vaccinationTypesList;

    private ObservableList<User> usersList;

    private ArrayList<Button> buttons;


    public void initialize() {

        anchorAdd.toFront();

        buttons = new ArrayList<>(Arrays.asList(btnAdd, btnMyPets, btnMyAppointments, btnAddAppointment,
                btnMyVaccinations, btnAddVaccination, btnLogout));


        sexChoiceBox.getItems().setAll(sexTypes);
        purposeChoiceBox.getItems().setAll(purposeTypes);

        MyStringConverter converter = new MyStringConverter();
        datePickerPet.setConverter(converter);
        datePickerAppointment.setConverter(converter);
        datePickerVaccination.setConverter(converter);

        ContextMenu contextMenuPet = new ContextMenu();
        MenuItem deletePet = new MenuItem("Usuń pupila");
        deletePet.setOnAction(event -> {
            Pet pet = tableViewPets.getSelectionModel().getSelectedItem();
            deletePet(pet,tableViewPets);
        });
        contextMenuPet.getItems().setAll(deletePet);
        tableViewPets.setContextMenu(contextMenuPet);

        ContextMenu contextMemuAppointment = new ContextMenu();
        MenuItem deleteAppointment = new MenuItem("Odwołaj wizytę");
        deleteAppointment.setOnAction(event -> {
            Appointment appointment = tableViewAppointments.getSelectionModel().getSelectedItem();
            deleteAppointment(appointment, tableViewAppointments);
        });
        contextMemuAppointment.getItems().setAll(deleteAppointment);
        tableViewAppointments.setContextMenu(contextMemuAppointment);

        ContextMenu contextMenuVaccination = new ContextMenu();
        MenuItem deleteVaccination = new MenuItem("Odwołaj szczepienie");
        deleteVaccination.setOnAction(event -> {
            Vaccination vaccination = tableViewVaccinations.getSelectionModel().getSelectedItem();
            deleteVaccination(vaccination, tableViewVaccinations);
        });
        contextMenuVaccination.getItems().setAll(deleteVaccination);
        tableViewVaccinations.setContextMenu(contextMenuVaccination);


        typesList = FXCollections.observableArrayList();
        petsList = FXCollections.observableArrayList();
        doctorsList = FXCollections.observableArrayList();
        appointmentsList = FXCollections.observableArrayList();
        vaccinationsList = FXCollections.observableArrayList();
        vaccinationTypesList = FXCollections.observableArrayList();
        usersList = FXCollections.observableArrayList();

        Platform.runLater(() -> {
            getUsers(DataVariables.email, usersList, null);
            getDoctors(doctorsList, null, doctorVaccinationChoiceBox, doctorAppointmentChoiceBox);
            getTypes(typesList, typeChoiceBox);
            getVaccinationTypes(vaccinationTypesList, vaccinationTypeChoiceBox);
        });


    }

    public void checkActivity(Event e) {

        if (e.getSource() == btnAdd) {
            anchorAdd.toFront();
            keepButtonFocused(btnAdd, buttons);
            getTypes(typesList, typeChoiceBox);
        } else if (e.getSource() == btnMyPets) {
            anchorPetsList.toFront();
            keepButtonFocused(btnMyPets, buttons);
            getPets(usersList.get(0).getId(), petsList, tableViewPets, petVaccinationChoiceBox, petAppointmentChoiceBox);
        } else if (e.getSource() == btnMyAppointments) {
            anchorAppointmentsList.toFront();
            keepButtonFocused(btnMyAppointments, buttons);
            getAppointments(DataVariables.email, appointmentsList, tableViewAppointments);
        } else if (e.getSource() == btnAddAppointment) {
            anchorAddAppointment.toFront();
            getDoctors(doctorsList, null, doctorVaccinationChoiceBox, doctorAppointmentChoiceBox);
            keepButtonFocused(btnAddAppointment, buttons);
            getPets(usersList.get(0).getId(), petsList, tableViewPets, petVaccinationChoiceBox, petAppointmentChoiceBox);
        } else if (e.getSource() == btnMyVaccinations) {
            anchorVaccinationsList.toFront();
            keepButtonFocused(btnMyVaccinations, buttons);
            getVaccinations(DataVariables.email, vaccinationsList, tableViewVaccinations);
        } else if (e.getSource() == btnAddVaccination) {
            anchorAddVaccination.toFront();
            keepButtonFocused(btnAddVaccination, buttons);
            getPets(usersList.get(0).getId(), petsList, tableViewPets, petVaccinationChoiceBox, petAppointmentChoiceBox);
            getDoctors(doctorsList, null, doctorVaccinationChoiceBox, doctorAppointmentChoiceBox);
            getVaccinationTypes(vaccinationTypesList, vaccinationTypeChoiceBox);
        } else if (e.getSource() == btnLogout) {
            logout(btnAdd);
            keepButtonFocused(btnAdd, buttons);
        }
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
                    insertPetStatement.setInt(4, usersList.get(0).getId());
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
                            clearAddAppointment();
                        });
                    } else {
                        Platform.runLater(() -> {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            DataVariables.setAlert(errorAlert, "Zapiasanie na wizytę", "Nie udało się zapisać na wizytę." +
                                    "Coś poszło nie tak!");
                            errorAlert.show();
                            clearAddAppointment();
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
                            clearAddVaccination();
                        });
                    } else {
                        Platform.runLater(() -> {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            DataVariables.setAlert(errorAlert, "Zapiasanie na szczepienie", "Nie udało się zapisać na szczepienie." +
                                    "Coś poszło nie tak!");
                            errorAlert.show();
                            clearAddVaccination();
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
    public void clearAddPet() {
        nameTextField.clear();
        datePickerPet.getEditor().clear();
        sexChoiceBox.getSelectionModel().clearSelection();
        typeChoiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    public void clearAddAppointment() {
        datePickerAppointment.getEditor().clear();
        petAppointmentChoiceBox.getSelectionModel().clearSelection();
        doctorAppointmentChoiceBox.getSelectionModel().clearSelection();
        purposeChoiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    public void clearAddVaccination() {
        datePickerVaccination.getEditor().clear();
        doctorVaccinationChoiceBox.getSelectionModel().clearSelection();
        petVaccinationChoiceBox.getSelectionModel().clearSelection();
        vaccinationTypeChoiceBox.getSelectionModel().clearSelection();
    }
}



