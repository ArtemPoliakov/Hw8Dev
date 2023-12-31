package com.homework;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try(Connection connection = ConfiguredDataSource.getPool().getConnection();) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}