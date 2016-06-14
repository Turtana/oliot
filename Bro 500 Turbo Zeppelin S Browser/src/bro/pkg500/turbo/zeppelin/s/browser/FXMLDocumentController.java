/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bro.pkg500.turbo.zeppelin.s.browser;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;

/**
 *
 * @author k4873
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private TextField urlField;
    @FXML
    private WebView web;
    
    private String urli;
    
    private ArrayList<String> edellispino = new ArrayList();
    private ArrayList<String> seuraavapino = new ArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void gotoSite(ActionEvent event) throws InterruptedException {
        
        if (urlField.getText().equals("index.html")) {
            web.getEngine().load(getClass().getResource("index.html").toExternalForm());
            return;
        }
        
        if (!urlField.getText().startsWith("http://")) {
            urli = "http://" + urlField.getText();
        } else {
            urli = urlField.getText();
        }
        
        web.getEngine().load(urli);
    }

    @FXML
    private void update() {
        urli = web.getEngine().getLocation();
        web.getEngine().load(urli);
    }

    @FXML
    private void resetUrlBar(MouseEvent event) {
        if (urli.equals(web.getEngine().getLocation())) {
            return;
        }
        edellispino.add(urli);
        urli = web.getEngine().getLocation();
        urlField.setText(urli);
    }

    @FXML
    private void huuda(ActionEvent event) {
        web.getEngine().executeScript("document.shoutOut()");
    }

    @FXML
    private void alusta(ActionEvent event) {
        web.getEngine().executeScript("initialize()");
    }

    @FXML
    private void edellinen(ActionEvent event) {
        if (edellispino.isEmpty())
            return;
        seuraavapino.add(urli);
        urli = edellispino.get(edellispino.size()-1);
        edellispino.remove(edellispino.size()-1);
        web.getEngine().load(urli);
        urlField.setText(urli);
    }

    @FXML
    private void seuraava(ActionEvent event) {
        if (seuraavapino.isEmpty())
            return;
        edellispino.add(urli);
        urli = seuraavapino.get(seuraavapino.size()-1);
        seuraavapino.remove(seuraavapino.size()-1);
        web.getEngine().load(urli);
        urlField.setText(urli);
    }
}
