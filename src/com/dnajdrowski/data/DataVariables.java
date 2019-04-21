package com.dnajdrowski.data;

import java.util.regex.Pattern;

public class DataVariables {


    //PATTERNS
    public static final Pattern passwordPattern = Pattern.compile("((?=.*[a-z])(?=.*d)(?=.*[@#$%!?])(?=.*[A-Z]).{8,})",
            Pattern.CASE_INSENSITIVE);

}
