/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pankki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author k4873
 */
public class Account {
    
    private int rahamaara;
    private String tilinumero;
    protected BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    protected int luottoraja = 0, alaraja = 0;
    
    public Account() {
        try {
            System.out.print("Syötä tilinumero: ");
            tilinumero = in.readLine();
            
            System.out.print("Syötä rahamäärä: ");
            rahamaara = Integer.parseInt(in.readLine());
            
        } catch (IOException ex) {
            System.out.println("Hyvänen aika! Jokin taisi mennä pieleen!");
        }
    }
    
    public String getTilinro() {
        return tilinumero;
    }
    
    public void setRahaa (int rahaa) {
        if (rahamaara + rahaa < alaraja) {
            System.out.println("Rahat lopussa!");
        } else {
            rahamaara += rahaa;
        }
    }
    
    public int getRahaa () {
        return rahamaara;
    }
    
        
    public int getLuotto () {
        return luottoraja;
    }
    
}

class Credit extends Account {
    
    
    Credit () {
        try {
            System.out.print("Syötä luottoraja: ");
            luottoraja = Integer.parseInt(in.readLine());
            alaraja = -1*luottoraja;
        } catch (IOException ex) {
            System.out.println("No voi herranjestas! Taas virhe!");
        }
        
    }
    
}