package com.homework;

public class DbConfig {

    private static final String DB_URL= "jdbc:h2:mem:office;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1;";
    private static final String DB_USER = "user-name";
    private static final String DB_PASSWORD = "strong-password";

    private DbConfig() {}

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPassword() {
        return DB_PASSWORD;
    }
}