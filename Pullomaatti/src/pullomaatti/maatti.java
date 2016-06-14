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
    private double rahaa;
    private ArrayList<Pullo> pullovarasto = new ArrayList();
    private double hinta;
    private String nimi;
    
    public maatti() {
        pullot = 12;
        rahaa = 0;
        //pullovarasto = new Pullo[pullot];
        
        for (int i = 0; i < (pullot/3); i++) {
            pullovarasto.add(new Pullo("Pepsi", 1.80));
            pullovarasto.add(new Pullo("Kokis", 2.00));
            pullovarasto.add(new Pullo("Fanta", 1.00));
        }
    }
    
    public void lisaaRahaa() {
        rahaa += 1.00;
        System.out.println("Klink! Euro lisätty. Rahaa nyt " + rahaa + " euroa.");
    }
    
    public void ostaPullo(String tyyppi) {
        int pullonnumero = -1;
        
        for (int a = 0; a < pullovarasto.size(); a++) {
            if (pullovarasto.get(a).getName() == tyyppi) {
                pullonnumero = a;
                break;
            }
        }
        
        if (pullonnumero == -1) {
            System.out.println("Tuontyyppiset pullot ovat loppuneet :(");
            return;
        }
        hinta = pullovarasto.get(pullonnumero).getPrice();
        nimi = tyyppi;
        if (rahaa < hinta) {
            System.out.println("Et ole asettanut tarpeeksi rahaa!");
        } else if (pullot == 0) {
            System.out.println("Pullot ovat loppuneet :("); // tarpeeton, mutten jaksa poistaakaan.
        } else {
            rahaa -= hinta;

            System.out.println("Ka-chunk! Ostit pullon " + nimi + ". Rahaa jäljellä " + rahaa);
            pullovarasto.remove(pullonnumero); // pullo "poistetaan" listalta
            pullot -= 1; // Pulloja yksi vähemmän
        }
    }
    
    public void palautarahat() {
        if (rahaa != 0)
            System.out.println("Kilinkilinkilin! Rahaa palautettu " + rahaa + " euroa. Miellyttävää päivänjatkoa.");
        rahaa = 0;
    }
}
