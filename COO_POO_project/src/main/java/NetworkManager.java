/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author peraire
 */
import java.io.IOException;
import java.net.*;

public class NetworkManager {
    
    private User_test localUser;
    private DatagramSocket dgramSocket;
    private byte[] inBuffer;
    private byte[] outBuffer;
    private DatagramPacket inPacket;
    private DatagramPacket outPacket;
    
    private InetAddress distantAddress;
    private int distantPort;
    
    NetworkManager(int id, int port) throws SocketException
    {
        localUser = new User_test(id);
        dgramSocket = new DatagramSocket(port);
        inBuffer = new byte[256];
        outBuffer = new byte[256];
        inPacket = new DatagramPacket(inBuffer, inBuffer.length);
        //outPacket = new DatagramPacket(outBuffer, outBuffer.length);
    }
    
    public void sendMessage(String message)
    {
        localUser.registerMessage(message);
        
        outPacket = new DatagramPacket(message.getBytes(), message.length(), distantAddress, distantPort);
        dgramSocket.send(outPacket);
    }
    
    public void receiveMessage() throws IOException
    {
        dgramSocket.receive(inPacket);
        distantAddress = inPacket.getAddress();
        distantPort = inPacket.getPort();
        
        String message = new String(inPacket.getData(), 0, inPacket.getLength());
        
        localUser.registerMessage(message);
    }
    
    public void closeServer()
    {
        dgramSocket.close();
    }
}
