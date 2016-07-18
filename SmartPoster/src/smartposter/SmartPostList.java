/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author k4873
 */
public class SmartPostList {
    
    private static SmartPostList spl = null;
    private static String urli;
    private static Connection con;
    private static SmartPost lahetys;
    private static ArrayList<SmartPost> smartlista;
    
    private SmartPostList () {
        urli = "jdbc:sqlite:timotei.db";
        smartlista = new ArrayList();
    }
    
    public static SmartPostList getInstance() {
        if (spl == null) {
            spl = new SmartPostList();
        }
        return spl;
    }
    
    public static SmartPost getPost (int i) { // Kaivaa tietokannasta SmartPostin, jonka id on i
        lahetys = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SmartPostList.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs;
        
        try {
            con = DriverManager.getConnection(urli);
            PreparedStatement viuh = con.prepareStatement("SELECT "
                    + "smartpost.smartid, address.code, address.city, address.street, "
                    + "smartpost.availability, smartpost.postoffice, coords.lat, coords.lon "
                    + "FROM (smartpost INNER JOIN coords ON smartpost.coordid = coords.coordid) "
                    + "INNER JOIN address ON smartpost.addressid = address.addressid"
                    + "WHERE smartpost.smartid = ?;");
            viuh.setInt(1, i);
            
            rs = viuh.executeQuery();
            if (rs.next()) {
                lahetys = new SmartPost(rs.getInt("smartid"), rs.getString("code"),
                    rs.getString("city"), rs.getString("street"), rs.getString("availability"),
                    rs.getString("postoffice"), rs.getDouble("lat"), rs.getDouble("lon"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(XmlReader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Auts!");
            return lahetys;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmartPostList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lahetys;
    }
    
    public static ArrayList<SmartPost> getAllPosts () {
        smartlista.clear();
        
        try {
            //Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(urli);
            PreparedStatement kaik = con.prepareStatement("SELECT "
                    + "smartpost.smartid, address.code, address.city, address.street, "
                    + "smartpost.availability, smartpost.postoffice, coords.lat, coords.lon "
                    + "FROM (smartpost INNER JOIN coords ON smartpost.coordid = coords.coordid) "
                    + "INNER JOIN address ON smartpost.coordid = address.addressid;");
            ResultSet rs = kaik.executeQuery();
            while (rs.next()) {
                smartlista.add(new SmartPost(rs.getInt("smartid"), rs.getString("code"),
                        rs.getString("city"), rs.getString("street"), rs.getString("availability"),
                        rs.getString("postoffice"), rs.getDouble("lat"), rs.getDouble("lon")));
            }
        } catch (/*ClassNotFoundException |*/ SQLException ex) {
            Logger.getLogger(SmartPostList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmartPostList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("Palautetaan lista");
        return smartlista;
    }
    
    public void addPost (SmartPost s) throws SQLException/*, ClassNotFoundException*/ { // Lisää tietokantaan SmartPostin s
        //Class.forName("org.sqlite.JDBC");
        //System.out.println("Lisätään " + s.getId());
        try {
            con = DriverManager.getConnection(urli);
            PreparedStatement varm = con.prepareStatement("SELECT smartid FROM smartpost WHERE smartid = ?;");
            varm.setInt(1, s.getId());
            ResultSet r = varm.executeQuery();
            if (r.next()) {
                System.out.println(r.getString("smartid") + " on jo taulussa");
                con.close();
                return;
            }
            PreparedStatement viuh = con.prepareStatement("INSERT INTO "
                    + "smartpost(smartid,availability,postoffice, addressid, coordid) "
                    + "VALUES (?, ?, ?, ?, ?);");
            // id, p-numero, kaupunki, osoite, aukiolo, toimisto, lat, lon

            viuh.setInt(1, s.getId()); // Pääavain
            viuh.setString(2, s.getAva());
            viuh.setString(3, s.getPost());
            viuh.setInt(4, s.getId());
            viuh.setInt(5, s.getId());
            viuh.executeUpdate();
            
            viuh = con.prepareStatement("INSERT INTO "
                    + "coords(coordid, lat, lon) "
                    + "VALUES (?, ?, ?)");
            viuh.setInt(1, s.getId());
            viuh.setDouble(2, s.getLat());
            viuh.setDouble(3, s.getLon());
            viuh.executeUpdate();
            
            viuh = con.prepareStatement("INSERT INTO "
                    + "address(addressid, code, city, street) "
                    + "VALUES (?, ?, ?, ?)");
            viuh.setInt(1, s.getId());
            viuh.setString(2, s.getCode());
            viuh.setString(3, s.getCity());
            viuh.setString(4, s.getStreet());
            viuh.executeUpdate();
            
            System.out.println(s.getCity() + " lisätty");
        } catch (SQLException ex) {
            Logger.getLogger(XmlReader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Auts!");
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static void removePost(SmartPost s) { // Poistaa SmartPost s:n tietokannasta

        //Class.forName("org.sqlite.JDBC");
 
        try {
            con = DriverManager.getConnection(urli);
            PreparedStatement pois = con.prepareStatement("DELETE FROM smartpost WHERE smartid = ?;");
            pois.setInt(1, s.getId());
            pois.executeUpdate();
            
            pois = con.prepareStatement("DELETE FROM address WHERE addressid = ?;");
            pois.setInt(1, s.getId());
            pois.executeUpdate();
            
            pois = con.prepareStatement("DELETE FROM coords WHERE coordid = ?;");
            pois.setInt(1, s.getId());
            pois.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SmartPostList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SmartPostList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void nukeFlash () {

        //Class.forName("org.sqlite.JDBC");

        try {
            con = DriverManager.getConnection(urli);
            PreparedStatement pois = con.prepareStatement("DELETE FROM smartpost;");
            pois.executeUpdate();
            pois = con.prepareStatement("DELETE FROM address;");
            pois.executeUpdate();
            pois = con.prepareStatement("DELETE FROM coords;");
            pois.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SmartPostList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SmartPostList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Duck and coveeeeeer!");
    }
}
