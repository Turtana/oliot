//sfsfgsdgdgsg

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainclass;

import java.io.IOException;

/**
 *
 * @author k4873
 
 Elias Vähäjylkkä
 1.6.2016
 Lähteet: http://stackoverflow.com/questions/4473256/reading-text-files-in-a-zip-archive
 
 
 */
public class Mainclass {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    
   
    public static void main(String[] args) throws IOException {
        ReadAndWriteIO io = new ReadAndWriteIO();
        io.readFile("input.txt");
        
        
    }
    
}
