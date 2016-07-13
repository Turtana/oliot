/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karttapiirturi;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author k4873
 */
public class Viiva {
    double ax, bx, ay, by;
    Line l;
    
    public Viiva (Point a, Point b) {
        ax = a.getKeha().getCenterX();
        ay = a.getKeha().getCenterY();
        bx = b.getKeha().getCenterX();
        by = b.getKeha().getCenterY();
        
        l = new Line(ax, ay, bx, by);
        
        l.setStroke(Color.RED);
        l.setStrokeWidth(5);
    }
    
    public Line getIt () {
        return l;
    }
}
