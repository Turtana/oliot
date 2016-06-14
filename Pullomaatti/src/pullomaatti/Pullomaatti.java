/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pullomaatti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author k4873
 */
public class Pullomaatti {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        maatti auto = new maatti();
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String val = "";
        int vali = 0;
        
        while (true) {
            System.out.println("\nPulloautomaatti");
            System.out.println("");
            System.out.println("1) Laita euron kolikko automaattiin");
            System.out.println("2) Osta Pepsi (1,80 €)");
            System.out.println("3) Osta Kokis (2,00 €)");
            System.out.println("4) Osta Fanta (1,00 €)");
            System.out.println("0) Lopeta");
            System.out.println("");
            System.out.print("Valitse: ");
            
            try {
                val = buff.readLine();
            } catch(IOException ex) {
                System.out.println("Tapahtui virhe!");
            }
            vali = Integer.parseInt(val);
            
            if (vali == 1) {
                auto.lisaaRahaa();
            } else if (vali == 2) {
                auto.ostaPullo("Pepsi");
            } else if (vali == 3) {
                auto.ostaPullo("Kokis");
            } else if (vali == 4) {
                auto.ostaPullo("Fanta");
            } else if (vali == 0) {
                break;
            } else {
                System.out.println("Virheellinen valinta!");
            }
            
        }
        
        auto.palautarahat();
        
    }
    
}
