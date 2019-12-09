import java.io.IOException;
import java.net.*;
import java.util.*;


public class NetworkManager {
    
    private User_test localUser;
    private DatagramSocket dgramSocket;
    
    private byte[] inBuffer;
    private DatagramPacket inPacket;
    private int inPort;
    
    private byte[] outBuffer;
    private DatagramPacket outPacket;
    
    private InetAddress distantAddress;
    private int applicationPort;
    
    NetworkManager(int id) throws SocketException, UnknownHostException
    {
        localUser = new User_test(id);
        applicationPort = 2000;
        dgramSocket = new DatagramSocket(applicationPort);
        
        inBuffer = new byte[256];
        
        
        inPacket = new DatagramPacket(inBuffer, inBuffer.length);
        
        outBuffer = new byte[256];
        
        distantAddress = InetAddress.getLocalHost();
    }
    

    
    public void receiveMessage() throws IOException
    {
        dgramSocket.receive(inPacket);
        
        distantAddress = inPacket.getAddress();
        
        String message = new String(inPacket.getData(), 0, inPacket.getLength());
        
        localUser.registerMessage(message);
        
        System.out.println("you have received this message: " + message + " on the user number " + localUser.getId());
    }
    
    
    public void sendBroadcast(String message) throws IOException
    {
        List<InetAddress> ListInetAddresses = listAllBroadcastAddresses();
        
        // test ok : listAllBroadcastAddresses().get(0).toString()
        broadcast(message, ListInetAddresses.get(0));
    }
    
    public void sendMessage(String message) throws IOException
    {
        localUser.registerMessage(message);
        
        byte[] buffer = message.getBytes();
        
        outPacket = new DatagramPacket(buffer, buffer.length, distantAddress, applicationPort);
        dgramSocket.send(outPacket);
    }
    
 

    public void broadcast(String message, InetAddress broadcastAddress) throws IOException 
    {
        dgramSocket.setBroadcast(true);
 
        byte[] buffer = message.getBytes();
 
        outPacket = new DatagramPacket(buffer, buffer.length, broadcastAddress, applicationPort);
        dgramSocket.send(outPacket);
    }

    
    public List<InetAddress> listAllBroadcastAddresses() throws SocketException 
    {
        List<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) 
        {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            networkInterface.getInterfaceAddresses().stream() 
              .map(a -> a.getBroadcast())
              .filter(Objects::nonNull)
              .forEach(broadcastList::add);
        }
        return broadcastList;
    }
    
    
    public void closeServer()
    {
        dgramSocket.close();
    }
}
