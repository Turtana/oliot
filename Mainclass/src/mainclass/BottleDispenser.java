/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainclass;


import java.util.ArrayList;


/**
 *	Elias Vähäjylkkä
 * 	1.6.2016
 */
public class BottleDispenser {
    private int pullot;
    private double rahaa;
    private ArrayList<Bottle> pullovarasto = new ArrayList();
    private double hinta = 1.00;
    private String nimi;
    
    public BottleDispenser() {
        pullot = 5;
        rahaa = 0;
        //pullovarasto = new Bottle[pullot];
        
        pullovarasto.add(new Bottle("Pepsi Max", 1.8, 0.5));
        pullovarasto.add(new Bottle("Pepsi Max", 2.2, 1.5));
        pullovarasto.add(new Bottle("Coca-Cola Zero", 2.0, 0.5));
        pullovarasto.add(new Bottle("Coca-Cola Zero", 2.5, 1.5));
        pullovarasto.add(new Bottle("Fanta Zero", 1.95, 0.5));
        pullovarasto.add(new Bottle("Fanta Zero", 1.95, 0.5));
		/*
        for (int i = 0; i < (pullot/3); i++) {
            pullovarasto.add(new Pullo("Pepsi", 1.80));
            pullovarasto.add(new Pullo("Kokis", 2.00));
            pullovarasto.add(new Pullo("Fanta", 1.00));
        }
		*/
    }
    
    public void lisaaRahaa() {
        rahaa += 1.00;
        // System.out.println("Klink! Euro lisätty. Rahaa nyt " + rahaa + " euroa.");
		System.out.println("Klink! Lisää rahaa laitteeseen!");
    }
    
    public void ostaPullo(int nro) {
        
        int pullonnumero = nro;
	/* int pullonnumero = -1;
        
        for (int a = 0; a < pullovarasto.size(); a++) {
            if (pullovarasto.get(a).getName() == tyyppi) { // Tämä looppi hakee varastosta oikeannumeroisen pullon
                pullonnumero = a;
                break;
            }
        } 
        
        if (pullonnumero == -1) {
            System.out.println("Tuontyyppiset pullot ovat loppuneet :(");
            return;
        } */
		
        hinta = pullovarasto.get(pullonnumero).getPrice();

		
        if (rahaa < hinta) {
            System.out.println("Syötä rahaa ensin!");
        } else if (pullot == 0) {
            System.out.println("Pullot ovat loppuneet :("); // tarpeeton, mutten jaksa poistaakaan.
        } else {
            rahaa -= hinta;

            pullovarasto.remove(pullonnumero); // pullo poistetaan listalta
			
            System.out.println("KACHUNK! " + pullovarasto.get(pullonnumero).getName() + " tipahti masiinasta!"/* + rahaa*/);
			
            pullot -= 1; // Pulloja yksi vähemmän
        }
    }
	
    public void pulloLista() {
        
        for (int a = 0; a < pullovarasto.size(); a++) {
             System.out.println((a+1) + ". Nimi: " + pullovarasto.get(a).getName());
             System.out.println("\tKoko: " + pullovarasto.get(a).getSize() + "\tHinta: " + pullovarasto.get(a).getPrice());
        }
        
        
    }
    
    
    
	/*public void ostaPullo() {
		
		if (rahaa == 0) {
			System.out.println("Syötä rahaa ensin!");
		} else {
			rahaa -= 1;
			pullot -= 1;
			
			System.out.println("KACHUNK! " + pullovarasto.get(0).getName() + " tipahti masiinasta!");
			pullovarasto.remove(0);
		}
		
	}*/
    
    public void palautaRahat() {
        /*
		if (rahaa != 0)
            System.out.println("Kilinkilinkilin! Rahaa palautettu " + rahaa + " euroa. Miellyttävää päivänjatkoa.");
		*/
        
	System.out.println("Klink klink. Sinne menivät rahat! Rahaa tuli ulos " + Math.round(rahaa));
        rahaa = 0;
    }
}
