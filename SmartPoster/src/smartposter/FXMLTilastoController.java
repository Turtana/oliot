/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author k4873
 */
public class FXMLTilastoController implements Initializable {
    @FXML
    private TableView<Historydump> historyTable;
    
    private final ObservableList<Historydump> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn kelloSarake;
    @FXML
    private TableColumn lahtoSarake;
    @FXML
    private TableColumn kohdeSarake;
    @FXML
    private TableColumn esineSarake;
    @FXML
    private TableColumn pakettiSarake;
    @FXML
    private TableColumn matkaSarake;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kelloSarake.setCellValueFactory(new PropertyValueFactory<Historydump, String>("a"));
        lahtoSarake.setCellValueFactory(new PropertyValueFactory<Historydump, String>("b"));
        kohdeSarake.setCellValueFactory(new PropertyValueFactory<Historydump, String>("c"));
        esineSarake.setCellValueFactory(new PropertyValueFactory<Historydump, String>("d"));
        pakettiSarake.setCellValueFactory(new PropertyValueFactory<Historydump, String>("e"));
        matkaSarake.setCellValueFactory(new PropertyValueFactory<Historydump, Double>("f"));
        
        historyTable.setItems(data); // Taulukon PITÄISI päivittyä datan myötä
        updateHistory();
    }    
    
    private void updateHistory () {
        data.clear();
        ResultSet rs, rt;
        Connection con = null;
        Historydump h;
        try {
            con = DriverManager.getConnection("jdbc:sqlite:timotei.db");
            PreparedStatement ehaku, thaku;
            
            ehaku = con.prepareStatement("SELECT history.eventtime, address.city " // Pitää vetää kaksi hakua, ettei mene sekaisin smartpost-kaupungit.
                    + "FROM (history INNER JOIN smartpost ON history.startid = smartpost.smartid) "
                    + "INNER JOIN address ON smartpost.smartid = address.addressid;");
            rs = ehaku.executeQuery(); // Eka puolikas
            
            thaku = con.prepareStatement("SELECT address.city, item.name, package.codename, history.distance "
                    + "FROM (((history INNER JOIN smartpost ON history.destid = smartpost.smartid) "
                    + "INNER JOIN address ON smartpost.smartid = address.addressid) "
                    + "INNER JOIN item ON history.itemid = item.itemid) "
                    + "INNER JOIN package ON history.packageid = package.packageid;");
            rt = thaku.executeQuery(); // Toka puolikas
            
            while (rs.next()) {
                rt.next();
                h = new Historydump();
                h.a.set(rs.getString("eventtime"));
                h.b.set(rs.getString("city"));
                h.c.set(rt.getString("city"));
                h.d.set(rt.getString("name"));
                h.e.set(rt.getString("codename"));
                h.f.set(rt.getDouble("distance"));
                
                System.out.println(rs.getString("city"));
                System.out.println(rt.getString("city"));
                data.add(h);
                System.out.println(rs.getString("eventtime"));
            }
            
        } catch (SQLException ex) {
            System.out.println("Olet tuomittu toistamaan sen");
            Logger.getLogger(FXMLTilastoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLTilastoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


    @FXML
    private void nukeHistory(ActionEvent event) {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:sqlite:timotei.db");
            PreparedStatement pois = con.prepareStatement("DELETE FROM history;"); // BOOOOOM
            pois.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Historia on historiaa. HAHAHAHAHAHA!");
        updateHistory();
    }
    
    public class Historydump {
        public SimpleStringProperty a = new SimpleStringProperty();
        public SimpleStringProperty b = new SimpleStringProperty();
        public SimpleStringProperty c = new SimpleStringProperty();
        public SimpleStringProperty d = new SimpleStringProperty();
        public SimpleStringProperty e = new SimpleStringProperty();
        public SimpleDoubleProperty f = new SimpleDoubleProperty();
        
        public String getA () {
            return a.get();
        }
        public String getB () {
            return b.get();
        }
        public String getC () {
            return c.get();
        }
        public String getD () {
            return d.get();
        }
        public String getE () {
            return e.get();
        }
        public Double getF () {
            return f.get();
        }
    }
}
