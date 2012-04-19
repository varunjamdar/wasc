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
import org.daiict.mscit.pl1.wasc.entities.Link;
import org.daiict.mscit.pl1.wasc.entities.LinkStore;
import org.daiict.mscit.pl1.wasc.security.SQLInjector;

public class TryMain {

    public static void main(String[] args) {
        
        Crawler crawler=new Crawler();
        crawler.setLinkStore(new LinkStore());
        crawler.setBaseURL("http://10.100.54.26:8084/TargetApp/index.jsp");
        crawler.crawl();
        
        //Crawler.displayForm("https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/ca/&ss=1&scc=1&ltmpl=default&ltmplcache=2");
        //Crawler.displayForm("http://localhost:8084/TargetApp/index.jsp");
        
        SQLInjector injector=new SQLInjector(crawler.getLinkStore());
        //injector.test(new Link(1, "http://10.100.54.26:8084/TargetApp/index.jsp", "", 1));
    }
}
