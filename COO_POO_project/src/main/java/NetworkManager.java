import java.io.IOException;
import java.net.*;
import java.util.*;


public class NetworkManager {
    
	private InetAddress local_address;
	
	//attributes to receive messages on a chat converssation (port fixed)
    private DatagramSocket inDgramSocket; 
    private DatagramPacket inPacket;
    private byte[] inBuffer;
    private int inPort;
    
    //attributes to send from anyport x, a message or a broadcast message
    private DatagramSocket outDgramSocket; 
    private DatagramPacket outPacket;
    
    //attributes to receive the answer in broadcast (port x)
    private DatagramPacket inPacket_broadcast;
    private byte[] inBuffer_broadcast;
    
    private Thread receiver;
    
    private global_buffer ...;
    
    public NetworkManager() throws SocketException, UnknownHostException
    {
    	local_address = InetAddress.getLocalHost();
    	
    	inPort = 2832;
    	inDgramSocket = new DatagramSocket(inPort);

        inBuffer = new byte[256];
        inPacket = new DatagramPacket(inBuffer, inBuffer.length);
        
        inBuffer_broadcast = new byte[256];
        inPacket_broadcast = new DatagramPacket(inBuffer_broadcast, inBuffer_broadcast.length);
        
        receiver = new Thread(new ReceiverThread(this));
		receiver.start();
    }
    
    public int get_inPort()
    {
    	return inPort;
    }
    
    public InetAddress get_local_address()
    {
    	return local_address;
    }
    
    public String receiveMessage() throws IOException
    {
    	inDgramSocket.receive(inPacket);
        
        InetAddress distantAddress = inPacket.getAddress();
        
        int distantPort = inPacket.getPort();
        
        String message = new String(inPacket.getData(), 0, inPacket.getLength());
        
        return message;
    }
    
    
    public void sendBroadcast(String message) throws IOException
    {
        List<InetAddress> ListInetAddresses = listAllBroadcastAddresses();
        
        outDgramSocket.setBroadcast(true);
        
        // test ok : listAllBroadcastAddresses().get(0).toString()
        broadcast(message, ListInetAddresses.get(0));
        
        outDgramSocket.receive(inPacket_broadcast);
        
        
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
        outDgramSocket.close();
        inDgramSocket.close();
    }
}
