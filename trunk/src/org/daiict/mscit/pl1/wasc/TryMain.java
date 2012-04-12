/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.WASC;
/**
 *
 * @author VJ
 */

import org.daiict.mscit.pl1.wasc.crawler.*;

public class TryMain {

    public static void main(String[] args) {
        
        /*Crawler crawler=new Crawler();
        crawler.setBaseURL("http://placement.daiict.ac.in");
        crawler.crawl();*/
        
        Crawler.displayForm("https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/ca/&ss=1&scc=1&ltmpl=default&ltmplcache=2");
    }
}
