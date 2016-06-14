/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tappelu;

/**
 *
 * @author k4873
 */
public class Character {
    
    String nimi;
    
    public WeaponBehavior weapon;
    
    public Character () {
        
        
        // asd
        
    }
    
    public void addWeapon(String ase) {
        
        if (ase == "veitsi")
        weapon = new KnifeBehavior();
        if (ase == "kirves")
        weapon = new AxeBehavior();
        if (ase == "miekka")
        weapon = new SwordBehavior();
        if (ase == "nuija")
        weapon = new ClubBehavior();
    }
    
    
    public void fight() {
        
        weapon.useWeapon(nimi);
    }
    
}

class WeaponBehavior {
    
    protected String keppi;
    
    WeaponBehavior () {
        
        // asd
    }
    
    public void useWeapon (String hahmo) {
        
        System.out.println(hahmo + " tappelee aseella " + keppi);
        
    }
}

class KnifeBehavior extends WeaponBehavior {
    
    KnifeBehavior () {
        
        keppi = "Knife";
        
    }

}

class AxeBehavior extends WeaponBehavior {
    
    AxeBehavior () {
        
        keppi = "Axe";
        
    }
    
}

class SwordBehavior extends WeaponBehavior {
    
    SwordBehavior () {
        
        keppi = "Sword";
        
    }
    
}

class ClubBehavior extends WeaponBehavior {
    
    ClubBehavior () {
        
        keppi = "Club";
        
    }
    
}

class King extends Character {
    King () {
        nimi = "King";
    }
}

class Queen extends Character {
    Queen () {
        nimi = "Queen";
    }
}

class Knight extends Character {
    Knight () {
        nimi = "Knight";
    }
}

class Troll extends Character {
    Troll () {
        nimi = "Troll";
    }
}