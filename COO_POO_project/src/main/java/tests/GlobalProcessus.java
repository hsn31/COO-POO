package tests;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import network.NetworkManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author peraire
 */

//Main-Class: <default package>.<GlobalProcessus>;

public class GlobalProcessus {
    
    private static NetworkManager manager;
    
    private static int iduser;
    
    GlobalProcessus() throws UnknownHostException
    {
 
        try 
        {
            manager = new NetworkManager();
        } 
        catch (SocketException ex) 
        {
            Logger.getLogger(GlobalProcessus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String [] args) throws IOException
    {

        try
        {
            iduser = Integer.parseInt(args[2]);

            System.out.println("le mode est: " + args[0]);
            System.out.println("le port est: " + args[1]);
            System.out.println("l'id user est: " + args[2]);

            GlobalProcessus gp = new GlobalProcessus();

            if(args[0].equals("send"))
            {
                System.out.println("Mode emission");
                manager.sendMessage(args[3], null, iduser);
            }
            else if(args[0].equals("receive"))
            {
                System.out.println("Mode reception");
                manager.receiveMessage();
            }

            manager.closeServer();
        }
        catch(Exception e)
        {
            manager.closeServer();
        }
        
        
    }
}
