/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daiict.mscit.pl1.wasc.entities;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author SHIKHA
 */
public class LinkStore {
    private Map<Integer,Link> linkStore=null;

    public LinkStore() {
        linkStore=new HashMap<Integer,Link>();
    }
    
    public Map<Integer,Link> getStore(){
        return this.linkStore;
    }
}
