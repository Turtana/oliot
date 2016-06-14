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
    private final String nimi;
    private final String valmistaja;
    private final double tilavuus;
    private final double hinta;
    
    public Pullo (String tyyppi, double arvo, double tila) {
        nimi = tyyppi;
        valmistaja = "Geneerinen limsatehdas";
        tilavuus = tila;
        hinta = arvo;
    }
    
    
    public String getName() {
        return nimi;
    }
    
    public double getPrice() {
        return hinta;
    }
    
    public double getVolume() {
        return tilavuus;
    }
    
    
    @Override
    public String toString() {
        return (nimi + " " + hinta + " €");
    }
    
}
