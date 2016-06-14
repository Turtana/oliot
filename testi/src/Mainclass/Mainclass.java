
package Mainclass;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author k4873
 */
public class Mainclass {
    
    public static void main(String[] args) {
        char[] alkuvokal = {'A', 'E', 'I', 'U', 'O', 'Y', 'A', 'E', 'I', 'O'};
        char[] vokal = {'a', 'e', 'i', 'u', 'o', 'y', 'a', 'e', 'i', 'o'};
        char[] konso = {'k', 'r', 's', 't', 'n', 'p', 'l', 'h', 'v', 's'};
        Printtaa printtaa = new Printtaa();
        printtaa.teksti();
        System.out.print("My name is ");
        for (int i = 0; i < 10; i++) {
            double luku = Math.random() * 10;
            
            if (i == 0) {
                System.out.print(alkuvokal[(int)luku]);
                continue;
            }
            if (i % 2 == 0) {
                System.out.print(vokal[(int)luku]);
            } else {
                System.out.print(konso[(int)luku]);
            }
            
        }
        System.out.println(" and I have come to conquer you!");
        
        Rakennus linna = new Rakennus();
        
        System.out.println("My castle is " + linna.hankiKorkeus() + " meters tall and has " + linna.hankiIkkunat() + " windows!");
    }
}

class Printtaa {
    public void teksti() {
        System.out.println("Beware, World!");
    }
}

class Rakennus {
    private int korkeus;
    private int ikkunamaara;
   
    public Rakennus() {
        korkeus = (int) (Math.random() * 500);
        ikkunamaara = (int) (Math.random() * 1000);
    }
    
    public int hankiKorkeus() {
        return korkeus;
    }
    
    public int hankiIkkunat() {
        return ikkunamaara;
    }
    
}
