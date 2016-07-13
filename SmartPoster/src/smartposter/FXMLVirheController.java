/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author k4873
 */
public class FXMLVirheController implements Initializable {
    @FXML
    private Button sulkuNappi;
    @FXML
    private Label varoText;
    private Varasto fgh = Varasto.getInstance();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        varoText.setText(fgh.getError());
    }    

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) sulkuNappi.getScene().getWindow();
        stage.close();
    }
}
