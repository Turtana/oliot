/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author k4873
 */
public class XmlReader {
   
    static private XmlReader xr = null;
    
    private Document doc; 
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private SmartPostList slist = SmartPostList.getInstance();

    Connection con = null;
    
    private XmlReader () throws SQLException { }
    
    public static XmlReader getInstance () {
        if (xr == null) {
            try {
                xr = new XmlReader();
            } catch (SQLException ex) {
                Logger.getLogger(XmlReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return xr;
    }
    
    public void haeAutomaatit() throws ClassNotFoundException {
    try {
        URL purli = new URL("http://smartpost.ee/fi_apt.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(purli.openStream()));

        String rivi, sisalto = "";

        while ((rivi = br.readLine()) != null) {
            sisalto += rivi + "\n";
        }
        br.close();

        try {
            slist = parsePostData(sisalto, slist);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        } catch (MalformedURLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Jokin taisi mennä pieleen URL:n kanssa.");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Hupsista keikkaa! Bufferilukija ei toimi!");
        }
    }
    
    
    
    public SmartPostList parsePostData(String data, SmartPostList g) throws SQLException, ClassNotFoundException {
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(new StringReader(data)));
            doc.getDocumentElement().normalize();
            
            NodeList code = doc.getElementsByTagName("code");
            NodeList city = doc.getElementsByTagName("city");
            NodeList address = doc.getElementsByTagName("address");
            NodeList availability = doc.getElementsByTagName("availability");
            NodeList postoffice = doc.getElementsByTagName("postoffice");
            NodeList lat = doc.getElementsByTagName("lat");
            NodeList lng = doc.getElementsByTagName("lng");
            
            
            for (int i = 0; i < code.getLength(); i++) { // Laittaa SmartPostListiin
                g.addPost(new SmartPost(i, 
                        code.item(i).getTextContent(), city.item(i).getTextContent(),
                        address.item(i).getTextContent(), availability.item(i).getTextContent(),
                        postoffice.item(i).getTextContent(), Double.parseDouble(lat.item(i).getTextContent()),
                        Double.parseDouble(lng.item(i).getTextContent())));
            }
            
            /* g.addPost(new SmartPost(500, "00001", "PRETORIA", // Pitempien matkojen testailuun
                        "1 Jean Ave", "dunno",
                        "Yhtiön pääkonttori, Etelä-Afrikka", 36.181872, 28.209372)); */
            
            return g;
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(SmartPostList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
