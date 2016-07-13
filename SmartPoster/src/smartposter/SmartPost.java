/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartposter;

/**
 *
 * @author k4873
 */
public class SmartPost {
    private final int id;
    private final String code, city, address, availability, postoffice;
    private final double lat, lon;
    
    public SmartPost(int i, String ucode, String ucity, String uaddress,
            String uavailability, String upostoffice, double ulat, double ulon) {
        id = i;
        code = ucode;
        city = ucity;
        address = uaddress;
        availability = uavailability;
        postoffice = upostoffice;
        lat = ulat;
        lon = ulon;
    }
    
    @Override
    public String toString() {
        String[] asd = postoffice.split(", ");
        return city + "\n  " + asd[1];
    }
    
    public int getId () {
        return id;
    }
    
    public String getCode () {
        return code;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getStreet() {
        return address;
    }
    
    public String getAva () {
        return availability;
    }
    
    public String getPost () {
        return postoffice;
    }
    
    public Double getLat () {
        return lat;
    }
    
    public Double getLon () {
        return lon;
    }
    
    public String getAddress() {
        return (address + ", " + code + " " + city);
    }
    
    public String getInfo() {
        return (postoffice + " " + availability);
    }
    
    public String getCoords () {
        return lat + " " + lon;
    }
    
}
