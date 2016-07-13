/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package packagepackage;

import item.Item;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author k4873
 */
public class Pakkaus {
    private Item esine = null; /* Esine laitetaan paketin sisään jo sitä
    luotaessa. Se käy järkeen, koska kyseessä on paketointi.*/
    private int nopeus, koko;
    private int maksimimatka;
    private final int packageid;
    private boolean turvallinen; // Eli onko rikkoutuva sisältö turvassa
    private String kuvaus, nimi;
    private Connection con;
    
    public Pakkaus (Item e, String ni, int i) {
        esine = e;
        nimi = ni;
        packageid = i;
    }
    
    @Override
    public String toString() {
        if (esine != null) {
            return esine + " (" + nimi + ")";
        } else {
            return nimi; // Tyhjä paketti – palauta vain pakettityypin nimi
        }
    }
    
    public String kuvaile () {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:timotei.db");
            PreparedStatement kaik = con.prepareStatement("SELECT desc FROM package WHERE packageid = ?;");
            kaik.setInt(1, packageid);
            ResultSet rs = kaik.executeQuery();
            kuvaus = rs.getString("desc");
        } catch (SQLException ex) {
            System.out.println("sql vaan kaatui");
            Logger.getLogger(Pakkaus.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Sulkeminen ei onnistunut. Vaaranpaikka!");
                Logger.getLogger(Pakkaus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return kuvaus;
    }
    
    public Item avaa () { // :)
        return esine;
    }
    
    public int getVauhti() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:timotei.db");
            PreparedStatement kaik = con.prepareStatement("SELECT speed FROM package WHERE packageid = ?;");
            kaik.setInt(1, packageid);
            ResultSet rs = kaik.executeQuery();
            nopeus = rs.getInt("speed");
        } catch (SQLException ex) {
            System.out.println("sql vaan kaatui");
            Logger.getLogger(Pakkaus.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Sulkeminen ei onnistunut. Vaaranpaikka!");
                Logger.getLogger(Pakkaus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nopeus;
    }
    
    public int getKoko () {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:timotei.db");
            PreparedStatement kaik = con.prepareStatement("SELECT size FROM package WHERE packageid = ?;");
            kaik.setInt(1, packageid);
            ResultSet rs = kaik.executeQuery();
            koko = rs.getInt("size");
        } catch (SQLException ex) {
            System.out.println("sql vaan kaatui");
            Logger.getLogger(Pakkaus.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Sulkeminen ei onnistunut. Vaaranpaikka!");
                Logger.getLogger(Pakkaus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return koko;
    }
    
    public boolean isSafe() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:timotei.db");
            PreparedStatement kaik = con.prepareStatement("SELECT safe FROM package WHERE packageid = ?;");
            kaik.setInt(1, packageid);
            ResultSet rs = kaik.executeQuery();
            turvallinen = rs.getBoolean("safe");
        } catch (SQLException ex) {
            System.out.println("sql vaan kaatui");
            Logger.getLogger(Pakkaus.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Sulkeminen ei onnistunut. Vaaranpaikka!");
                Logger.getLogger(Pakkaus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return turvallinen;
    }
    
    public int getMatka () {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:timotei.db");
            PreparedStatement kaik = con.prepareStatement("SELECT maxdistance FROM package WHERE packageid = ?;");
            kaik.setInt(1, packageid);
            ResultSet rs = kaik.executeQuery();
            maksimimatka = rs.getInt("maxdistance");
        } catch (SQLException ex) {
            System.out.println("sql vaan kaatui");
            Logger.getLogger(Pakkaus.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("Sulkeminen ei onnistunut. Vaaranpaikka!");
                Logger.getLogger(Pakkaus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return maksimimatka;
    }
    
    public int getId() {
        return packageid;
    }
}
