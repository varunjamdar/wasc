/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.wasc.entities;

/**
 *
 * @author VJ
 */
public class Link {

    private int ID;
    private String URL;
    private String ParentURL;
    private int done;

    public Link(int id, String URL, String ParentURL, int done) {
        this.ID=id;
        this.URL = URL;
        this.ParentURL = ParentURL;
        this.done = done;
    }

    public int getID() {
        return ID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getParentURL() {
        return ParentURL;
    }

    public void setParentURL(String ParentURL) {
        this.ParentURL = ParentURL;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }
}
