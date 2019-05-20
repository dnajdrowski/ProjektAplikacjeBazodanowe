package com.dnajdrowski.Services;

import com.dnajdrowski.Classes.Pet;
import com.dnajdrowski.Classes.Type;
import com.dnajdrowski.Classes.User;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetService extends Service<ObservableList<Pet>> {
    final private String query;
    final private boolean flag;
    final private int id;

    public PetService() {
        this.query = DataVariables.SELECT_PETS;
        this.flag = false;
        this.id = -1;
    }

    public PetService(int id) {
        this.query = DataVariables.SELECT_PETS_BY_USER_ID;
        this.flag = true;
        this.id = id;
    }

    @Override
    protected Task<ObservableList<Pet>> createTask() {
        return new Task<>() {
            @Override
            protected ObservableList<Pet> call() throws Exception {
                ObservableList<Pet> pets = FXCollections.observableArrayList();

                try (PreparedStatement selectPets = Main.con.prepareStatement(query)) {

                    if(flag) {
                        selectPets.setInt(1, id);
                    }

                    ResultSet resultSet = selectPets.executeQuery();

                    while (resultSet.next()) {
                        pets.add(new Pet(resultSet.getInt("id_zwierze"),
                                resultSet.getString("nazwa_zwierze"),
                                resultSet.getString("plec"),
                                resultSet.getDate("data_ur_zwierz"),
                                new Type(resultSet.getInt("id_gatunek"),
                                        resultSet.getString("nazwa_gatunek")),
                                new User(resultSet.getInt("id_wlasciciel"),
                                        resultSet.getString("imie"),
                                        resultSet.getString("nazwisko"))
                                )
                        );
                    }
                    return pets;

                } catch (SQLException e) {
                    e.getMessage();
                    return null;
                }
            }
        };
    }
}
