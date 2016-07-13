/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karttapiirturi;

import java.util.ArrayList;
import javafx.scene.shape.Line;

/**
 *
 * @author k4873
 */
public class ShapeHandler {
    private static ShapeHandler sh = null;
    private ArrayList<Point> pointlist = new ArrayList();
    private ArrayList<Line> linelist = new ArrayList();
    
    private ShapeHandler () {  }
    
    public static ShapeHandler getInstance () {
        if (sh == null) {
            sh = new ShapeHandler();
        }
        return sh;
    }
    
    public ArrayList<Point> getPointList () {
        return pointlist;
    }
    
    public void addPoint (Point p) {
        pointlist.add(p);
    }
    
    public void addLine (Line l) {
        linelist.add(l);
    }
    
    public ArrayList<Line> getLineList() {
        return linelist;
    }
}
