/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.wasc.crawler;

import org.daiict.mscit.pl1.wasc.db.DAL;
import org.daiict.mscit.pl1.wasc.entities.Link;
import org.jsoup.Jsoup;
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

        try {
            /*
             * if (link.getURL().contains((new String("orkut")).subSequence(0,
             * 4))) { return; } if (link.getURL().contains((new
             * String("facebook")).subSequence(0, 4))) { return; } if
             * (link.getURL().contains((new String("twitter")).subSequence(0,
             * 4))) { return;
            }
             */

            Document doc = Jsoup.connect(link.getURL()).get();
            Elements links = doc.select("a[href]");
            Link l;

            System.out.println(link.getID() + " " + link.getURL());

            int counter = 0;

            for (Element subLink : links) {
                l = new Link(dal.getNextURLID(), subLink.attr("abs:href"), link.getURL(), 1);
                result = dal.persistLink(l);
            }
            link.setDone(0);
            dal.updateLink(link);

            while (dal.getNextUndoneLink() != null) {
                crawl(dal.getNextUndoneLink(), --depth);
            }

        } catch (Exception e) {
        }

    }
}
