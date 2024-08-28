package com.example.demo;

import java.sql.*;

public class Connect {
    private Connection connection;

    public void connectDB() {
        String URL = "jdbc:sqlite:baza_zamowien.db";
        try {
            connection = DriverManager.getConnection(URL);
            Statement stmt = connection.createStatement();
            System.out.println("successful");
            // Zapytanie do sqlite_master, aby uzyskać nazwy tabel
            String sql = "SELECT * FROM pozycja_zamowienie";

            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs);

            // Zapytanie do sqlite_master, aby uzyskać nazwy tabel
            String sqla   = "SELECT name FROM sqlite_master WHERE type='table'";
            rs = stmt.executeQuery(sqla);

            // Wyświetlanie nazw tabel
            while (rs.next()) {
                System.out.println("Table name: " + rs.getString("name"));
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeconnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("closed");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
