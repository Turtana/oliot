/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainclass;
/**
 *
 * @author k4873
 */
public class Bottle {
    private String nimi;
    private String valmistaja;
    private double tilavuus;
    private double hinta;
    private double energia;
    
    public Bottle (String tyyppi, double arvo, double tila) {
        nimi = tyyppi;
        valmistaja = "Geneerinen limsatehdas";
        tilavuus = tila;
        hinta = arvo;
        energia = 0.3;
    }
    
    
    public String getName() {
        return nimi;
    }
    
    public double getPrice() {
        return hinta;
    }
    
    public double getSize() {
        return tilavuus;
    }
}
