/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tekstieditori;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author k4873
 */
public class FXMLDocumentController implements Initializable {
    

    @FXML
    private TextArea textInField;
    @FXML
    private TextField fileField;
    
    
    
    @FXML
    private void tallenna () throws FileNotFoundException, IOException {
        
        String tied = fileField.getText();
        BufferedWriter buff = new BufferedWriter(new FileWriter(tied));
        buff.write(textInField.getText());
        buff.close();
        
    }
    
    @FXML
    private void lataa () throws IOException {
        String temp;
        String tied = fileField.getText();
        BufferedReader buff = new BufferedReader(new FileReader(tied));
        
        while ((temp = buff.readLine()) != null) {
            textInField.setText(textInField.getText() + temp + "\n");
        }
        buff.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
