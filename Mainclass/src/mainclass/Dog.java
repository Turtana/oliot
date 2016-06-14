import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;

class Dog {
    
    private String name;
    private String says;
    
    public Dog(String nimi) {
        
        if ((nimi.trim()).isEmpty()) {
            name = "Doge";
        } else {
            name = nimi;
        }
        
        System.out.println("Hei, nimeni on " + name + "!");
        
        says = "";
    }
    
    public void speak(String lause) {
        
        says = lause;
		
        System.out.println(name + ": " + says);
        
        Scanner skannaus = new Scanner(says);
        
        while (skannaus.hasNext()) {
            if (skannaus.hasNextInt()) {
                System.out.println("Hau! Noudettu luku " + skannaus.nextInt());
                continue;
            }
            
            if (skannaus.hasNextBoolean()) {
                System.out.println("Wow! Noudettu totuusarvo " + skannaus.nextBoolean());
                continue;
            }
            skannaus.next();
        }
        
        skannaus.close();
        
        
    }
    
    
}