/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import packagepackage.Pakkaus;

/**
 * FXML Controller class
 *
 * @author k4873
 */
public class FXMLStartController implements Initializable {
    @FXML
    private Button riseAndShine;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void oldHat(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/smartposter/FXMLDocument.fxml"));
            
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.setTitle("Timotei");
            stage.show();
            heippa();
        } catch (IOException ex) {
            System.out.println("Voi rähmä.");
            Logger.getLogger(FXMLStartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void startAnew(ActionEvent event) {
        ItemList.nukeFlash(); // Tervemenoa tavaratyypit
        Varasto v = Varasto.getInstance();
        v.delAll(); // Varasto viskattu veks
        SmartPostList.getInstance().nukeFlash(); // Sayonara SmartPostit
        vaillaMenneisyytta(); // Historia hävitetty
        
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/smartposter/FXMLDocument.fxml"));
            
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.setTitle("Timotei");
            stage.show();
            heippa();
        } catch (IOException ex) {
            System.out.println("Voi rähmä.");
            Logger.getLogger(FXMLStartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void heippa () {
        Stage raks = (Stage) riseAndShine.getScene().getWindow();
        raks.close();
    }
    
    private void vaillaMenneisyytta() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:sqlite:timotei.db");
            PreparedStatement pois = con.prepareStatement("DELETE FROM history;");
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
    }
}
