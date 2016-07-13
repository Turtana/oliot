/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package item;

/**
 *
 * @author k4873
 */
public class Item {
    private final String kuvaus, nimi;
    private final int id, koko;
    private final boolean breakable; // Jos pistetään ei-turvalliseen pakettiin niin särkyy
    
    public Item (int i, String s, int k, boolean b, String n) {
        id = i;
        kuvaus = s;
        koko = k;
        breakable = b;
        nimi = n;
    }
    
    @Override
    public String toString() {
        return nimi;
    }
    
    public String kuvaile() {
        return kuvaus;
    }
    
    public int getId () {
        return id;
    }
    
    public int getKoko() {
        return koko;
    }
    
    public boolean isBreakable() {
        return breakable;
    }
}
