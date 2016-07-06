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
    private final String code, city, address, availability, postoffice;
    private final double lat, lon;
    
    public SmartPost(String ucode, String ucity, String uaddress,
            String uavailability, String upostoffice, double ulat, double ulon) {
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
        return postoffice;
    }
    
    public String getAddress() {
        return (address + ", " + code + " " + city);
    }
    
    public String getInfo() {
        return (postoffice + " " + availability);
    }
    
}
