package com.dnajdrowski.Services;

import com.dnajdrowski.Classes.VaccinationType;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VaccinationTypeService extends Service<ObservableList<VaccinationType>> {
    @Override
    protected Task<ObservableList<VaccinationType>> createTask() {
        return new Task<>() {
            @Override
            protected ObservableList<VaccinationType> call() {

                ObservableList<VaccinationType> vacciantionTypes = FXCollections.observableArrayList();

                try (PreparedStatement vaccinationTypeStatement = Main.con.prepareStatement(DataVariables.SELECT_VACCINATION_TYPE)) {
                    ResultSet resultSet = vaccinationTypeStatement.executeQuery();

                    while(resultSet.next()) {
                        vacciantionTypes.add(new VaccinationType(resultSet.getInt("id_typ_szczepienia"),
                                resultSet.getString("nazwa_typu"),
                                resultSet.getDouble("cena")));
                    }
                    return vacciantionTypes;

                } catch (SQLException e) {
                    e.getMessage();
                    return null;
                }

            }
        };
    }
}
