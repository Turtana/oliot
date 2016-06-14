/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pullomaatti;

import java.util.ArrayList;

/**
 *
 * @author k4873
 */
public class maatti {
    private int pullot;
    public double rahaa;
    public ArrayList<Pullo> pullovarasto = new ArrayList();
    private double hinta;
    private String lista;
    
    static private maatti pm = null;
    
    private maatti() {
        pullot = 12;
        rahaa = 0;
        lista = "";
        //pullovarasto = new Pullo[pullot];
        
        for (int i = 0; i < (pullot/3); i++) {
            pullovarasto.add(new Pullo("Pepsi", 1.80, 0.5));
            pullovarasto.add(new Pullo("Kokis", 2.00, 0.5));
            pullovarasto.add(new Pullo("Fanta", 1.00, 0.33));
        }
        
        
    }
    
    public static maatti getInstance() {
        if (pm == null) {
            pm = new maatti();
        }
        return pm;
    }
    
    public String listaaPullot () {
        int i = 1;
        lista = "";
        for (Pullo bottle : pullovarasto) {
            lista = lista + i + ". " + bottle.getName() + " " + bottle.getVolume()
                    + "l " + bottle.getPrice() + " €\n";
            i++;
        }
        return lista;
    }
    
    public void lisaaRahaa(double raha) {
        rahaa += raha;
    }
    
    
    public int ostaPullo(String tyyppi, double volume) {
        int pullonnumero = -1;
        String nimi;
        
        for (int a = 0; a < pullovarasto.size(); a++) {
            if (pullovarasto.get(a).getName().equals(tyyppi) && pullovarasto.get(a).getVolume() == volume) {
                pullonnumero = a;
                break;
            }
        }
        
        if (pullonnumero == -1) {
            // System.out.println("Tuontyyppiset pullot ovat loppuneet :(");
            return 2;
        }
        hinta = pullovarasto.get(pullonnumero).getPrice();
        nimi = tyyppi;
        if (rahaa < hinta) {
            //System.out.println("Et ole asettanut tarpeeksi rahaa!");
            return 1;
        } else if (pullot == 0) {
            // asd prölölölölölö
        } else {
            rahaa -= hinta;

            // System.out.println("Ka-chunk! Ostit pullon " + nimi);
            pullovarasto.remove(pullonnumero); // pullo poistetaan listalta
            pullot -= 1; // Pulloja yksi vähemmän
        }
        
        return 0;
    }
    
    public void palautarahat() {
        if (rahaa != 0)
            //System.out.println("Kilinkilinkilin! Rahaa palautettu " + rahaa + " euroa. Miellyttävää päivänjatkoa.");
        rahaa = 0;
    }
}
