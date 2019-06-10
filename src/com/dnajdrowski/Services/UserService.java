package com.dnajdrowski.Services;

import com.dnajdrowski.Main;
import com.dnajdrowski.Classes.Address;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Classes.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService extends Service<ObservableList<User>> {
    private final String query;
    private final String email;
    private final boolean flag;

    public UserService() {
        query = DataVariables.SELECT_WLASCICIEL;
        flag = false;
        email = null;
    }

    public UserService(String email) {
        query = DataVariables.SELECT_WLASCICIEL_BY_EMAIL;
        flag = true;
        this.email = email;
    }

    @Override
    protected Task<ObservableList<User>> createTask() {
        return new Task<>() {
            @Override
            protected ObservableList<User> call() {
                ObservableList<User> users = FXCollections.observableArrayList();

                try (PreparedStatement userStatement = Main.con.prepareStatement(query)) {

                    if(flag) {
                        userStatement.setString(1, email);
                    }

                    ResultSet resultSet = userStatement.executeQuery();
                    while (resultSet.next()) {
                        users.add(new User(resultSet.getInt("id_wlasciciel"),
                                resultSet.getString("imie"),
                                resultSet.getString("nazwisko"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("telefon"),
                                resultSet.getString("pesel"),
                                resultSet.getDate("data_ur"),
                                new Address(resultSet.getInt("id_adres"),
                                        resultSet.getString("miasto"),
                                        resultSet.getString("kod_pocz"),
                                        resultSet.getString("ulica"),
                                        resultSet.getString("nr_domu"))
                        ));
                    }
                    return users;

                } catch (SQLException e) {
                    e.getMessage();
                    return null;
                }
            }
        };
    }
}
