/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.wasc.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.daiict.mscit.pl1.wasc.db.DAL;
import org.daiict.mscit.pl1.wasc.entities.Link;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author VJ
 */
public class Crawler {

    private Link baseLink;
    private DAL dal;

    public Crawler() {
        dal = new DAL();
    }

    public String getBaseURL() {
        return baseLink.getURL();
    }

    public void setBaseURL(String URL) {
        baseLink = new Link(dal.getNextURLID(), URL, "", 0);
        boolean result = dal.persistLink(baseLink);
    }

    public void crawl() {
        crawl(baseLink);
    }

    public void crawl(Link URL) {
        crawl(URL, 2);
    }

    public void crawl(Link link, int depth) {
        boolean result;
        List<Link> childNodes = new ArrayList<Link>();

        if (depth > 0) {
            try {
                /* if (link.getURL().contains((new String("orkut")).subSequence(0, 4))) { return; } if (link.getURL().contains((new String("facebook")).subSequence(0, 4))) { return; } if(link.getURL().contains((new String("twitter")).subSequence(0,4))) { return;} */

                Document doc = Jsoup.connect(link.getURL()).get();
                Elements links = doc.select("a[href]");
                Link l;

                System.out.println(link.getID() + " " + link.getURL());

                int counter = 0;

                for (Element subLink : links) {
                    l = new Link(dal.getNextURLID(), subLink.attr("abs:href"), link.getURL(), 1);
                    result = dal.persistLink(l);
                    childNodes.add(l);
                }
                link.setDone(0);
                dal.updateLink(link);


                depth--;
                for (Link ll : childNodes) {
                    crawl(ll, depth);
                }

            } catch (Exception e) {
            }
        } else {
            return;
        }
    }

    public static void displayForm(String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            Elements hidden = doc.select("input[type=hidden]");

            Elements forms = doc.select("form[action]");
            Elements textboxes = null;
            for (Element e : forms) {
                System.out.println(e.attr("abs:action"));
                textboxes = e.getElementsByAttributeValue("type", "text");
                textboxes.addAll(e.getElementsByAttributeValue("type", "password"));
                for (Element child : textboxes) {
                    System.out.println(child.toString());
                }
            }

            //Document responseDoc = Jsoup.connect(url).ignoreHttpErrors(true).ignoreContentType(true).data("", "").post();
            //responseDoc.toString();
            
            Connection con=Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true);
            
            for(Element e1:hidden){
                con=con.data(e1.attr("name"), e1.attr("value"));
            }

        } catch (IOException ex) {
        }
    }
}
