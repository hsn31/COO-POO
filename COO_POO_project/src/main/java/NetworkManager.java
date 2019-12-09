import java.io.IOException;
import java.net.*;
import java.util.*;


public class NetworkManager {
    
	private int local_id;
	private InetAddress local_address;
	
    private DatagramSocket inDgramSocket; 
    private DatagramPacket inPacket;
    private byte[] inBuffer;
    private int inPort;
    
    private DatagramSocket outDgramSocket; 
    private DatagramPacket outPacket;
    private byte[] outBuffer;
    
    public NetworkManager(int id) throws SocketException, UnknownHostException
    {
    	inPort = 2832;
    	inDgramSocket = new DatagramSocket(inPort);

        
        inBuffer = new byte[256];
        
        
        inPacket = new DatagramPacket(inBuffer, inBuffer.length);
        
        outBuffer = new byte[256];
        
        InetAddress distantAddress = InetAddress.getLocalHost();
    }
    
    public int get_inPort()
    {
    	return inPort;
    }
    
    public InetAddress get_local_address()
    {
    	return local_address;
    }
    
    public void receiveMessage() throws IOException
    {
    	inDgramSocket.receive(inPacket);
        
        InetAddress distantAddress = inPacket.getAddress();
        
        String message = new String(inPacket.getData(), 0, inPacket.getLength());
    }
    
    
    public void sendBroadcast(String message) throws IOException
    {
        List<InetAddress> ListInetAddresses = listAllBroadcastAddresses();
        
        outDgramSocket.setBroadcast(true);
        
        // test ok : listAllBroadcastAddresses().get(0).toString()
        broadcast(message, ListInetAddresses.get(0));
        
        outDgramSocket.receive();
        
        
        outDgramSocket.setBroadcast(false);
    }
    
    public void broadcast(String message, InetAddress broadcastAddress) throws IOException 
    {
        byte[] buffer = message.getBytes();
 
        outPacket = new DatagramPacket(buffer, buffer.length, broadcastAddress, inPort);
        outDgramSocket.send(outPacket);
    }
    
    public void sendMessage(String message, InetAddress distantAddress, int distantPort) throws IOException
    {
        byte[] buffer = message.getBytes();
        
        outPacket = new DatagramPacket(buffer, buffer.length, distantAddress, distantPort);
        outDgramSocket.send(outPacket);
    }
    
    
    
    
    //------ Finished functions !!! ---------------------------------------------------------------------------------------
    
    
    private List<InetAddress> listAllBroadcastAddresses() throws SocketException 
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
