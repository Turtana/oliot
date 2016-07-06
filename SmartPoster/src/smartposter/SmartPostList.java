/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import java.util.ArrayList;

/**
 *
 * @author k4873
 */
public class SmartPostList {
    
    static private SmartPostList spl = null;
    
    private final ArrayList<SmartPost> smartlist = new ArrayList();
  
    private SmartPostList () {

    }
    
    public static SmartPostList getInstance() {
        if (spl == null) {
            spl = new SmartPostList();
        }
        return spl;
    }
    
    public ArrayList<SmartPost> getPostList () {
        return smartlist;
    }
    
    public void addPost (SmartPost s) {
        smartlist.add(s);
    }
    
}
