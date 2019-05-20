package com.dnajdrowski.Services;

import com.dnajdrowski.Classes.*;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VaccinationService extends Service<ObservableList<Vaccination>> {
    private final String query;
    private final boolean flag;
    private final String email;

    public VaccinationService() {
        this.query = DataVariables.SELECT_VACCINATION;
        this.email = "";
        this.flag = false;
    }

    public VaccinationService(String email) {
        this.email = email;
        this.flag = true;
        this.query = DataVariables.SELECT_VACCINATION_BY_EMAIL;
    }


    @Override
    protected Task<ObservableList<Vaccination>> createTask() {
        return new Task<>() {
            @Override
            protected ObservableList<Vaccination> call() throws Exception {
                ObservableList<Vaccination> vaccinations = FXCollections.observableArrayList();

                try (PreparedStatement vaccinationStatement = Main.con.prepareStatement(query)) {

                    if(flag) {
                        vaccinationStatement.setString(1, email);
                        vaccinationStatement.setString(2, email);
                    }

                    ResultSet resultSet = vaccinationStatement.executeQuery();

                    while (resultSet.next()) {
                        vaccinations.add(new Vaccination(resultSet.getInt("id_szczepienie"),
                                resultSet.getDate("data_szczepienia"),
                                new Pet(resultSet.getInt("id_zwierze"),
                                        resultSet.getString("nazwa_zwierze"),
                                        new User(resultSet.getInt("id_wlasciciel"),
                                                resultSet.getString("w.imie"),
                                                resultSet.getString("w.nazwisko"))),
                                new Doctor(resultSet.getInt("id_lekarz"),
                                        resultSet.getString("l.imie"),
                                        resultSet.getString("l.nazwisko")),
                                new VaccinationType(resultSet.getInt("id_typ_szczepienia"),
                                        resultSet.getString("nazwa_typu"),
                                        resultSet.getDouble("cena"))));
                    }

                    return vaccinations;
                } catch (SQLException e) {
                    e.getMessage();
                    return null;
                }
            }
        };
    }
}
