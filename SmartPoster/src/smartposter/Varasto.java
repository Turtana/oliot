/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import item.Item;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import packagepackage.Pakkaus;

/**
 *
 * @author k4873
 */
public class Varasto {
    private static Varasto varas = null;
    
    Connection con = null;
    
    private final ArrayList<Pakkaus> lista; // JA TÄMÄ SITTEN TIETOKANNOILLA!
    
    private static String errori; // Laitetaan nyt yleinen virheteksti vaikka tänne. 
    
    private Varasto () {
        lista = new ArrayList();
    }
    
    public static Varasto getInstance() {
        if (varas == null) {
            varas = new Varasto();
        }
        return varas;
    }
    
    public void addThing (Pakkaus p) {
        int laskuri = 0;
        try {
            con = DriverManager.getConnection("jdbc:sqlite:timotei.db");
            PreparedStatement check = con.prepareStatement("SELECT wareid FROM warehouse;");
            ResultSet z = check.executeQuery();
            while (z.next()) { // Etsitään seuraava vapaa id
                laskuri++;
            }
            PreparedStatement lisaa = con.prepareStatement("INSERT INTO warehouse(wareid, itemid, packageid) VALUES (?, ?, ?)");
            lisaa.setInt(1, laskuri);
            lisaa.setInt(2, p.avaa().getId());
            lisaa.setInt(3, p.getId());
            lisaa.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Varasto.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Varasto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<Pakkaus> getList() { // Kasaa  kaikki paketit yhdeksi ArrayListiksi
        String packnimi, desc, esinenimi;
        int packid, itemid, size;
        boolean breaks;
        
        lista.clear();
        try {
            con = DriverManager.getConnection("jdbc:sqlite:timotei.db");
            PreparedStatement hae = con.prepareStatement("SELECT "
                    + "package.codename, package.packageid, item.itemid, item.description, "
                    + "item.size, item.breakable, item.name FROM "
                    + "(warehouse INNER JOIN package ON warehouse.packageid = package.packageid) "
                    + "INNER JOIN item ON warehouse.itemid = item.itemid;");
            ResultSet fg = hae.executeQuery();
            
            while (fg.next()) {
                packnimi = fg.getString("codename");
                packid = fg.getInt("packageid");
                
                itemid = fg.getInt("itemid");
                desc = fg.getString("description");
                size = fg.getInt("size");
                breaks = fg.getBoolean("breakable");
                esinenimi = fg.getString("name");
                
                lista.add(new Pakkaus(new Item(itemid, desc, size, breaks, esinenimi), packnimi, packid));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Varasto.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Varasto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return lista;
    }
    
    // Virheilmoitukset alla
    
    public void setError (String s) {
        errori = s;
    }
    
    public String getError() {
        return errori;
    }
}
