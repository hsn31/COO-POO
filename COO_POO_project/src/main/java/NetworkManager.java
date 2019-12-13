import java.io.IOException;
import java.net.*;
import java.util.*;


/*
 * format d'un message : String
 * 
 * <broadcast/unicast> <IPsender> <Text>
 * 
 * unicast => Text = message => relever le temps auquel on le reçoit
 * broadcast => Text = <IPsourcerequete> <requete> <answer>
 * 		| connected
 * 		| pseudo utilisé ?
 * 		| disconnected
 * 
 */

public class NetworkManager 
{
    
	private InetAddress local_address;
	
	//attributes to receive messages on a chat conversation (port fixed)
    private DatagramSocket inDgramSocket; 
    private DatagramPacket inPacket;
    private byte[] inBuffer;
    private int inPort;
    
    //attributes TO SEND from any port x, a message or a broadcast message
    private DatagramSocket outDgramSocket; 
    private DatagramPacket outPacket;
    
    //attributes to receive the answer in broadcast (port x)
    private DatagramPacket inPacket_broadcast;
    private byte[] inBuffer_broadcast;
    
    private Thread receiver;
    
    private ArrayList<String> global_buffer;
    
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
    
    public void receiveMessage() throws IOException
    {
    	inDgramSocket.receive(inPacket);
        
        String message = new String(inPacket.getData(), 0, inPacket.getLength());
        
        save_message(message);
    }
    
    public void save_message(String m)
    {
    	global_buffer.add(m);
    }
    
    
    public void sendFirstBroadcast(String message) throws IOException
    {
        List<InetAddress> ListInetAddresses = listAllBroadcastAddresses();
        
        outDgramSocket.setBroadcast(true);
        
        // test ok : listAllBroadcastAddresses().get(0).toString()
        broadcast(message, ListInetAddresses.get(0));
        
        outDgramSocket.receive(inPacket_broadcast);
        
        outDgramSocket.setBroadcast(false);
    }
    
    public void sendClassicBroadcast(String message) throws IOException
    {
        List<InetAddress> ListInetAddresses = listAllBroadcastAddresses();
        
        outDgramSocket.setBroadcast(true);
        
        // test ok : listAllBroadcastAddresses().get(0).toString()
        broadcast(message, ListInetAddresses.get(0));
        
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
