package com.dnajdrowski.Services;

import com.dnajdrowski.Classes.Sickness;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SicknessService extends Service<ObservableList<Sickness>> {
    @Override
    protected Task<ObservableList<Sickness>> createTask() {
        return new Task<>() {
            @Override
            protected ObservableList<Sickness> call() {
                ObservableList<Sickness> sicknesses = FXCollections.observableArrayList();
                try (PreparedStatement vaccinationTypeStatement = Main.con.prepareStatement(DataVariables.SELECT_SICKNESSES)) {
                    ResultSet resultSet = vaccinationTypeStatement.executeQuery();

                    while(resultSet.next()) {
                        sicknesses.add(new Sickness(resultSet.getInt("id_choroba"),
                                resultSet.getString("nazwa_choroba")));
                    }
                    return sicknesses;
                } catch (SQLException e) {
                    e.getMessage();
                    return null;
                }
            }
        };
    }
}
