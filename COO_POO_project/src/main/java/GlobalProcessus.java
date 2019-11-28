
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author peraire
 */
public class GlobalProcessus {
    
    private static NetworkManager manager_1;
    private static NetworkManager manager_2;
    
    GlobalProcessus()
    {
        try 
        {
            manager_1 = new NetworkManager(11);
            manager_2 = new NetworkManager(12);
        } 
        catch (SocketException ex) 
        {
            Logger.getLogger(GlobalProcessus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static void main()
    {
        manager_1.sendMessage("abcd");
        manager_2.sendMessage("abcd");
    }
}
