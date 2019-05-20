package com.dnajdrowski.DataStructure;

import com.dnajdrowski.Classes.*;
import com.dnajdrowski.Main;
import com.dnajdrowski.Services.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class Functions {

    public static void logout(Button button) {
        try {
            ButtonType buttonYes = new ButtonType("Tak", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonNo = new ButtonType("Nie", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", buttonYes, buttonNo);
            DataVariables.setAlert(alert, "Wylogowanie", "Czy na pewno chcesz się wylogować?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == buttonYes)) {
                DataVariables.email = "";
                Main.setRoot("login");
                Main.stage.setWidth(600);
                Main.stage.setHeight(800);
                Main.stage.centerOnScreen();
            } else {
                button.requestFocus();
                button.fire();
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void keepButtonFocused(Button keepButton, ArrayList<Button> buttons) {
        Platform.runLater(() -> {
            for (Button button : buttons) {
                if (button == keepButton) {
                    button.setStyle("-fx-border-width: 4 0 0 0; -fx-background-color: lightgrey; -fx-font-size: 17");
                } else {
                    button.setStyle("-fx-border-width: 4 4 0 0; -fx-background-color: white; -fx-font-size: 15");
                }
            }
        });
    }

    public static void deleteUser(User user, TableView<User> tableView) {
        if (user != null) {
            tableView.getItems().remove(user);
            new Thread(() -> {
                try {
                    PreparedStatement deleteUserStatement = Main.con.prepareStatement(DataVariables.DELETE_USER);
                    deleteUserStatement.setInt(1, user.getId());
                    deleteUserStatement.execute();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Usunięcie", "Pomyślnie usunięto " + user.getEmail() + ".");
                        alert.show();
                    });
                } catch (SQLException e) {
                    e.getMessage();
                }
            }).start();
        }

    }

    public static void deletePet(Pet pet, TableView<Pet> tableView) {
        if(pet != null) {
            tableView.getItems().remove(pet);
            new Thread(()->{
                try {
                    PreparedStatement deletePetStatement = Main.con.prepareStatement(DataVariables.DELETE_PET);
                    deletePetStatement.setInt(1, pet.getId());
                    deletePetStatement.execute();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Usunięcie", "Pomyślnie usunięto " + pet.getName() + ".");
                        alert.show();
                    });
                } catch (SQLException e) {
                    e.getMessage();
                }
            }).start();
        }
    }

    public static void deleteAppointment(Appointment appointment, TableView<Appointment> tableView) {
        if(appointment != null) {
            tableView.getItems().remove(appointment);
            new Thread(()->{
                try {

                    PreparedStatement deletePetStatement = Main.con.prepareStatement(DataVariables.DELETE_APPOINTMENT);
                    deletePetStatement.setInt(1, appointment.getId());
                    deletePetStatement.execute();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Usunięcie", "Pomyślnie usunięto wizytę " +
                                appointment.getPetName() + " " + appointment.getAppointmentDate().toString() + ".");
                        alert.show();
                    });
                } catch (SQLException e) {
                    e.getMessage();
                }
            }).start();
        }
    }

    public static void deleteVaccination(Vaccination vaccination, TableView<Vaccination> tableView) {
        if (vaccination != null) {
            tableView.getItems().remove(vaccination);
            new Thread(() -> {
                try {

                    PreparedStatement deletePetStatement = Main.con.prepareStatement(DataVariables.DELETE_VACCINATION);
                    deletePetStatement.setInt(1, vaccination.getId());
                    deletePetStatement.execute();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Usunięcie", "Pomyślnie usunięto szczepienie " +
                                vaccination.getPetName() + " " + vaccination.getVaccinationDate().toString() + ".");
                        alert.show();
                    });
                } catch (SQLException e) {
                    e.getMessage();
                }
            }).start();
        }
    }

    public static void getVaccinations(String email, ObservableList<Vaccination> vaccinationsList,
                                       TableView<Vaccination> tableView) {

        if (!vaccinationsList.isEmpty()) {
            vaccinationsList.clear();
        }

        VaccinationService vaccinationService;
        if (email == null)
            vaccinationService = new VaccinationService();
        else
            vaccinationService = new VaccinationService(email);

        vaccinationService.start();

        vaccinationService.setOnSucceeded(e -> {
            vaccinationsList.setAll(vaccinationService.getValue());
            tableView.setItems(vaccinationsList);
        });
    }

    public static void getPets(int id_user, ObservableList<Pet> petsList, TableView<Pet> tableView,
                               ChoiceBox<Pet> petVaccinationChoiceBox, ChoiceBox<Pet> petAppointmentChoiceBox) {

        if (!petsList.isEmpty()) {
            petsList.clear();
        }


        PetService petService;
        if (id_user == -1)
            petService = new PetService();
        else
            petService = new PetService(id_user);


        petService.start();

        petService.setOnSucceeded(e -> {
            petsList.setAll(petService.getValue());
            tableView.setItems(petsList);
            if (petVaccinationChoiceBox != null) {
                petVaccinationChoiceBox.setItems(petsList);
            }
            if (petAppointmentChoiceBox != null) {
                petAppointmentChoiceBox.setItems(petsList);
            }
        });

    }

    public static void getVaccinationTypes(ObservableList<VaccinationType> vaccinationTypesList,
                                           ChoiceBox<VaccinationType> vaccinationTypeChoiceBox) {

        if (!vaccinationTypesList.isEmpty()) {
            vaccinationTypesList.clear();
        }

        VaccinationTypeService vaccinationTypeService = new VaccinationTypeService();
        vaccinationTypeService.start();

        vaccinationTypeService.setOnSucceeded(e -> {
            vaccinationTypesList.setAll(vaccinationTypeService.getValue());
            vaccinationTypeChoiceBox.setItems(vaccinationTypesList);
        });
    }

    public static void getTypes(ObservableList<Type> typesList, ChoiceBox<Type> typeChoiceBox) {
        if (!typesList.isEmpty()) {
            typesList.clear();
        }

        TypeService typeService = new TypeService();
        typeService.start();

        typeService.setOnSucceeded(e -> {
            typesList.setAll(typeService.getValue());
            typeChoiceBox.setItems(typesList);
        });
    }


    public static void getDoctors(ObservableList<Doctor> doctorsList, TableView<Doctor> tableView,
                                  ChoiceBox<Doctor> doctorVaccinationChoiceBox, ChoiceBox<Doctor> doctorAppointmentChoiceBox) {
        if (!doctorsList.isEmpty()) {
            doctorsList.clear();
        }

        DoctorService doctorService = new DoctorService();
        doctorService.start();

        doctorService.setOnSucceeded(e -> {
            doctorsList.setAll(doctorService.getValue());
            if(tableView != null)
                tableView.setItems(doctorsList);
            if(doctorAppointmentChoiceBox != null)
                doctorAppointmentChoiceBox.setItems(doctorsList);
            if(doctorVaccinationChoiceBox != null)
                doctorVaccinationChoiceBox.setItems(doctorsList);
        });
    }

    public static void getAppointments(String email, ObservableList<Appointment> appointmentsList,
                                       TableView<Appointment> tableView) {

        if(!appointmentsList.isEmpty()) {
            appointmentsList.clear();
        }

        AppointmentService appointmentService;
        if(email == null)
           appointmentService = new AppointmentService();
        else
            appointmentService = new AppointmentService(email);

        appointmentService.start();

        appointmentService.setOnSucceeded(e -> {
            appointmentsList.setAll(appointmentService.getValue());
            tableView.setItems(appointmentsList);
        });


    }

    public static void getUsers(String email, ObservableList<User> userList, TableView<User> tableView) {

        if(!userList.isEmpty()) {
            userList.clear();
        }

        UserService userService;
        if(email == null)
            userService = new UserService();
        else
            userService = new UserService(email);
        userService.start();

        userService.setOnSucceeded(e -> {
            userList.setAll(userService.getValue());
            if(tableView != null)
                tableView.setItems(userList);
        });
    }

}

