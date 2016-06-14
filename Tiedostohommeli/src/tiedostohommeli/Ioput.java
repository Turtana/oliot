/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiedostohommeli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author k4873
 */
public class Ioput {
    
    private String rivi;
    
    
    public void readFile(String tied) throws FileNotFoundException, IOException {
        try {
            BufferedReader in = new BufferedReader(new FileReader(tied));
            
            String asd = null;
            
            while ((asd = in.readLine()) != null) {
                System.out.println(asd);
            }
            in.close();
        } catch (IOException ex) {
            System.out.println("Tiedostoa ei löytynyt!");
        }
    }
    
    public void readAndWrite(String input, String output) {
        
        
        
        try {
            BufferedReader in = new BufferedReader(new FileReader(input));
            BufferedWriter out = new BufferedWriter(new FileWriter(output));
            
            
            
            while ((rivi = in.readLine()) != null) {
                if (rivi.length() < 30 && rivi.trim().length() != 0) { // Rivi on alle 30 merkkiä eikä tyhjä
                    System.out.println(rivi);
                    if (rivi.contains("v"))
                        out.write(rivi + "\n");
                }
                
            }

            in.close();
            out.close();
        } catch (IOException ex) {
            System.out.println("Tiedostoa ei löytynyt!");
        }
    }
    
    public void lueZip(String zipfilu) {
        
        try {
            
            ZipFile zippitied = new ZipFile(zipfilu);
            Enumeration<? extends ZipEntry> entryt = zippitied.entries();
            
            while (entryt.hasMoreElements()) {
                ZipEntry zipattu = entryt.nextElement();
                
                String nimi = zipattu.getName();
                if (nimi.endsWith(".txt")) {
                    InputStream in = zippitied.getInputStream(zipattu);
                    try (BufferedReader buffi = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
                        while ((rivi = buffi.readLine()) != null) {
                            System.out.println(rivi);
                        }
                    }
                }
                
            }
            zippitied.close();
        } catch (IOException ex) {
            System.out.println("Tiedostoa ei löytynyt!");
        }  
    }
}
