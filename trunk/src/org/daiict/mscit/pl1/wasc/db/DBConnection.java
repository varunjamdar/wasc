/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.wasc.db;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author ankur
 */
public class DBConnection {

    private Connection dbCon;
    private Statement dbStatement;
    private ResultSet dbResultSet;

    public DBConnection() {
    }

    public boolean isConnected() throws SQLException {
        return !dbCon.isClosed();
    }

    public boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbCon = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "");
            if (!dbCon.isClosed()) {
                return true;
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found :-" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Sql Exception in main:- " + ex.getMessage());
        }
        return false;
    }

        public boolean connect(String database) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbCon = DriverManager.getConnection("jdbc:mysql://localhost/"+database, "root", "");
            if (!dbCon.isClosed()) {
                return true;
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found :-" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Sql Exception in main:- " + ex.getMessage());
        }
        return false;
    }
    
    protected Connection getConnection() throws SQLException {
        if (!dbCon.isClosed()) {
            return this.dbCon;
        } else {
            return null;
        }
    }

    //insert update delete
    public boolean executeQuery(String sql) throws SQLException {
        boolean retVal = true;
        try {
            dbStatement = dbCon.createStatement();
            dbStatement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("error in insert or update query :" + ex.getMessage());
            retVal = false;
        } /*finally {
        dbStatement=null;
        dbResultSet.close();
        }*/

        return retVal;
    }

    public ResultSet selectRecords(String tableName) throws SQLException {
        try {
            dbStatement = dbCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            dbResultSet = dbStatement.executeQuery("select * from " + tableName);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return dbResultSet;
    }

    public ResultSet customQuery(String sqlQuery) throws SQLException {
        try {
            dbStatement = dbCon.createStatement();
            dbResultSet = dbStatement.executeQuery(sqlQuery);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return dbResultSet;
    }

    public boolean disconnect() {
        boolean retval = false;
        try {
            dbCon.close();
            retval = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return retval;
    }
}
