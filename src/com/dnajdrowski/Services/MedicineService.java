package com.dnajdrowski.Services;

import com.dnajdrowski.Classes.Medicine;
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

public class MedicineService extends Service<ObservableList<Medicine>> {
    @Override
    protected Task<ObservableList<Medicine>> createTask() {
        return new Task<>() {
            @Override
            protected ObservableList<Medicine> call() {
                ObservableList<Medicine> medicines = FXCollections.observableArrayList();

                try (PreparedStatement medicinesStatement = Main.con.prepareStatement(DataVariables.SELECT_MEDICINES)) {
                    ResultSet resultSet = medicinesStatement.executeQuery();

                    while(resultSet.next()) {
                        medicines.add(new Medicine(resultSet.getInt("id_lek"),
                                resultSet.getString("nazwa_lek"),
                                resultSet.getString("dawka"),
                                resultSet.getString("postac")));
                    }
                    return medicines;
                } catch (SQLException e) {
                    e.getMessage();
                    return null;
                }
            }
        };
    }
}
