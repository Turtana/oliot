/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karttapiirturi;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author k4873
 */
public class FXMLDocumentController implements Initializable {
    
    private Point a, alku = null, loppu = null;
    private ShapeHandler sh = ShapeHandler.getInstance();
    private Viiva v = null, deleteme = null;
    
    
    @FXML
    private AnchorPane alusta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }    

    @FXML
    private void piirraPiste(MouseEvent event) {
        for (Point point : sh.getPointList()) {
            if (Math.abs(point.getKeha().getCenterX() - event.getSceneX()) < 20 && Math.abs(point.getKeha().getCenterY() - event.getSceneY()) < 20) { // Hui
                System.out.println("Afrikan Tähteä etsitään...");
                if (alku == null) {
                    alku = point;
                } else if (loppu == null) {
                    deleteme = v;
                    loppu = point;
                    v = new Viiva(alku, loppu);
                    alusta.getChildren().add(v.getIt());
                    sh.addLine(v.getIt());
                    //alusta.getChildren().remove(deleteme.getIt());
                    //sh.getLineList().remove(deleteme.getIt());
                } else {
                    deleteme = v;
                    alku = loppu;
                    loppu = point;
                    v = new Viiva(alku, loppu);
                    alusta.getChildren().add(v.getIt());
                    sh.addLine(v.getIt());
                    //alusta.getChildren().remove(deleteme.getIt());
                    //sh.getLineList().remove(deleteme.getIt());
                }
                
                return;
            }
        }
        alku = null; loppu = null;
        a = new Point(event.getSceneX(), event.getSceneY());
        alusta.getChildren().add(a.getKeha());
        sh.addPoint(a);
    }


    
}
