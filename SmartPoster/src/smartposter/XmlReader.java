/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

import java.io.IOException;
import java.io.StringReader;
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
    
    private XmlReader () { }
    
    public static XmlReader getInstance () {
        if (xr == null) {
            xr = new XmlReader();
        }
        return xr;
    }
    
    public SmartPostList parsePostData(String data, SmartPostList g) {
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
            
            //System.out.println("Parsitaan SmartPost-dataa...");
            
            for (int i = 0; i < code.getLength(); i++) {
                g.addPost(new SmartPost(
                        code.item(i).getTextContent(), city.item(i).getTextContent(),
                        address.item(i).getTextContent(), availability.item(i).getTextContent(),
                        postoffice.item(i).getTextContent(), Double.parseDouble(lat.item(i).getTextContent()),
                        Double.parseDouble(lng.item(i).getTextContent())));
            }
            
            return g;
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(SmartPostList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
