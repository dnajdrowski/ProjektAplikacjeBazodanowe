package com.dnajdrowski.DataStructure;

import com.dnajdrowski.Main;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Modality;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Pattern;

public class DataVariables {

    //PATTERNS
    public static final Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?])(?=\\S+$).{8,}$",
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

    //UPDATES
    public static final String UPDATE_PRICE_APPOINTMENT = "UPDATE wizyta SET cena = ? WHERE id_wizyty = ?";
    public static final String UPDATE_SICKNESS_APPOINTMENT = "UPDATE wizyta SET id_choroba = ? WHERE id_wizyty = ?";
    public static final String UPDATE_MEDICINE_APPOINTMENT = "UPDATE wizyta SET id_lek = ? WHERE id_wizyty = ?";


    //SELECTS
    public static final String SELECT_WLASCICIEL = "SELECT * FROM wlasciciel INNER JOIN adres a ON " +
            "wlasciciel.id_adres = a.id_adres;";
    public static final String SELECT_DOCTOR = "SELECT * FROM lekarz INNER JOIN adres a ON lekarz.id_adres = a.id_adres;";
    public static final String SELECT_GATUNEK = "SELECT * FROM gatunek;";
    public static final String SELECT_WLASCICIEL_BY_EMAIL = "SELECT * FROM wlasciciel INNER JOIN adres a ON " +
            "wlasciciel.id_adres = a.id_adres WHERE email = ?";
    public static final String SELECT_PETS = "SELECT * FROM zwierze INNER JOIN gatunek g ON zwierze.id_gatunek=g.id_gatunek inner join wlasciciel w on zwierze.id_wlasciciel = w.id_wlasciciel";
    public static final String SELECT_SICKNESSES = "SELECT * FROM choroba";
    public static final String SELECT_MEDICINES = "SELECT * FROM lek";
    public static final String SELECT_PETS_BY_USER_ID = "SELECT * FROM zwierze INNER JOIN gatunek g ON zwierze.id_gatunek=\n" +
            "                      g.id_gatunek INNER JOIN wlasciciel w on zwierze.id_wlasciciel = w.id_wlasciciel WHERE w.id_wlasciciel = ?";
    public static final String SELECT_APPOINTMENTS = "SELECT * FROM wizyta INNER JOIN lekarz l ON wizyta.id_lekarz = " +
            "l.id_lekarz INNER JOIN zwierze z ON wizyta.id_zwierze = z.id_zwierze\n" +
            "INNER JOIN wlasciciel w ON z.id_wlasciciel = w.id_wlasciciel INNER JOIN gatunek g on z.id_gatunek = g.id_gatunek;";
    public static final String SELECT_APPOINTMENTS_BY_EMAIL = "SELECT * FROM wizyta INNER JOIN lekarz l ON wizyta.id_lekarz = l.id_lekarz INNER JOIN zwierze z ON wizyta.id_zwierze = z.id_zwierze\n" +
            "INNER JOIN wlasciciel w ON z.id_wlasciciel = w.id_wlasciciel INNER JOIN gatunek g on z.id_gatunek = g.id_gatunek WHERE w.email=? or l.email= ?;";
    public static final String SELECT_VACCINATION = "SELECT * FROM szczepienie INNER JOIN lekarz l on szczepienie.id_lekarz " +
            "= l.id_lekarz INNER JOIN zwierze z on szczepienie.id_zwierze = z.id_zwierze\n" +
            "INNER JOIN typ_szczepienia ts on szczepienie.id_typ_szczepienia = ts.id_typ_szczepienia;";
    public static final String SELECT_VACCINATION_BY_EMAIL = "SELECT * FROM szczepienie INNER JOIN lekarz l on szczepienie.id_lekarz = l.id_lekarz INNER JOIN zwierze z on szczepienie.id_zwierze = z.id_zwierze\n" +
            "INNER JOIN typ_szczepienia ts on szczepienie.id_typ_szczepienia = ts.id_typ_szczepienia INNER JOIN wlasciciel w on z.id_wlasciciel = w.id_wlasciciel WHERE w.email = ? or l.email = ?;";
    public static final String SELECT_VACCINATION_TYPE = "SELECT * FROM typ_szczepienia;";


    //CHECKS
    public static final String checkUniqueValue = "SELECT * FROM wartosci_unikatowe WHERE wartosci_unikatowe = ?";
    public static final String checkTableByEmail = "SELECT * FROM $tableName WHERE email = ?";

    //FUNCTIONS
    public static final String functionAddUser = "CALL dodaj_wlasciciela(?,?,?,?,?,?,?,?,?,?,?)";
    public static final String functionChangeAdress = "SELECT zmien_adres_wlasciciela(?,?,?,?,?)";

    //INSERTS
    public static final String INSERT_PET = "INSERT INTO zwierze(nazwa_zwierze,plec,data_ur_zwierz,id_wlasciciel,id_gatunek) VALUE " +
            "(?,?,?,?,?)";
    public static final String INSERT_APPOINTMENT = "INSERT INTO wizyta(data_wizyty, id_zwierze, id_lekarz, cel_wizyty) VALUE" +
            "(?,?,?,?)";
    public static final String INSERT_VACCINATION = "INSERT INTO szczepienie(data_szczepienia, id_zwierze, id_lekarz, id_typ_szczepienia)" +
            "VALUE (?,?,?,?)";


    //DELETING
    public static final String DELETE_USER = "DELETE FROM wlasciciel where id_wlasciciel=?";
    public static final String DELETE_PET = "DELETE FROM zwierze where id_zwierze=?";
    public static final String DELETE_APPOINTMENT = "DELETE FROM wizyta where id_wizyty=?";
    public static final String DELETE_VACCINATION = "DELETE FROM szczepienie where id_szczepienie=?";
    public static final String DELETE_MEDICINE ="DELETE FROM lek where id_lek =?";
    public static final String DELETE_VACCINATION_TYPE ="DELETE FROM typ_szczepienia where id_typ_szczepienia =?";
    public static final String DELETE_SICKNESS ="DELETE FROM choroba where id_choroba";
    public static final String DELETE_TYPE ="DELETE FROM gatunek where id_gatunek =?";



    //ACTUAL EMAIL LOGGED IN
    public static String email;
    public static String userType;

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
