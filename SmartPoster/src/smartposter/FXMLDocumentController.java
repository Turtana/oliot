/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

/**
 *
 * @author k4873
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private WebView kartta;
    
    private final XmlReader xreader = XmlReader.getInstance();
    private SmartPostList slist = SmartPostList.getInstance();
    
    //private String address, postInfo;
    
    @FXML
    private ComboBox<SmartPost> comboPost;

    @FXML
    private Label karttanappula;
    
    private String nappulatext = "Satelliitti";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kartta.getEngine().load(getClass().getResource("index.html").toExternalForm());
        haeAutomaatit();
        kartta.requestFocus();
    }    

    private void haeAutomaatit() {
        try {
            URL urli = new URL("http://smartpost.ee/fi_apt.xml");
            BufferedReader br = new BufferedReader(new InputStreamReader(urli.openStream()));
            
            String rivi, sisalto = "";
        
            while ((rivi = br.readLine()) != null) {
                sisalto += rivi + "\n";
            }
            br.close();
            
            slist = xreader.parsePostData(sisalto, slist);
            
            for (SmartPost s : slist.getPostList()) {
                comboPost.getItems().add(s);
            }
            

        } catch (MalformedURLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Jokin taisi mennä pieleen URL:n kanssa.");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Hupsista keikkaa! Bufferilukija ei toimi!");
        }
        
        
    }
  
    @FXML
    private void tiekartta() {
        if (null != karttanappula.getText()) switch (karttanappula.getText()) {
            case "Satelliitti":
                kartta.getEngine().executeScript("document.setMapTypeHybrid()");
                karttanappula.setText("Tiekartta");
                nappulatext = "Tiekartta";
                break;
            case "Tiekartta":
                kartta.getEngine().executeScript("document.setMapTypeRoad()");
                karttanappula.setText("Satelliitti");
                nappulatext = "Satelliitti";
                break;
        }
        
        kartta.requestFocus();
    }

    @FXML
    private void labelPop(MouseEvent event) {
        
        karttanappula.setText(nappulatext);
        karttanappula.setMinWidth(130);
    }

    @FXML
    private void labelExit(MouseEvent event) {
        karttanappula.setMinWidth(35);
        karttanappula.setText(">");
    }
    

}
