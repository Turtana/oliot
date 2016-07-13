/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import item.Item;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import packagepackage.Pakkaus;

/**
 *
 * @author k4873
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private WebView kartta;
    
    private SmartPostList slist = SmartPostList.getInstance();
    
    @FXML
    private static ComboBox<SmartPost> comboPost;
    @FXML
    private static ComboBox<SmartPost> comboVasta;

    @FXML
    private Label karttanappula;
    
    @FXML
    private ComboBox<Pakkaus> comboPaketti;
    
    private String nappulatext = "Satelliitti";
    private final Varasto varas = Varasto.getInstance();
    private Stage popuppi, virhe, tietokannat;
    private SmartPost temp;
    private String[] herpy;
    private String derp;
    private Pakkaus rakkaus;
    private Item kapine;
    
    @FXML
    private Label titleText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kartta.getEngine().load(getClass().getResource("index.html").toExternalForm());
        kartta.requestFocus();
        try {
            refreshPosts(); // Jos vanhoja SmartPosteja on tietokannassa, kaivetaan ne esille
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        titleText.setTooltip(new Tooltip("Toiminnaltaan Itellaa Muistuttava Ohjelmisto,\nTietokantaa Edellyttävä Integraatio"));
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

    @FXML
    private void createPacket(ActionEvent event) {
        try {
            popuppi = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLUusiPaketti.fxml"));
            Scene scene = new Scene(root);
            
            popuppi.setTitle("Uusi paketti");
            popuppi.setScene(scene);
            popuppi.sizeToScene();
            
            popuppi.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void refreshWares() {
        //temp = comboPaketti.getSelectionModel().getSelectedItem();
        comboPaketti.getItems().clear();
        for (Pakkaus p : varas.getList()) { // Valinta häviää kun hiiren vie valikon päälle. Se ei ole bugi vaan ominaisuus!
            comboPaketti.getItems().add(p);
        }
        //comboPaketti.getSelectionModel().select(temp);
        //System.out.println(temp);
    }

    @FXML
    private void drawIt(ActionEvent event) {
        kartta.getEngine().executeScript("document.deleteMarkers('blue')");
        
        if (comboPost.getSelectionModel().isEmpty()) {
            return;
        } else {
            temp = comboPost.getSelectionModel().getSelectedItem();
        }
        kartta.getEngine().executeScript("document.goToLocation('"
                + temp.getAddress() + "', '" + temp.getInfo() + "', 'blue')");
        kartta.requestFocus();
    }
    // Aika samanlaiset funktiot on kyllä.
    @FXML
    private void drawThat(ActionEvent event) {
        kartta.getEngine().executeScript("document.deleteMarkers('red')");
        
        if (comboVasta.getSelectionModel().isEmpty()) {
            return;
        } else {
            temp = comboVasta.getSelectionModel().getSelectedItem();
        }
        kartta.getEngine().executeScript("document.goToLocation('"
                + temp.getAddress() + "', '" + temp.getInfo() + "', 'red')");
        kartta.requestFocus();
    }

    @FXML
    private void lahetaPaketti(ActionEvent event) throws IOException {
        rakkaus = comboPaketti.getSelectionModel().getSelectedItem();
        kapine = comboPaketti.getSelectionModel().getSelectedItem().avaa(); // Mitähän ylläriä sisällä on?
        
        if (!comboPost.getSelectionModel().isEmpty() && !comboVasta.getSelectionModel().isEmpty()
                && !comboPaketti.getSelectionModel().isEmpty()) {
            
            if (!rakkaus.isSafe() && kapine.isBreakable()) { // Runollista :,(
                varas.getList().remove(rakkaus);
                varas.setError("Esineesi on särkynyt matkalla! Olisi kannattanut pakata paremmin."); // !!!
                virhe = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLVirhe.fxml"));
                Scene scene = new Scene(root);
                virhe.setTitle("Pakettisi on särkynyt");
                virhe.setScene(scene);
                virhe.sizeToScene();
                virhe.show();
            } else {
                derp = comboPost.getSelectionModel().getSelectedItem().getCoords() // Kasataan koordit
                        + " " + comboVasta.getSelectionModel().getSelectedItem().getCoords();

                herpy = derp.split(" ");
                
                
                System.out.println("Paketin maksimimatka on " + comboPaketti.getSelectionModel().getSelectedItem().getMatka() + " km, vauhti " + comboPaketti.getSelectionModel().getSelectedItem().getVauhti());
                if (calculateDistance(herpy) > comboPaketti.getSelectionModel().getSelectedItem().getMatka()) {
                    varas.setError("Valitsemaasi pakettia ei voi lähettää noin kauas!"); // !!!
                    virhe = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("FXMLVirhe.fxml"));
                    Scene scene = new Scene(root);
                    virhe.setTitle("Liian pitkä matka");
                    virhe.setScene(scene);
                    virhe.sizeToScene();
                    virhe.show();
                } else {
                    kartta.getEngine().executeScript("document.createPath("
                            + Arrays.toString(herpy) + ", 'blue', "
                            + comboPaketti.getSelectionModel().getSelectedItem().getVauhti() + ")");
                }
            }
        }
    }

    @FXML
    private void karttaFokus(MouseEvent event) {
        kartta.requestFocus();
    }

    @FXML
    private void hardReset(ActionEvent event) {
        kartta.getEngine().executeScript("document.deletePaths()");
        kartta.getEngine().executeScript("document.deleteMarkers('boom')");
    }
    
    private double calculateDistance (String[] s) {
        double d;
        double sade = 6378.137;
        double dLat = (Double.parseDouble(s[0]) - Double.parseDouble(s[2])) * Math.PI / 180;
        double dLon = (Double.parseDouble(s[1]) - Double.parseDouble(s[3])) * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Double.parseDouble(s[0]) * Math.PI / 180) *
                Math.cos(Double.parseDouble(s[2]) * Math.PI / 180) *
                Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        d = sade * c;
        System.out.println(d + " km");
        return d; // kilometriä
        // Lähde: http://stackoverflow.com/questions/639695/how-to-convert-latitude-or-longitude-to-meters
    }

    @FXML
    private void hallitseKantoja(ActionEvent event) throws IOException {
        tietokannat = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLKannat.fxml"));
        Scene scene = new Scene(root);
        tietokannat.setTitle("Muokkaa tietokantoja");
        tietokannat.setScene(scene);
        tietokannat.sizeToScene();
        tietokannat.show();
    }

    @FXML
    public static void refreshPosts() throws SQLException, ClassNotFoundException {
        int sad = 0;
        comboPost.getItems().clear();
        comboVasta.getItems().clear();
        for (SmartPost a : SmartPostList.getAllPosts()) {
            comboPost.getItems().add(a);
            comboVasta.getItems().add(a);
            sad++;
        }
        System.out.println("Yhteensä " + sad);
    }
}
