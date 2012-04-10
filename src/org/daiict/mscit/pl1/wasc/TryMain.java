/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.WASC;
/**
 *
 * @author VJ
 */

import org.daiict.mscit.pl1.wasc.db.*;
import org.daiict.mscit.pl1.wasc.entities.*;
import org.daiict.mscit.pl1.wasc.crawler.*;
import java.sql.*;

public class TryMain {

    public static void main(String[] args) {
        
        Crawler crawler=new Crawler();
        crawler.setBaseURL("http://www.twitter.com");
        crawler.crawl();
    }
}
