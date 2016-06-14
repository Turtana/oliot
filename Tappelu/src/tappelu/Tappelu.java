/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tappelu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author k4873
 */
public class Tappelu {


    public static void main(String[] args) throws IOException {
        
        Character hahmo = null;
        String ase = "";
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int vali, hahvali, asevali;
        
        while (true) {
            System.out.println("*** TAISTELUSIMULAATTORI ***");
            System.out.println("1) Luo hahmo");
            System.out.println("2) Taistele hahmolla");
            System.out.println("0) Lopeta");
            System.out.print("Valintasi: ");
            vali = Integer.parseInt(in.readLine());
            
            if (vali == 1) {
                System.out.println("Valitse hahmosi:");
                System.out.println("1) Kuningas");
                System.out.println("2) Ritari");
                System.out.println("3) Kuningatar");
                System.out.println("4) Peikko");
                System.out.print("Valintasi: ");
                
                hahvali = Integer.parseInt(in.readLine());
                if (hahvali == 1)
                hahmo = new King();
                if (hahvali == 2)
                hahmo = new Knight();
                if (hahvali == 3)
                hahmo = new Queen();
                if (hahvali == 4)
                hahmo = new Troll();
                
                System.out.println("Valitse aseesi.");
                System.out.println("1) Veitsi");
                System.out.println("2) Kirves");
                System.out.println("3) Miekka");
                System.out.println("4) Nuija");
                System.out.print("Valintasi: ");
                
                asevali = Integer.parseInt(in.readLine());
                if (asevali == 1)
                hahmo.addWeapon("veitsi");
                if (asevali == 2)
                hahmo.addWeapon("kirves");
                if (asevali == 3)
                hahmo.addWeapon("miekka");
                if (asevali == 4)
                hahmo.addWeapon("nuija");
                
            } else if (vali == 2) {
                hahmo.fight();
            } else if (vali == 0) {
                break;
            } else {
                System.out.println("Virheellinen valinta!");
            }
            
        }
    }
    
}
