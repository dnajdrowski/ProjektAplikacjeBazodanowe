package com.dnajdrowski.Services;

import com.dnajdrowski.Classes.Type;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TypeService extends Service<ObservableList<Type>> {
    @Override
    protected Task<ObservableList<Type>> createTask() {
        return new Task<>() {
            @Override
            protected ObservableList<Type> call(){
                ObservableList<Type> types = FXCollections.observableArrayList();

                try (Statement typesStatement = Main.con.createStatement()) {
                    ResultSet resultSet = typesStatement.executeQuery(DataVariables.SELECT_GATUNEK);
                    while (resultSet.next()) {
                        types.add(new Type(resultSet.getInt("id_gatunek"),
                                resultSet.getString("nazwa_gatunek")));
                    }

                    return types;
                } catch (SQLException e) {
                    e.getMessage();
                    return null;
                }
            }
        };
    }
}
