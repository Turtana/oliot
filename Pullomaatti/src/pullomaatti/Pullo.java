/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pullomaatti;

/**
 *
 * @author k4873
 */
public class Pullo {
    private String nimi;
    private String valmistaja;
    private double tilavuus;
    private double hinta;
    
    public Pullo (String tyyppi, double arvo) {
        nimi = tyyppi;
        valmistaja = "Geneerinen limsatehdas";
        tilavuus = 0.5;
        hinta = arvo;
    }
    
    
    public String getName() {
        return nimi;
    }
    
    public double getPrice() {
        return hinta;
    }
}
