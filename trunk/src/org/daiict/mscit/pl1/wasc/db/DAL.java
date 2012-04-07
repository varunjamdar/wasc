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
    private PreparedStatement ps;
    private ResultSet resultset;
    private String database = "WASC";

    public DAL() {
        connection = new DBConnection();
    }

    public boolean connect() {
        return connection.connect(database);
    }

    public int getNextURLID() {
        int id = -1;
        try {
            if (connect()) {
                resultset = connection.customQuery("Select max(ID) from links");
                if (resultset.next()) {
                    id = (resultset.getInt(1) == 0 ? 1 : resultset.getInt(1));
                    id++;
                } else {
                    id = 1;
                }
                connection.disconnect();
            }
        } catch (Exception ex) {
            System.out.println("getNext URL - Connection Error occurred : " + ex.getMessage());
        }finally{
            connection.disconnect();
        }
        return id;
    }

    public boolean persistLink(Link l) {
        try {
            if(l.getURL().equals(""))
                return false;
            if (connect()) {
                //need to implement using prepared statement
                ps=connection.getConnection().prepareStatement("INSERT INTO links (ID,URL,ParentURL,Done) VALUES(?,?,?,?)");
                ps.setInt(1, l.getID());
                ps.setString(2, l.getURL());
                ps.setString(3, l.getParentURL());
                ps.setInt(4, l.getDone());
                
                ps.executeUpdate();
                
                //boolean result= connection.executeQuery("INSERT INTO links (ID,URL,ParentURL,Done) VALUES(" + l.getID() + ",'" + l.getURL() + "','" + l.getParentURL() + "'," + l.getDone() + ")");
                connection.disconnect();
                //return result;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("persist Link "+l.getURL()+" - Connection Error occurred : " + ex.getMessage());
        }finally{
            connection.disconnect();
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
            System.out.println("Update Link "+l.getURL()+" - Connection Error occurred : " + ex.getMessage());
        }finally{
            connection.disconnect();
        }
        return false;
    }
    
    public Link getNextUndoneLink(){
        try{
            if(connect()){
                resultset=connection.customQuery("Select * from links where Done=1 Limit 1");
                if(resultset.first()){
                    Link l=new Link(resultset.getInt(1), resultset.getString(2), resultset.getString(3), resultset.getInt(5));
                    connection.disconnect();
                    return l;
                }
            }
        }catch(Exception ex){
            System.out.println("getNextUndoneLink - Connection Error occurred : " + ex.getMessage());
        }finally{
            connection.disconnect();
        }
        return null;
    }
}
