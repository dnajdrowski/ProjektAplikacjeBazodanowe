package com.dnajdrowski.Services;

import com.dnajdrowski.Classes.*;
import com.dnajdrowski.Main;
import com.dnajdrowski.DataStructure.DataVariables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentService extends Service<ObservableList<Appointment>> {
    private final String query;
    private final boolean flag;
    private final String email;

    public AppointmentService() {
        this.query = DataVariables.SELECT_APPOINTMENTS;
        this.email = "";
        this.flag = false;
    }

    public AppointmentService(String email) {
        this.email = email;
        this.flag = true;
        this.query = DataVariables.SELECT_APPOINTMENTS_BY_EMAIL;
    }

    @Override
    protected Task<ObservableList<Appointment>> createTask() {
        return new Task<>() {
            @Override
            protected ObservableList<Appointment> call() {
                ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

                try (PreparedStatement appointmentsStatement = Main.con.prepareStatement(query)) {

                    if (flag) {
                        appointmentsStatement.setString(1, email);
                        appointmentsStatement.setString(2, email);
                    }

                    ResultSet resultSet = appointmentsStatement.executeQuery();

                    while (resultSet.next()) {
                        appointmentList.add(new Appointment(resultSet.getInt("id_wizyty"),
                                new Pet(resultSet.getInt("id_zwierze"),
                                        resultSet.getString("nazwa_zwierze"),
                                        resultSet.getString("plec"),
                                        resultSet.getDate("data_ur_zwierz"),
                                        new Type(resultSet.getInt("id_gatunek"),
                                                resultSet.getString("nazwa_gatunek")),
                                        new User(resultSet.getInt("id_wlasciciel"),
                                                resultSet.getString("w.imie"),
                                                resultSet.getString("w.nazwisko"))
                                        ),
                                new Doctor(resultSet.getInt("id_lekarz"),
                                        resultSet.getString("l.imie"),
                                        resultSet.getString("l.nazwisko")),
                                resultSet.getString("cel_wizyty"),
                                resultSet.getDouble("cena"),
                                resultSet.getDate("data_wizyty"))
                        );
                    }

                    return appointmentList;

                } catch (SQLException e) {
                    e.getMessage();
                    return null;
                }
            }
        };
    }
}
