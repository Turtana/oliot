/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karttapiirturi;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author k4873
 */
public class Point {
    
    private String nimi;
    
    private Circle piste;
    
    public Point (double x, double y) {
        piste = new Circle(x, y, 15.0);
        piste.setFill(Color.RED);
        nimi = "piste"; 
    }
    
    public Circle getKeha () {
        return piste;
    }

}
