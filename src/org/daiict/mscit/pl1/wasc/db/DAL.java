/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.wasc.db;

import java.sql.*;
import org.daiict.mscit.pl1.wasc.entities.*;

/**
 *
 * @author VJ
 */
public class DAL {

    private DBConnection connection;
    private ResultSet resultset;
    private String database = "WASC";

    public DAL() {
        connection = new DBConnection();
    }

    public boolean connect() {
        return connection.connect(database);
    }

    int getNextURLID() {
        int id = -1;
        try {
            if (connect()) {
                resultset = connection.customQuery("Select max(ID) from links");
                if (resultset.next()) {
                    id = (resultset.getInt(1) == 0 ? 1 : resultset.getInt(1));
                } else {
                    id = 1;
                }
                connection.disconnect();
            }
        } catch (Exception ex) {
            System.out.println("Connection Error occurred : " + ex.getMessage());
        }
        return id;
    }

    public boolean persistLink(Link l) {
        try {
            if (connect()) {
                boolean result= connection.executeQuery("INSERT INTO links (ID,URL,ParentURL,Done) VALUES(" + l.getID() + ",'" + l.getURL() + "','" + l.getParentURL() + "'," + l.getDone() + ")");
                connection.disconnect();
                return result;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Connection Error occurred : " + ex.getMessage());
        }
        return false;
    }
    
    public boolean updateLink(Link l){
        try {
            if (connect()) {
                boolean result= connection.executeQuery("UPDATE links SET Done='"+l.getDone()+"' WHERE ID='"+l.getID()+"'");
                connection.disconnect();
                return result;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Connection Error occurred : " + ex.getMessage());
        }
        return false;
    }
}
