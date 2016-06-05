package me.tiliondc.atu.Database;


import me.tiliondc.atu.ATilionUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB {

    Connection con;
    Statement stmt;
    ATilionUtilities plugin;

    public SQLiteDB(ATilionUtilities plugin) {

        try {

            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:test.db");
            stmt = con.createStatement();
            plugin.getLogger().info("Connected successfully to database");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


}
