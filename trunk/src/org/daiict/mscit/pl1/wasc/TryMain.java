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
import org.daiict.mscit.pl1.wasc.entities.Report;
import org.daiict.mscit.pl1.wasc.security.SQLInjector;

public class TryMain {

    public static void main(String[] args) {
        
        LinkStore ls=new LinkStore();
        Crawler crawler=new Crawler();
        crawler.setLinkStore(ls);
        crawler.setBaseURL("http://localhost:8080/TargetApp/index.jsp");
        crawler.crawl();
        System.out.println("let us see...");
        
        //Crawler.displayForm("https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/ca/&ss=1&scc=1&ltmpl=default&ltmplcache=2");
        //Crawler.displayForm("http://localhost:8080/TargetApp/register.jsp");
        
        //SQLInjector injector=new SQLInjector(crawler.getLinkStore());
        //if(injector.testAll()){
          //  for(Report r : injector.getReport()){
                //System.out.println(injector.getLinkStore().getStore().get(r.getLinkID()).getURL());
                //System.out.println(r.getReport());
            //}
        //}
    }
}
