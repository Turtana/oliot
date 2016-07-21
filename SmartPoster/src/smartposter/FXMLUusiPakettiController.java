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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import packagepackage.Pakkaus;
import packagepackage.Pikavauhti;
import packagepackage.Stressipurku;
import packagepackage.Turvakyyti;

/**
 * FXML Controller class
 *
 * @author k4873
 */
public class FXMLUusiPakettiController implements Initializable {
    @FXML
    private ComboBox<Item> comboEsine;
    @FXML
    private Label esineLabel;
    @FXML
    private Label pakkausLabel;
    @FXML
    private ComboBox<Pakkaus> comboPakkaus;
    @FXML
    private Button sulkuNappi;
    private Pakkaus pak;
    private Item tavara;
    private final Varasto varus = Varasto.getInstance();

    private final Pikavauhti pika = new Pikavauhti(null);
    private final Stressipurku stres = new Stressipurku(null);
    private final Turvakyyti turv = new Turvakyyti(null);
    private Stage virhe;
    private String urli;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        urli = "jdbc:sqlite:timotei.db";
        comboFill();
    }    

    @FXML
    private void closePopup() {
        Stage stage = (Stage) sulkuNappi.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void verify() throws IOException {
        pak = comboPakkaus.getSelectionModel().getSelectedItem();
        tavara = comboEsine.getSelectionModel().getSelectedItem();
        
        if (comboPakkaus.getSelectionModel().isEmpty()
                || comboEsine.getSelectionModel().isEmpty()) {
            varus.setError("Anna sekä esine että pakettityyppi! Tyhjät paketit ja irtoesineet ovat kiellettyjä.");
            virhe = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/smartposter/FXMLVirhe.fxml"));
            Scene scene = new Scene(root);
            virhe.setTitle("Liian vähän parametreja");
            virhe.setScene(scene);
            virhe.sizeToScene();
            virhe.show();
            
            return;
        }
        
        if (pak.getKoko() < tavara.getKoko()) {
            eiMahdu(tavara);
        } else {
            switch (pak.getVauhti()) {
                case 3:
                    varus.addThing(new Stressipurku(tavara));
                    break;
                case 2:
                    varus.addThing(new Turvakyyti(tavara));
                    break;
                case 1:
                    varus.addThing(new Pikavauhti(tavara));
                    break;
                default:
                    System.out.println("nope");
            }
            //FXMLDocumentController.refreshWares();
            closePopup();
        }
    }
    
    private void comboFill () {
        comboPakkaus.getItems().add(pika);
        comboPakkaus.getItems().add(turv); // Tyhjiä esimerkkipaketteja valikon takia
        comboPakkaus.getItems().add(stres);
        Connection con = null;
        
        try {
            con = DriverManager.getConnection(urli);
            PreparedStatement zip = con.prepareStatement("SELECT * FROM item;");
            ResultSet rs = zip.executeQuery();
            
            while (rs.next()) {
                comboEsine.getItems().add(new Item(rs.getInt("itemid"), rs.getString("description"),
                        rs.getInt("size"), rs.getBoolean("breakable"), rs.getString("name")));
            }
        } catch (SQLException ex) {
            System.out.println("No voi herttinen sentään.");
            Logger.getLogger(FXMLUusiPakettiController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLUusiPakettiController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    private void labelRefresh(MouseEvent event) {
        if (!comboEsine.getSelectionModel().isEmpty())
            esineLabel.setText(comboEsine.getSelectionModel().getSelectedItem().kuvaile());
        if (!comboPakkaus.getSelectionModel().isEmpty()) {
            pakkausLabel.setText(comboPakkaus.getSelectionModel().getSelectedItem().kuvaile());
        }
    }
    
    private void eiMahdu(Item i) throws IOException {
        varus.setError(i.toString() + " ei mahdu valitsemaasi pakettiin! Valitse isompi paketti tai luovu leikistä.");
        virhe = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/smartposter/FXMLVirhe.fxml"));
        Scene scene = new Scene(root);
        virhe.setTitle("Liian pieni paketti");
        virhe.setScene(scene);
        virhe.sizeToScene();
        virhe.show();
    }
}
