/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package leffaselain;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author k4873
 */
public class Teatterilista {
    private ArrayList<Teatteri> teatterit = new ArrayList();

    public ArrayList<Teatteri> getTeatterit() {
        return teatterit;
    }
    
    private ArrayList<String> leffat = new ArrayList();

    public ArrayList<String> getLeffat() {
        return leffat;
    }
    
    private Document doc; 
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    
    public Teatterilista (String sisalto) {
        try {
                dbf = DocumentBuilderFactory.newInstance();
                db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(new StringReader(sisalto)));
                doc.getDocumentElement().normalize();
            
                parseTeatteriData();
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Teatterilista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void paivanohjelma (String sisalto, int alku, int loppu) {
        try {
            db.reset();
            doc = db.parse(new InputSource(new StringReader(sisalto)));
            doc.getDocumentElement().normalize();
            parseOhjelmaData(alku, loppu);
            
        } catch (SAXException | IOException ex) {
            Logger.getLogger(Teatterilista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void parseTeatteriData () {
        NodeList ids = doc.getElementsByTagName("ID");
        NodeList names = doc.getElementsByTagName("Name");
        System.out.println("Parsitaan teatteridataa.");
        for(int i = 1; i < ids.getLength(); i++) {
            teatterit.add(new Teatteri(Integer.parseInt(ids.item(i).getTextContent()), names.item(i).getTextContent()));
        }
    }

    private void parseOhjelmaData(int a, int l) { // a alku, l loppu - hakuajat
        leffat.clear();
        NodeList ajat = doc.getElementsByTagName("dttmShowStart");
        NodeList names = doc.getElementsByTagName("Title");
        String temp;
        System.out.println("Parsitaan ohjelmadataa.");
        for(int i = 0; i < ajat.getLength(); i++) {
            String[] aika = ajat.item(i).getTextContent().split("T");
            temp = aika[1] + "\t" + names.item(i).getTextContent();
            if (a <= Integer.parseInt(aika[1].substring(0, 2)) && Integer.parseInt(aika[1].substring(0, 2)) <= l-1)
                leffat.add(temp); // Melkoinen viritys

        }
    }
    
    public String tarkistaOnko(String leffa, String sisalto) { // Ottaa sisään YHDEN teatterin ohjelman ja leffannimen, palauttaa leffan esitysajan
        String kellonaika = "";
        //System.out.println("Aloitus");
        try {
            doc = db.parse(new InputSource(new StringReader(sisalto)));
            doc.getDocumentElement().normalize();
        } catch (SAXException | IOException ex) {
            Logger.getLogger(Teatterilista.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        NodeList ajat = doc.getElementsByTagName("dttmShowStart");
        NodeList names = doc.getElementsByTagName("Title");
        
        for (int g = 0; g < ajat.getLength(); g++) {
            if (names.item(g).getTextContent().toLowerCase().contains(leffa.toLowerCase())) {
                kellonaika = ajat.item(g).getTextContent().substring(11, 16) + " " + names.item(g).getTextContent();
                break;
            }
        }
        //System.out.println("Ajettu");
        return kellonaika;
    }


}
