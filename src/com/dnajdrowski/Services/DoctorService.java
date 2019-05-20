package com.dnajdrowski.Services;

import com.dnajdrowski.Classes.Adress;
import com.dnajdrowski.Classes.Doctor;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DoctorService extends Service<ObservableList<Doctor>> {


    @Override
    protected Task<ObservableList<Doctor>> createTask() {
        return new Task<>() {
            @Override
            protected ObservableList<Doctor> call() {
                ObservableList<Doctor> doctors = FXCollections.observableArrayList();

                try(Statement doctorStatement = Main.con.createStatement()) {
                    ResultSet resultSet = doctorStatement.executeQuery(DataVariables.SELECT_DOCTOR);

                    while(resultSet.next()) {
                        doctors.add(new Doctor(resultSet.getInt("id_lekarz"),
                                resultSet.getString("imie"),
                                resultSet.getString("nazwisko"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("telefon"),
                                resultSet.getString("pesel"),
                                resultSet.getDate("data_ur_lekarz"),
                                //TODO
                                //zmienic literowke nizej specjalizajca
                                resultSet.getString("specjalizajca"),
                                new Adress(resultSet.getInt("id_adres"),
                                        resultSet.getString("miasto"),
                                        resultSet.getString("kod_pocz"),
                                        resultSet.getString("ulica"),
                                        resultSet.getString("nr_domu")))
                        );
                    }

                    return doctors;
                } catch (SQLException e) {
                    e.getMessage();
                    return null;
                }
            }
        };
    }
}
