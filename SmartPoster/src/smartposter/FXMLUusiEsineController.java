/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import item.Item;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author k4873
 */
public class FXMLUusiEsineController implements Initializable {
    @FXML
    private TextField nimiField;
    @FXML
    private TextField kokoField;
    @FXML
    private CheckBox sarkyvaBox;
    @FXML
    private TextArea kuvausArea;
    @FXML
    private Button peruutaNappi;
    
    private Stage virhe;
    private Varasto varus;
    private boolean sarkyva;
    private Item asd;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        varus = Varasto.getInstance();
        kuvausArea.setPromptText("Kuvaus");
    }    

    @FXML
    private void peruuta() {
        Stage stage = (Stage) peruutaNappi.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void OK() {
        int iddi;
        if ("".equals(nimiField.getText()) && "".equals(kokoField.getText())) {
            try {
                varus.setError("Et antanut kaikkia tietoja uutta esinettä varten! Nimi ja koko ovat välttämättömiä.");
                virhe = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLVirhe.fxml"));
                Scene scene = new Scene(root);
                virhe.setTitle("Liian vähän parametreja");
                virhe.setScene(scene);
                virhe.sizeToScene();  
                virhe.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLUusiEsineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            sarkyva = sarkyvaBox.isSelected();
            iddi = ItemList.getFreeId();
            asd = new Item(iddi, kuvausArea.getText(), Integer.parseInt(kokoField.getText()), sarkyva, nimiField.getText());
            ItemList.addItem(asd);
            peruuta(); // Ja suljetaan ikkuna
        }
    }
}
