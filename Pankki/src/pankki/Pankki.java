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
 * Elias Vähäjylkkä
 * 8.6.2016
 */
public class Pankki {

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        boolean lopeta = false;
        String hakunimi;
        int vali, raha, luotto; // Rahat kokonaislukuna... No ihan sama >:(
        Bank pank = new Bank();
        Account kasitili; // Siis KÄSIteltävä tili
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
        while (true) {
                System.out.println("\n*** PANKKIJÄRJESTELMÄ ***");
                System.out.println("1) Lisää tavallinen tili");
                System.out.println("2) Lisää luotollinen tili");
                System.out.println("3) Tallenna tilille rahaa");
                System.out.println("4) Nosta tililtä");
                System.out.println("5) Poista tili");
                System.out.println("6) Tulosta tili");
                System.out.println("7) Tulosta kaikki tilit");
                System.out.println("0) Lopeta");
                System.out.print("Valintasi: ");
                vali = Integer.parseInt(in.readLine());
                
                switch (vali) {
                    case 1:
                        pank.lisaaTili();
                        System.out.println("Tili luotu.");
                        break;
                    case 2:
                        pank.lisaaLuotto();
                        System.out.println("Tili luotu.");
                        break;
                    case 3:
                        System.out.print("Syötä tilinumero: ");
                        hakunimi = in.readLine();
                        kasitili = pank.etsiTili(hakunimi);
                        System.out.print("Syötä rahamäärä: ");
                        raha = Integer.parseInt(in.readLine());
                        if (kasitili == null) {
                            System.out.println("Tiliä ei löytynyt! Such sad :(");
                            break;
                        } else {
                            kasitili.setRahaa(raha);
                        }
                        break;
                    case 4:
                        System.out.print("Syötä tilinumero: ");
                        hakunimi = in.readLine();
                        kasitili = pank.etsiTili(hakunimi);
                        System.out.print("Syötä rahamäärä: ");
                        raha = Integer.parseInt(in.readLine());
                        //System.out.println("Nostetaan tililtä: " + hakunimi + " rahaa " + raha);
                        if (kasitili == null) {
                            System.out.println("Tiliä ei löytynyt! Such bad :(");
                            break;
                        } else {
                            kasitili.setRahaa(raha*-1);
                        }
                        break;
                    case 5:
                        System.out.print("Syötä poistettava tilinumero: ");
                        hakunimi = in.readLine();
                        kasitili = pank.etsiTili(hakunimi);
                        if (kasitili == null) {
                            System.out.println("Tiliä ei löytynyt! Such mad :(");
                            break;
                        } else {
                            pank.poistaTili(hakunimi); // Juupajuu. Kaksi etsintää. No menköön.
                        }
                        break;
                    case 6:
                        System.out.print("Syötä tulostettava tilinumero: ");
                        hakunimi = in.readLine();
                        kasitili = pank.etsiTili(hakunimi);
                        if (kasitili == null) {
                            System.out.println("Tiliä ei löytynyt! Such GLaD :(");
                            break;
                        } else {
                            System.out.print("Tilinumero: " + kasitili.getTilinro() + " Tilillä rahaa: " + kasitili.getRahaa());
                            if (kasitili.getLuotto() == 0) {
                                System.out.println();
                            } else {
                                System.out.println(" Luottoraja: " + kasitili.getLuotto());
                            }
                        }
                        break;
                    case 7:
                        System.out.println("Kaikki tilit:");
                        pank.printAll();
                        break;
                    case 0:
                        lopeta = true;
                        break;
                    default:
                        System.out.println("Valinta ei kelpaa."); // Eikä tämä ohjelmakaan aivot omistavalle asiakkaalle.
                }
                
                if (lopeta == true) {
                    break;
                }
                
        }

        
    }
    
}

