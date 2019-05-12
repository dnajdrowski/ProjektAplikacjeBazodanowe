package com.dnajdrowski.data;

import java.util.regex.Pattern;

public class DataVariables {

    public static final Pattern passwordPattern = Pattern.compile("((?=.*[a-z])(?=.*d)(?=.*[@#$%!?])(?=.*[A-Z]).{8,})",
            Pattern.CASE_INSENSITIVE);

    public static final String checkWlasiciel = "SELECT * FROM $tableName WHERE email = ?";


    public static final String TABLE_WLASCICIEL = "wlasciciel";
    public static final String TABLE_LEKARZ = "lekarz";
    public static final String TABLE_RECEPCJONISTA = "recepcjonista";

}
