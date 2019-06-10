package com.dnajdrowski.DataStructure;

import com.dnajdrowski.Classes.*;
import com.dnajdrowski.Controllers.UserDetailsController;
import com.dnajdrowski.Main;
import com.dnajdrowski.Services.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
                DataVariables.userType = "";
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

    public static void deleteVaccinationType(VaccinationType type,  TableView<VaccinationType> tv) {
        if(type != null) {
            tv.getItems().remove(type);
            new Thread(()->{
                try {
                    PreparedStatement deletePetStatement = Main.con.prepareStatement(DataVariables.DELETE_VACCINATION_TYPE);
                    deletePetStatement.setInt(1, type.getId());
                    deletePetStatement.execute();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Usunięcie", "Pomyślnie usunięto " + type.getName() + ".");
                        alert.show();
                    });
                } catch (SQLException e) {
                    e.getMessage();
                }
            }).start();
        }
    }

    public static void deleteType(Type type,  TableView<Type> tv) {
        if(type != null) {
            tv.getItems().remove(type);
            new Thread(()->{
                try {
                    PreparedStatement deletePetStatement = Main.con.prepareStatement(DataVariables.DELETE_TYPE);
                    deletePetStatement.setInt(1, type.getId());
                    deletePetStatement.execute();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Usunięcie", "Pomyślnie usunięto " + type.getName() + ".");
                        alert.show();
                    });
                } catch (SQLException e) {
                    e.getMessage();
                }
            }).start();
        }
    }

    public static void deleteSicnkess(Sickness type,  TableView<Sickness> tv) {
        if(type != null) {
            tv.getItems().remove(type);
            new Thread(()->{
                try {
                    PreparedStatement deletePetStatement = Main.con.prepareStatement(DataVariables.DELETE_SICKNESS);
                    deletePetStatement.setInt(1, type.getId());
                    deletePetStatement.execute();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Usunięcie", "Pomyślnie usunięto " + type.getName() + ".");
                        alert.show();
                    });
                } catch (SQLException e) {
                    e.getMessage();
                }
            }).start();
        }
    }

    public static void deleteMedicine(Medicine type,  TableView<Medicine> tv) {
        if(type != null) {
            tv.getItems().remove(type);
            new Thread(()->{
                try {
                    PreparedStatement deletePetStatement = Main.con.prepareStatement(DataVariables.DELETE_MEDICINE);
                    deletePetStatement.setInt(1, type.getId());
                    deletePetStatement.execute();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Usunięcie", "Pomyślnie usunięto " + type.getName() + ".");
                        alert.show();
                    });
                } catch (SQLException e) {
                    e.getMessage();
                }
            }).start();
        }
    }

    public static void updateAppointment(Appointment appointment, String query, String result) {
        if(appointment != null) {
            new Thread(()->{
                try {
                    PreparedStatement updateAppointmentStatement = Main.con.prepareStatement(query);

                    if(query.equals(DataVariables.UPDATE_MEDICINE_APPOINTMENT) || query.equals(DataVariables.UPDATE_SICKNESS_APPOINTMENT)){
                        updateAppointmentStatement.setInt(1, Integer.parseInt(result));
                    } else {
                            updateAppointmentStatement.setDouble(1, Double.parseDouble(result));
                            appointment.setPrice(Double.parseDouble(result));
                    }
                    updateAppointmentStatement.setInt(2, appointment.getId());
                    updateAppointmentStatement.execute();
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        DataVariables.setAlert(alert, "Edycja", "Pomyślnie edytowano wizytę.");
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

    public static void getVaccinations(String email, TableView<Vaccination> tableView) {

        if (!tableView.getItems().isEmpty()) {
            tableView.getItems().clear();
        }

        VaccinationService vaccinationService;
        if (email == null)
            vaccinationService = new VaccinationService();
        else
            vaccinationService = new VaccinationService(email);

        vaccinationService.start();

        vaccinationService.setOnSucceeded(e -> {
            tableView.setItems(vaccinationService.getValue());
            tableView.setVisible(true);
        });
    }

    public static void getPets(int id_user, TableView<Pet> tableView,
                               ChoiceBox<Pet> petChoiceBox) {

        if ((tableView != null ) && (!tableView.getItems().isEmpty())) {
            tableView.getItems().clear();
        }


        PetService petService;
        if (id_user == -1)
            petService = new PetService();
        else
            petService = new PetService(id_user);


        petService.start();

        petService.setOnSucceeded(e -> {
            if(tableView != null) {
                tableView.setItems(petService.getValue());
                tableView.setVisible(true);
            }
            if (petChoiceBox != null) {
                petChoiceBox.setItems(petService.getValue());
            }
        });

    }

    public static void getVaccinationTypes(ChoiceBox<VaccinationType> vaccinationTypeChoiceBox) {

        if (!vaccinationTypeChoiceBox.getItems().isEmpty()) {
            vaccinationTypeChoiceBox.getItems().clear();
        }

        VaccinationTypeService vaccinationTypeService = new VaccinationTypeService();
        vaccinationTypeService.start();

        vaccinationTypeService.setOnSucceeded(e ->
                vaccinationTypeChoiceBox.setItems(vaccinationTypeService.getValue()));
    }

    public static void getVaccinationTypes(TableView vaccinationTypeChoiceBox) {

        if (!vaccinationTypeChoiceBox.getItems().isEmpty()) {
            vaccinationTypeChoiceBox.getItems().clear();
        }

        VaccinationTypeService vaccinationTypeService = new VaccinationTypeService();
        vaccinationTypeService.start();

        vaccinationTypeService.setOnSucceeded(e ->
                vaccinationTypeChoiceBox.setItems(vaccinationTypeService.getValue()));
    }

    public static void getSicknesses(ObservableList<Sickness> sicknessesList) {

        if (!sicknessesList.isEmpty()) {
            sicknessesList.clear();
        }

        SicknessService sicknessService = new SicknessService();
        sicknessService.start();

        sicknessService.setOnSucceeded(e -> {
            sicknessesList.setAll(sicknessService.getValue());
        });
    }

    public static void getMedicines(ObservableList<Medicine> medicinesList) {

        if (!medicinesList.isEmpty()) {
            medicinesList.clear();
        }

        MedicineService medicineService = new MedicineService();
        medicineService.start();

        medicineService.setOnSucceeded(e -> {
            medicinesList.setAll(medicineService.getValue());
        });
    }

    public static void getTypes(ChoiceBox<Type> typeChoiceBox) {
        if (!typeChoiceBox.getItems().isEmpty()) {
            typeChoiceBox.getItems().clear();
        }

        TypeService typeService = new TypeService();
        typeService.start();

        typeService.setOnSucceeded(e -> typeChoiceBox.setItems(typeService.getValue()));
    }

    public static void getTypes(TableView types) {
        if (!types.getItems().isEmpty()) {
            types.getItems().clear();
        }

        TypeService typeService = new TypeService();
        typeService.start();

        typeService.setOnSucceeded(e -> types.setItems(typeService.getValue()));
    }

    public static void getDoctors(TableView<Doctor> tableView,
                                  ChoiceBox<Doctor> doctorChoiceBox) {

        if ((tableView != null) && (!tableView.getItems().isEmpty())) {
            tableView.getItems().clear();
        }

        DoctorService doctorService = new DoctorService();
        doctorService.start();

        doctorService.setOnSucceeded(e -> {
            if(tableView != null) {
                tableView.setItems(doctorService.getValue());
                tableView.setVisible(true);
            }
            if(doctorChoiceBox != null)
                doctorChoiceBox.setItems(doctorService.getValue());
        });
    }

    public static void getAppointments(String email, TableView<Appointment> tableView) {

        if(!tableView.getItems().isEmpty()) {
            tableView.getItems().clear();
        }

        AppointmentService appointmentService;
        if(email == null)
           appointmentService = new AppointmentService();
        else
            appointmentService = new AppointmentService(email);

        appointmentService.start();

        appointmentService.setOnSucceeded(e -> {
            tableView.setItems(appointmentService.getValue());
            tableView.setVisible(true);
        });


    }

    public static void getUsers(String email, TableView<User> tableView) {

        if(!tableView.getItems().isEmpty()) {
            tableView.getItems().clear();
        }

        UserService userService;
        if(email == null)
            userService = new UserService();
        else
            userService = new UserService(email);
        userService.start();

        userService.setOnSucceeded(e -> {
            tableView.setItems(userService.getValue());
            tableView.setVisible(true);
        });
    }

    public static void getUser(String email, List<User> user) {

        UserService userService;
        if(email == null)
            userService = new UserService();
        else
            userService = new UserService(email);
        userService.start();

        userService.setOnSucceeded(e -> user.add(userService.getValue().get(0)));

    }

    public static void showUserDetails(User user) {
        if(user == null) {
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(Main.stage);
        dialog.setTitle("Kliniczka by dnajdrowski");
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("POWRÓT",ButtonBar.ButtonData.CANCEL_CLOSE));

        try {
            FXMLLoader loader = Main.loadFXML("userDetailsDialog");
            dialog.getDialogPane().setContent(loader.load());
            UserDetailsController controller = loader.getController();
            controller.setUser(user);
        } catch (IOException e) {
            e.getMessage();
        }

        dialog.showAndWait();
    }

    public static void setAnchorPane(HBox hBox, String fxml) {
        Platform.runLater(() -> {
            try {
                if (hBox.getChildren().size() == 1) {
                    hBox.getChildren().add(1, Main.loadFXML(fxml + "AnchorPane").load());
                } else {
                    hBox.getChildren().set(1, Main.loadFXML(fxml + "AnchorPane").load());
                }
            } catch (IOException e) {
                e.getMessage();
            }
        });
    }
}

