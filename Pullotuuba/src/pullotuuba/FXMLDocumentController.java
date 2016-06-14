/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pullotuuba;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import pullomaatti.maatti;

/**
 *
 * @author k4873
 */
public class FXMLDocumentController implements Initializable {
    
    private final maatti auto = maatti.getInstance();
    private final String[] nimet = {"Kokis", "Pepsi", "Fanta"};
    private final String[] koot = {"0.5", "0.33"};
    
    
    @FXML
    private ComboBox<String> comboDrink;
    @FXML
    private Label textOutput;
    @FXML
    private Label rahaOutput;
    @FXML
    private Slider euroSlider;
    @FXML
    private ComboBox<String> comboSize;
    @FXML
    private Label listSpace;

    public FXMLDocumentController() throws IOException {
        
    }
    
    @FXML
    private void osta() throws IOException {
        double alkuraha = auto.rahaa, erotus;
        
        int vert = auto.ostaPullo(comboDrink.getSelectionModel().getSelectedItem(),
                Double.parseDouble(comboSize.getSelectionModel().getSelectedItem()));
        if (vert == 0) {
            textOutput.setText("KA-CHUNK! " + comboDrink.getSelectionModel().getSelectedItem()+ " tipahti luukusta!");
            rahaUpdate();
            erotus = alkuraha - auto.rahaa; // Vähän joutuu kikkailemaan
            
            listSpace.setText(auto.listaaPullot());
            BufferedWriter buff = new BufferedWriter(new FileWriter("kuitti.txt"));
            buff.write("Kuitti\n\nEdellinen ostos: " + comboDrink.getSelectionModel().getSelectedItem() + 
                ", " + comboSize.getSelectionModel().getSelectedItem() + "l, " + 
                    String.format("%.2f", erotus) + " €");
            buff.close();
        } else if (vert == 1) {
            textOutput.setText("Et ole asettanut tarpeeksi rahaa!");
        } else {
            textOutput.setText("Haluamaasi pulloa ei ole varastossa :(");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboDrink.getItems().addAll(nimet);
        comboSize.getItems().addAll(koot);
        listSpace.setText(auto.listaaPullot());
    }    
    
    @FXML
    private void rahanLisays () {
        auto.lisaaRahaa(euroSlider.getValue());
        this.rahaUpdate();
        textOutput.setText("Klink!");
        euroSlider.setValue(0);
    }
    
    @FXML
    private void rahaPalautus () {
        auto.palautarahat();
        this.rahaUpdate();
        textOutput.setText("Kilinkilin! Rahat pulautettu.");
    }
    
    private void rahaUpdate() {
        rahaOutput.setText("Rahaa: " + String.format("%.2f", auto.rahaa) + " €");
    }
    
}
