package com.dnajdrowski.Controllers;

import com.dnajdrowski.Classes.Address;
import com.dnajdrowski.Classes.User;
import com.dnajdrowski.DataStructure.DataVariables;
import com.dnajdrowski.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetailsController {

    @FXML
    private Label firstNameLabel, lastNameLabel, emailLabel, phoneNumberLabel,
            peselNumberLabel, birthDateLabel;

    @FXML
    private TextField cityTextField, postCodeTextField, streetNameTextField, houseNumberTextField;

    private User user;


    public void initialize() {

        Platform.runLater(()->{
            firstNameLabel.setText(user.getFirstName());
            lastNameLabel.setText(user.getLastName());
            emailLabel.setText(user.getEmail());
            phoneNumberLabel.setText(user.getPhoneNumber());
            peselNumberLabel.setText(user.getPeselNumber());
            birthDateLabel.setText(user.getBirthDate().toString());
            cityTextField.setText(user.getAddress().getCity());
            postCodeTextField.setText(user.getAddress().getPostCode());
            streetNameTextField.setText(user.getAddress().getStreet());
            houseNumberTextField.setText(user.getAddress().getHouseNumber());
        });
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void changeUserAddress() {
        String message = checkAddressDetails();

        System.out.println(message);
        if(!message.isEmpty())  {
            String errorMessage = "Poniższe dane zostały wprowadzone błędnie:\n" + message;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            DataVariables.setAlert(alert,"Błędne dane!", errorMessage);
            alert.show();
            return;
        } else {
            try (PreparedStatement updateAddressStatement = Main.con.prepareStatement(DataVariables.functionChangeAdress)) {
                updateAddressStatement.setString(1, cityTextField.getText());
                updateAddressStatement.setString(2, postCodeTextField.getText());
                updateAddressStatement.setString(3, streetNameTextField.getText());
                updateAddressStatement.setString(4, houseNumberTextField.getText());
                updateAddressStatement.setInt(5, user.getId());

                ResultSet resultSet = updateAddressStatement.executeQuery();

                resultSet.next();
                user.setAddress(new Address(resultSet.getInt(1),
                        cityTextField.getText(),
                        postCodeTextField.getText(),
                        streetNameTextField.getText(),
                        houseNumberTextField.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                DataVariables.setAlert(alert, "Zmiana adresu", "Pomyślnie zmieniono adres!");
                alert.show();
            } catch (SQLException e) {
                e.getMessage();
            }
        }

    }

    private String checkAddressDetails() {
        boolean validCode = DataVariables.isValidToRegex(postCodeTextField.getText(), DataVariables.codePattern);
        boolean validNumber = DataVariables.isValidToRegex(houseNumberTextField.getText(), DataVariables.numberPattern);
        boolean validCity = cityTextField.getText().trim().isEmpty();
        boolean validStreetName = streetNameTextField.getText().trim().isEmpty();


        String errorMessage = "";

        if(!validCode) {
            errorMessage += "kod pocztowy\n";
        }
        if(!validNumber) {
            errorMessage += "numer domu\n";
        }
        if(validCity) {
            errorMessage += "miasto\n";
        }
        if(validStreetName) {
            errorMessage += "ulica\n";
        }

        return errorMessage.trim();

    }
}
