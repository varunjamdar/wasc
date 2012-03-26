/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.wasc.crawler;

import org.daiict.mscit.pl1.wasc.db.DAL;
import org.daiict.mscit.pl1.wasc.entities.Link;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 *
 * @author VJ
 */
public class Crawler {

    private String baseURL;
    private DAL dal;

    public Crawler() {
        dal = new DAL();
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
        dal.persistLink(new Link(baseURL, "", 0));
    }

    public void crawl() {
        crawl(baseURL);
    }

    public void crawl(String URL) {
        crawl(URL, -1);
    }

    public void crawl(String URL, int depth) {
        if (depth < 0) {
            try {
                Document doc = Jsoup.connect(URL).get();
                
            } catch (IOException ioe) {
            }
        }
    }
}
