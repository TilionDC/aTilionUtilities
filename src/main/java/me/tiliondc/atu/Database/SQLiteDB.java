package me.tiliondc.atu.database;


import me.tiliondc.atu.ATilionUtilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDB {

    Connection con;
    Statement stmt;
    ATilionUtilities plugin;

    public SQLiteDB(ATilionUtilities plugin) {

        this.plugin = plugin;

        try {

            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/atu.db");
            stmt = con.createStatement();
            plugin.getLogger().info("Connected successfully to database");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    public boolean createTable(String tablename, int keys, int values) {
        if(keys < 1) keys = 1;

        String syntax = "CREATE TABLE IF NOT EXISTS " + tablename + " ( ";

        for(int i = 0; i < keys; i++) {
            syntax = syntax + "key" + i + " var_char(63) not null, ";
        }
        for(int i = 0; i < values; i++) {
            syntax = syntax + "value" + i + " var_char(255), ";
        }
        syntax = syntax + "PRIMARY KEY ( key0";

        for(int i = 1; i < keys; i++) {
            syntax = syntax + ",key" + i;
        }
        syntax = syntax + " ) );";

        try {
            return stmt.execute(syntax);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertToTable(String tablename, String... data) {

        int keys = getKeysCount(tablename);
        int columns = getColumnCount(tablename);
        if(data.length < keys) return false;

        String keyNames = "key0";
        String keyNamesAndValues = "key0 = " + data[0];
        String valNames = "";

        String syntax = "INSERT OR REPLACE INTO " + tablename + " (";

        for(int i = 1; i < columns; i++) {
            if(i < keys) {
                keyNames = keyNames + ", key" + i;
                if(data[i] == null) return false;
                keyNamesAndValues = keyNamesAndValues + " AND key" + i + " = '" + data[i] + "'";
            }
            else {
                valNames = valNames + ", value" + (i - keys);
            }
        }
        syntax = syntax + keyNames + valNames;
        syntax = syntax + " ) VALUES ( '" + data[0] + "'";
        for(int i = 1; i < data.length && i < columns; i++) {
            if(data[i] == null && i < keys) return false;
            if(data[i] == null) syntax = syntax + "( SELECT " + valNames.split(", ")[i] + " FROM " + tablename +
                    " WHERE " + keyNamesAndValues + " )";
            else syntax = syntax + ", '" + data[i] + "'";
        }
        syntax = syntax + ");";

        System.out.println(syntax);

        try {
            return stmt.execute(syntax);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<String[]> selectFromTable(String tablename, String... keysAndValues) {

        String syntax = "SELECT ";
        int keys = getKeysCount(tablename);
        int columns = getColumnCount(tablename);
        String vals = "";
        String cols = "";
        for(int i = 0; i < keysAndValues.length; i++) {
            if(keysAndValues[i] != null) {
                if(i < keys) {
                    cols = cols + "key" + i;
                } else {
                    cols = cols + "value" + (i - keys);
                }
                vals = vals + cols.split(", ")[i] + " = '" + keysAndValues + "'";
                if(i < keysAndValues.length - 1) {
                    vals = vals + " AND ";
                    cols = cols + ", ";
                }
            }
        }
        if(cols.isEmpty())
            cols = "*";

        syntax = syntax + cols + " FROM " + tablename;

        if(!vals.isEmpty()) {
            syntax = syntax + " WHERE " + vals + " ;";
        }
        System.out.println(syntax);
        ResultSet rs;
        try {
            rs = stmt.executeQuery(syntax);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<String[]> results = new ArrayList<>();
        int i = 0;
        try {
            while(!rs.isAfterLast()) {

                String[] s = new String[columns];
                for(int j = 0; j < columns; j++) {

                    s[j] = rs.getString(j);

                }
                results.set(i, s);
                i++;
                if(!rs.next()) break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public int getColumnCount(String tablename) {
        String syntax = "SELECT * FROM (" + tablename + ");";

        int count = 0;
        try {
            ResultSet rs = stmt.executeQuery(syntax);
            ResultSetMetaData rsmd = rs.getMetaData();
            count = rsmd.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getKeysCount(String tablename) {
        String syntax = "SELECT * FROM (" + tablename + ");";

        int count = 1;
        try {
            ResultSet rs = stmt.executeQuery(syntax);
            ResultSetMetaData rsmd = rs.getMetaData();
            for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                if(!rsmd.getColumnName(i).equals("key" + (i - 1))) break;
                count = i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return count;
    }

    


}
