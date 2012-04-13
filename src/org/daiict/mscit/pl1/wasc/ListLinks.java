/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author VJ
 */
package org.daiict.mscit.pl1.wasc;

import org.daiict.mscit.pl1.wasc.db.DBConnection;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.sql.*;


/**
 * Example program to list links from a URL.
 */
public class ListLinks {

    public static void main(String[] args) throws IOException {
        
        DBConnection con=new DBConnection();
        ResultSet rs=null;
        int id=-1;
        
        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = args[0];
        
        try {
            if(con.connect()){
                rs=con.customQuery("Select max(ID) from WASC.links");
                
                if(rs.next())
                    id=(rs.getInt(1) == 0 ? 1 : rs.getInt(1));
                else
                    id=1;
                con.executeQuery("INSERT INTO WASC.links (ID, URL, ParentURL, Done) VALUES("+(id++)+",'"+url+"','',0)");
            }
            con.disconnect();
        } catch (Exception e) {
            print(e.getMessage(), url);
            con.disconnect();
        }
        
        print("Fetching %s...", url);

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        //commented code
        //<editor-fold>
        //Elements media = doc.select("[src]");
        //Elements imports = doc.select("link[href]");

        /*print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img")) {
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            } else {
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
            }
        }

        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
        }*/
        //</editor-fold>
        
        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
            try{
                if(con.connect()){
                
                con.executeQuery("INSERT INTO WASC.links (ID, URL, ParentURL, Done) VALUES("+(id++)+",'"+link.attr("abs:href")+"','"+url+"',1)");
            }
            con.disconnect();
               
            }catch(Exception exe){
                print(exe.getMessage(), "");
              //  con.disconnect();
            }
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width) {
            return s.substring(0, width - 1) + ".";
        } else {
            return s;
        }
    }
}
