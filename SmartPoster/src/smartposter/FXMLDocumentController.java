/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import item.Item;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
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
    private ComboBox<SmartPost> comboPost;
    @FXML
    private ComboBox<SmartPost> comboVasta;//anottaja

    @FXML
    private Label karttanappula;
    
    @FXML
    private ComboBox<Pakkaus> comboPaketti;
    
    private String nappulatext = "Satelliitti";
    private static Varasto varas = Varasto.getInstance();
    private Stage popuppi, virhe, tietokannat, historia;
    private SmartPost temp;
    private String[] herpy;
    private String derp;
    private Pakkaus rakkaus;
    private Item kapine;
    private double matka;
    
    @FXML
    private Label titleText;
    @FXML
    private Pane poistoPane;
    @FXML
    private Ellipse satelliteDish;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kartta.getEngine().load(getClass().getResource("index.html").toExternalForm());
        kartta.requestFocus();
        try {
            refreshPosts(); // Jos vanhoja SmartPosteja on tietokannassa, kaivetaan ne esille
            refreshWares(); // Sama koskee paketteja
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
        satelliteDish.setLayoutX(330);
    }

    @FXML
    private void labelExit(MouseEvent event) {
        karttanappula.setMinWidth(35);
        karttanappula.setText(">");
        satelliteDish.setLayoutX(236);
    }

    @FXML
    private void createPacket(ActionEvent event) {
        try {
            popuppi = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/smartposter/FXMLUusiPaketti.fxml"));
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
    public void refreshWares() {
        comboPaketti.getItems().clear();
        for (Pakkaus p : varas.getList()) {
            comboPaketti.getItems().add(p);
        }
        poistoPane.setVisible(false);
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
        
        if (!comboPost.getSelectionModel().isEmpty() && !comboVasta.getSelectionModel().isEmpty()
                && !comboPaketti.getSelectionModel().isEmpty()) {
            
            rakkaus = comboPaketti.getSelectionModel().getSelectedItem();
            kapine = comboPaketti.getSelectionModel().getSelectedItem().avaa(); // Mitähän ylläriä sisällä on?
            
            if (!rakkaus.isSafe() && kapine.isBreakable()) { // Runollista :,(
                varas.delThing(rakkaus);
                refreshWares();
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
                matka = calculateDistance(herpy);
                
                System.out.println("Paketin maksimimatka on " + comboPaketti.getSelectionModel().getSelectedItem().getMatka() + " km, vauhti " + comboPaketti.getSelectionModel().getSelectedItem().getVauhti());
                if (matka > comboPaketti.getSelectionModel().getSelectedItem().getMatka()) {
                    varas.setError("Valitsemaasi pakettia ei voi lähettää noin kauas!"); // !!!
                    virhe = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/smartposter/FXMLVirhe.fxml"));
                    Scene scene = new Scene(root);
                    virhe.setTitle("Liian pitkä matka");
                    virhe.setScene(scene);
                    virhe.sizeToScene();
                    virhe.show();
                } else {
                    kartta.getEngine().executeScript("document.createPath("
                            + Arrays.toString(herpy) + ", 'blue', "
                            + comboPaketti.getSelectionModel().getSelectedItem().getVauhti() + ")");
                    try {
                        forHistorySake(matka);
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    private void forHistorySake(double mat) throws SQLException { // Merkkaa jokaisen tehdyn lähetyksen historiankirjoihin
        String siti = "";
        Connection con = DriverManager.getConnection("jdbc:sqlite:timotei.db");
        Date nyt = new Date();
        SimpleDateFormat fort = new SimpleDateFormat ("dd.MM.yyyy hh:mm:ss");
        int laskuri = 0;
        try {
            
            PreparedStatement check = con.prepareStatement("SELECT eventid FROM event;");
            ResultSet z = check.executeQuery();
            while (z.next()) { // Etsitään seuraava vapaa id
                laskuri++; // Joka on arvoltaan laskuri
            }
            String g = String.format(Locale.US, "%.2f", mat);
            mat = Double.parseDouble(g);
            PreparedStatement hist = con.prepareStatement("INSERT INTO event "
                    + "(eventid, startid, destid, packageid, itemid) "
                    + "VALUES (?, ?, ?, ?, ?);");
            hist.setInt(1, laskuri);
            hist.setInt(2, comboPost.getSelectionModel().getSelectedItem().getId());
            hist.setInt(3, comboVasta.getSelectionModel().getSelectedItem().getId());
            hist.setInt(4, comboPaketti.getSelectionModel().getSelectedItem().getId());
            hist.setInt(5, comboPaketti.getSelectionModel().getSelectedItem().avaa().getId());
            hist.executeUpdate();
            
            hist = con.prepareStatement("SELECT address.city " // Pitää vetää kaksi hakua, ettei mene sekaisin smartpost-kaupungit.
                    + "FROM (event INNER JOIN smartpost ON event.startid = smartpost.smartid) "
                    + "INNER JOIN address ON smartpost.smartid = address.addressid "
                    + "WHERE event.eventid = ?;");
            hist.setInt(1, laskuri);
            z = hist.executeQuery();
            if (z.next()) {
                siti = z.getString("city"); // Lähtökaupunki
            }
            
            hist = con.prepareStatement("SELECT address.city, item.name, package.codename "
                    + "FROM (((event INNER JOIN smartpost ON event.destid = smartpost.smartid) "
                    + "INNER JOIN address ON smartpost.smartid = address.addressid) "
                    + "INNER JOIN item ON event.itemid = item.itemid) "
                    + "INNER JOIN package ON event.packageid = package.packageid "
                    + "WHERE event.eventid = ?;");
            hist.setInt(1, laskuri);
            z = hist.executeQuery();
            
            hist = con.prepareStatement("INSERT INTO history "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?);");
            hist.setInt(1, laskuri); // Id
            hist.setString(2, fort.format(nyt)); // Kellonaika
            hist.setString(3, siti); // Lähtökaupunki
            hist.setString(4, z.getString("city")); // Kohdekaupunki
            hist.setString(5, z.getString("codename")); // Paketin nimi
            hist.setString(6, z.getString("name")); // Esineen nimi
            hist.setDouble(7, mat); // Välimatka
            hist.executeUpdate();
            System.out.println("Historiaid: " + laskuri);
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                con.close();
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
    
    private double calculateDistance (String[] s) { // Linnuntietä vetää, mutta vetäköön
        double d;
        double sade = 6378.137; // Huomasin vasta hyvin myöhään, että viivanvetäjäfunktio indexissä palauttaa välimatkan. Käytetään nyt tätä.
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
        Parent root = FXMLLoader.load(getClass().getResource("/smartposter/FXMLKannat.fxml"));
        Scene scene = new Scene(root);
        tietokannat.setTitle("Muokkaa tietokantoja");
        tietokannat.setScene(scene);
        tietokannat.sizeToScene();
        tietokannat.show();
    }

    @FXML
    public void refreshPosts() throws SQLException, ClassNotFoundException {
        int sad = 0;
        
        comboPost.getItems().clear();
        //comboVasta.getItems().clear();
        
        for (SmartPost a : SmartPostList.getInstance().getAllPosts()) {
            comboPost.getItems().add(a);
            //comboVasta.getItems().add(a);
            sad++;
        }
        //System.out.println("Yhteensä " + sad + " SmartPostia");
        //refreshPostsv();
        //refreshWares();
    }

    @FXML
    private void poistoVisible(ActionEvent event) {
        poistoPane.setVisible(true);
    }

    @FXML
    private void delPaket(ActionEvent event) {
        varas.delThing(comboPaketti.getSelectionModel().getSelectedItem());
        refreshWares();
    }

    @FXML
    private void openHistory(ActionEvent event) {
        try {
            historia = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/smartposter/FXMLTilasto.fxml"));
            Scene scene = new Scene(root);
            historia.setTitle("Lähetyshistoria");
            historia.setScene(scene);
            historia.sizeToScene();
            historia.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void refreshPostsv() {
        int sad = 0;
        
        //comboPost.getItems().clear();
        comboVasta.getItems().clear();
        
        for (SmartPost a : SmartPostList.getInstance().getAllPosts()) {
            //comboPost.getItems().add(a);
            comboVasta.getItems().add(a);
            sad++;
        }
        //System.out.println("Yhteensä " + sad + " Smartia");
    }
}
