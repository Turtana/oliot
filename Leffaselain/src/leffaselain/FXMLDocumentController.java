/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package leffaselain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author k4873
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ComboBox<Teatteri> comboList;
    @FXML
    private TextField paivaInput;
    @FXML
    private ListView<String> filmList;
    
    private Teatterilista tel;
    @FXML
    private TextField alkuInput;
    @FXML
    private TextField loppuInput;
    @FXML
    private TextField hakuPalkki;
    
    private ArrayList<String> leffanimilista = new ArrayList();
    
    private ArrayList<String> esitysajat = new ArrayList();
    private String[] asdd;
    private String aargh, aika;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            nettiJussi();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void nettiJussi() throws MalformedURLException, IOException { // Tämä vedetään heti alussa
        URL url = new URL ("http://www.finnkino.fi/xml/TheatreAreas/");
        
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        
        String rivi, sisalto = "";
        
        while ((rivi = br.readLine()) != null) {
            sisalto += rivi + "\n";
        }
        
        // isoLabel.setText(sisalto);
        
        br.close();
        
        tel = new Teatterilista(sisalto);
        
        for (Teatteri tet : tel.getTeatterit()) {
            comboList.getItems().add(tet);
        }
    }
    
    @FXML
    private void paivanLeffat() throws MalformedURLException, IOException {
        
        int alku = 00, loppu = 23; 
        
        filmList.getItems().clear();

        
        int id = comboList.getSelectionModel().getSelectedItem().getId();
        
        Date pvm = new Date();
        
        SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
        
        if ("".equals(paivaInput.getText())) {
            paivaInput.setText(ft.format(pvm));
        }
        
        String date = paivaInput.getText();
        
        URL leffaurl = new URL ("http://www.finnkino.fi/xml/Schedule/?area=" + Integer.toString(id) + "&dt=" + date);
        BufferedReader br = new BufferedReader(new InputStreamReader(leffaurl.openStream()));
        String rivi, sisalto = "";
        
        while ((rivi = br.readLine()) != null) {
            sisalto += rivi + "\n";
        }
        br.close();
        
        if (!"".equals(alkuInput.getText())) {
            alku = Integer.parseInt(alkuInput.getText());
        }
        
        if (!"".equals(loppuInput.getText())) {
            loppu = Integer.parseInt(loppuInput.getText());
        }
        
        tel.paivanohjelma(sisalto, alku, loppu);
        for (String s : tel.getLeffat()) {
            filmList.getItems().add(s);
        }
        
    }
    
    @FXML
    private void leffahaku () throws MalformedURLException, IOException {
        
        filmList.getItems().clear();
        
        Date pvm = new Date();
        
        SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
        
        if ("".equals(paivaInput.getText())) {
            paivaInput.setText(ft.format(pvm));
        }
        
        leffanimilista.clear();
        esitysajat.clear();
        
        String date = paivaInput.getText();
        
        String nimi = hakuPalkki.getText();
        
        String zeta;
        
        if ("".equals(nimi))
            return;
        
        for (Teatteri teatteri : tel.getTeatterit()) {
            URL urli = new URL ("http://www.finnkino.fi/xml/Schedule/?area=" + Integer.toString(teatteri.getId()) + "&dt=" + date);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(urli.openStream()));
            String rivi, sisalto = "";
        
            while ((rivi = br.readLine()) != null) {
                sisalto += rivi + "\n";
            }
            br.close();
            
            zeta = tel.tarkistaOnko(nimi, sisalto);
            //System.out.println("Weeee");
            aika = zeta.substring(0, 5);
            aargh = zeta.substring(6, zeta.length());
            if (zeta.equals("")) {
                continue;
            } else if (!leffanimilista.contains(aargh)) {
                leffanimilista.add(aargh); // PELKKÄ nimi
            }
            esitysajat.add(aika + " " + teatteri.getPaikka() + " %" + aargh); // "17:30 Espoo %Warcraft: The Beginning"
        }
        
        for (String leffanimi : leffanimilista) {
            filmList.getItems().add(leffanimi);
            for (String esitys : esitysajat) {
                asdd = esitys.split("%");
                if (asdd[1].equals(leffanimi)) {
                    filmList.getItems().add("\t" + asdd[0]);
                }
            }
        }
        
        
        System.out.println("Leffahaku ajettu");
    }
    
}
