/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pankki;

import java.util.ArrayList;

/**
 *
 * @author k4873
 */
public class Bank {
	
    private boolean tililoytyi;
    
    private ArrayList<Account> tilit;
    
    public Bank () {
        tilit = new ArrayList(); // Jee, tilejä!
    }
    
    public void lisaaTili () {
        tilit.add(new Account());
        //System.out.println("Pankkiin lisätään: " + tilit.get(tilit.size()-1).getTilinro() + "," + tilit.get(tilit.size()-1).getRahaa());
    }
    
    public void lisaaLuotto() {
        tilit.add(new Credit());
        //System.out.println("Pankkiin lisätään: " + tilit.get(tilit.size()-1).getTilinro() + "," + tilit.get(tilit.size()-1).getRahaa() + "," +  tilit.get(tilit.size()-1).getLuotto());
    }
    
    public Account etsiTili(String id) {
        //System.out.println("Etsitään tiliä " + id);
	tililoytyi = false;
        for (int i = 0; i < tilit.size(); i++) {
            if (tilit.get(i).getTilinro().equals(id)) {
                tililoytyi = true;
		return tilit.get(i);
            }
        }
	if (tililoytyi == false) {
            return null;
	} else {
            return tilit.get(0); // Tätä ei kyllä koskaan tapahdu, mutta muuten Java heittää errorin.
	}
    }
    
    public void poistaTili(String id) {
        tililoytyi = false;
        for (int i = 0; i < tilit.size(); i++) {
            if (tilit.get(i).getTilinro().equals(id)) {
                tililoytyi = true;
		tilit.remove(i);
                System.out.println("Tili poistettu.");
            }
        }
	if (tililoytyi == false) {
            System.out.println("Tiliä ei muutenkaan ollut olemassa.");
        }
    }
    
    public void printAll() {
        for (int i = 0; i < tilit.size(); i++) {
            System.out.println("Tilinumero: " + tilit.get(i).getTilinro() + " Tilillä rahaa: " + tilit.get(i).getRahaa());
                if (tilit.get(i).getLuotto() == 0) {
                    System.out.println();
                } else {
                    System.out.println(" Luottoraja: " + tilit.get(i).getLuotto());
                }
        }
    }
    
}

