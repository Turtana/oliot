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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author k4873
 */
public class FXMLKannatController implements Initializable {

    /**
     * Initializes the controller class
     */
    private final XmlReader x = XmlReader.getInstance();
    @FXML
    private Button okButton;
    @FXML
    private ComboBox<SmartPost> comboSmart;
    @FXML
    private ComboBox<Item> comboItem;
    
    private Stage uusiE;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refresh();
        restock();
        // TODO
    }    

    @FXML
    private void downloadPosts(ActionEvent event) throws ClassNotFoundException, InterruptedException {
        x.haeAutomaatit();
        refresh();
    }

    @FXML
    private void OK(ActionEvent event) throws ClassNotFoundException, InterruptedException {
        try {
            FXMLDocumentController.refreshPosts();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLKannatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
    
    private void refresh () {
        comboSmart.getItems().clear();
        for (SmartPost g : SmartPostList.getAllPosts()) {
            comboSmart.getItems().add(g);
        }
    }
    
    @FXML
    private void restock () {
        comboItem.getItems().clear();
        for (Item g : ItemList.getAllItems()) {
            comboItem.getItems().add(g);
        }
    }

    @FXML
    private void deletePost(ActionEvent event) {
        if (!comboSmart.getSelectionModel().isEmpty())
            SmartPostList.removePost(comboSmart.getSelectionModel().getSelectedItem());
        refresh();
    }

    @FXML
    private void nukeEmAll(ActionEvent event) {
        SmartPostList.nukeFlash();
        refresh();
    }

    @FXML
    private void newItem(ActionEvent event) {
        try {
            // Uusi ikkuna esineille
            uusiE = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLUusiEsine.fxml"));
            Scene scene = new Scene(root);
            uusiE.setTitle("Luo uusi esine");
            uusiE.setScene(scene);
            uusiE.sizeToScene();
            uusiE.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLKannatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteItem(ActionEvent event) {
        if (!comboItem.getSelectionModel().isEmpty()) {
            ItemList.removeItem(comboItem.getSelectionModel().getSelectedItem());
        }
        restock();
    }

    @FXML
    private void nukeAllItems(ActionEvent event) {
        ItemList.nukeFlash();
        restock();
    }
}
