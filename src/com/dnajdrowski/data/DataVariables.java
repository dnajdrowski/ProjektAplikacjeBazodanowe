package com.dnajdrowski.data;

import com.dnajdrowski.Main;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Modality;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Pattern;

public class DataVariables {

    //PATTERNS
    public static final Pattern passwordPattern = Pattern.compile("((?=.*[a-z])(?=.*d)(?=.*[@#$%!?])(?=.*[A-Z]).{8,})",
            Pattern.CASE_INSENSITIVE);
    public static final Pattern phonePattern = Pattern.compile("[0-9]{9}", Pattern.CASE_INSENSITIVE);
    public static final Pattern peselPattern = Pattern.compile("[1-9][0-9]{10}", Pattern.CASE_INSENSITIVE);
    public static final Pattern codePattern = Pattern.compile("[0-9]{2}[-][0-9]{3}", Pattern.CASE_INSENSITIVE);
    public static final Pattern numberPattern = Pattern.compile("[a-zA-z0-9]{1,5}", Pattern.CASE_INSENSITIVE);
    public static final Pattern datePattern = Pattern.compile("[1-9][0-9]{3}[-][0-1][0-9][-][0-3][0-9]");



    //DATABASE_QUERYS
    //TABLES
    public static final String TABLE_WLASCICIEL = "wlasciciel";
    public static final String TABLE_LEKARZ = "lekarz";
    public static final String TABLE_RECEPCJONISTA = "recepcjonista";


    //SELECTS
    public static final String SELECT_WLASCICIEL = "SELECT * FROM wlasciciel INNER JOIN adres a ON " +
            "wlasciciel.id_adres = a.id_adres;";


    //CHECKS
    public static final String checkUniqueValue = "SELECT * FROM wartosci_unikatowe WHERE wartosci_unikatowe = ?";
    public static final String checkTableByEmail = "SELECT * FROM $tableName WHERE email = ?";

    //FUNCTIONS
    public static final String functionAddUser = "CALL dodaj_wlasciciela(?,?,?,?,?,?,?,?,?,?,?)";



    //walidacja emailu
    public static boolean isValidEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    //walidacja danych
    public static boolean isValidToRegex(String text, Pattern pattern) {
        boolean result = true;
        if (!pattern.matcher(text).matches()) {
            result = false;
        }
        return result;
    }

    public static void setAlert(Alert alert, String title, String context) {
        alert.initOwner(Main.stage);
        alert.setResizable(false);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setContentText(context);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setStyle("-fx-font-size: 16; -fx-font-family: 'Times New Roman Bold'");
        alert.setHeaderText(null);
    }


}
