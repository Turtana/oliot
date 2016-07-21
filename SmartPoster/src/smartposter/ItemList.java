

/**
 *
 * @author k4873
 */
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

/**
 *
 * @author k4873
 */
public class ItemList {
    
    private static ItemList il = null;
    private static String urli;
    private static Connection con;
    private static Item lahetys;
    private static ArrayList<Item> itemlista = new ArrayList();
    
    private ItemList () {
        
    }
    
    public static ItemList getInstance() {
        if (il == null) {
            il = new ItemList();
        }
        return il;
    }
    
    public static Item getItem (int i) { // Kaivaa tietokannasta esineen, jonka itemid on i
        urli = "jdbc:sqlite:timotei.db";
        lahetys = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs;
        
        try {
            con = DriverManager.getConnection(urli);
            PreparedStatement viuh = con.prepareStatement("SELECT * FROM item WHERE smartid = ?;");
            viuh.setInt(1, i);
            
            rs = viuh.executeQuery();
            if (rs.next()) {
                lahetys = new Item(rs.getInt("itemid"), rs.getString("description"),
                    rs.getInt("size"), rs.getBoolean("breakable"), rs.getString("name"));
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
                    Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return lahetys;
    }
    
    public static int getFreeId() { // Hakee vapaana olevan id:n uusien esineiden tekemiseen
        urli = "jdbc:sqlite:timotei.db";
        int i = 0;
        try {
            //Class.forName("org.sqlite3.JDBC");
            con = DriverManager.getConnection(urli);
            PreparedStatement free;
            ResultSet rs;
            
            free = con.prepareStatement("SELECT itemid FROM item;");
            
            rs = free.executeQuery();
            
            while (rs.next()) {
                i = rs.getInt("itemid");
            }
            i++;
            
            System.out.println("Varataan id " + i);
        } catch (/*ClassNotFoundException | */SQLException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    
    public static ArrayList<Item> getAllItems () {
        urli = "jdbc:sqlite:timotei.db";
        itemlista.clear();
        
        try {
            //Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(urli);
            PreparedStatement kaik = con.prepareStatement("SELECT * FROM item;");
            ResultSet rs = kaik.executeQuery();
            while (rs.next()) {
                itemlista.add(new Item(rs.getInt("itemid"), rs.getString("description"),
                    rs.getInt("size"), rs.getBoolean("breakable"), rs.getString("name")));
                
            }
        } catch (/*ClassNotFoundException | */SQLException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return itemlista;
    }
    
    public static void addItem (Item s) { // Lisää tietokantaan Itemin s
        String[] kuvauscrop;
        kuvauscrop = s.kuvaile().split("\n"); // Rujo ratkaisu. Menköön.
        urli = "jdbc:sqlite:timotei.db";
        try {
            con = DriverManager.getConnection(urli);
            PreparedStatement varm = con.prepareStatement("SELECT itemid FROM item WHERE itemid = ?;");
            varm.setInt(1, s.getId());
            ResultSet r = varm.executeQuery();
            if (r.next()) { // getFreeId-funktiosta johtuen näin ei PITÄISI koskaan käydä. Varmuus paras.
                System.out.println("Esine numerolla " + s.getId() + " on jo taulussa");
                con.close();
                return;
            }
            PreparedStatement viuh = con.prepareStatement("INSERT INTO "
                    + "item(itemid,name,size,breakable,description) "
                    + "VALUES (?, ?, ?, ?, ?);");

            viuh.setInt(1, s.getId()); // Pääavain
            viuh.setString(2, s.toString());
            viuh.setInt(3, s.getKoko());
            viuh.setBoolean(4, s.isBreakable());
            viuh.setString(5, kuvauscrop[0]);
            viuh.executeUpdate();
            
            System.out.println(s.toString() + " OK");
        } catch (SQLException ex) {
            Logger.getLogger(XmlReader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Auts!");
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static void removeItem(Item s) { // Poistaa esine s:n tietokannasta
        urli = "jdbc:sqlite:timotei.db";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(urli);
            PreparedStatement pois = con.prepareStatement("DELETE FROM item WHERE itemid = ?;");
            pois.setInt(1, s.getId());
            pois.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void nukeFlash () {
        urli = "jdbc:sqlite:timotei.db";
        try {
            con = DriverManager.getConnection(urli);
            PreparedStatement pois = con.prepareStatement("DELETE FROM item;"); // BOOOOOM
            pois.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Duck and coveeeeee- BOOOOOOOOMMMM");
    }
}

