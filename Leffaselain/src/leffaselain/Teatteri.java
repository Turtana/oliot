/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package leffaselain;

/**
 *
 * @author k4873
 */
public class Teatteri {
    private final int id;

    
    public int getId() {
        return id;
    }
 
        // Getterien tarpeellisuus kyseenalainen, mutta olkoot nyt kaiken varalta.
    public String getPaikka() {
        return paikka;
    }
    
    private final String paikka;
    
    public Teatteri (int i, String p) {
        id = i;
        paikka = p;
    }
    
    @Override
    public String toString() {
        return paikka;
    }
    
}