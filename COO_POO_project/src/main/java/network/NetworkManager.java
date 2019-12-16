package network;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.lang.String;

/*
 * format d'un message : String
 * 
 * ID<>broadcast/unicast<>IPsender<>Text
 * 
 * unicast => Text = message => relever le temps auquel on le reçoit
 * broadcast => Text = <IPsourcerequete> <requete> <answer>
 * 		ID == <1> | on veut se connecter => demande du tableau (?)
 * 		ID == <2> | connected 
 *      ID == <3> | update_pseudo 
 * 		ID == <4> | disconnected 
 * 
 * L'ID nous permet de differencier les differents messages possibles. 
 * 
 *
 */
 

public class NetworkManager implements Runnable 
{
    private boolean NetworkManagerActive = false;
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
	
	//Online and active Users 
	private HashMap <String,InetAddress> onlineUsers = new HashMap <String,InetAddress>();
    
    private Thread receiver;
    
    private ArrayList<String> global_buffer;
    
    public NetworkManager() throws SocketException, UnknownHostException
    {
		this.NetworkManagerActive = true;
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
    
	
    
	public void run() {
		//debugging
		System.out.println("Network_Manager running");
		
		try {
			while(this.NetworkManagerActive) {
			//Je pensais mettre ici socket.receive puis analyser le paquet en mettant 
			//	if (packets==<1> then
			// Envoyer moi le tableau
			//traitement
			// if packets == <3> then
			// disconection........... Utilisation du HashMap <String,InetAddress> onlineUsers
				
				String message = global_buffer.get(0);
				
				treatMessageReceived(message);
			}
			
		}catch(Exception e) {
			System.out.println("NetworkManager Error");
			e.printStackTrace();
		}
		
		while(true) {
			//System.out.println("Network_Manager while");
		}
		
	}
	
	//a mettre dans une thread treatment qui est demarrée par mainapplication
	public void treatMessageReceived(String message)
	{
		String[] dataPacket = message.split("<>");
		
		String idPacketReceived = dataPacket[0];
		String naturePacketReceived = dataPacket[1];
		String ipSenderPacketReceived = dataPacket[2];
		String textPacketReceived = dataPacket[3];
		
		if(idPacketReceived.equals("1"))
		{
			//sendListActiveUser(ipSenderPacketReceived);
		}
		else if(idPacketReceived.equals("2"))
		{
			//addActiveUser(ipSenderPacketReceived, textPacketReceived);
		}
		else if(idPacketReceived.equals("3"))
		{
			//modifyActiveUser(ipSenderPacketReceived, textPacketReceived);
		}
		else if(idPacketReceived.equals("4"))
		{
			//deleteActiveUser(ipSenderPacketReceived, textPacketReceived);
		}
	}
	
	
	//To have all onlineUsers
	public Set<String> getOnlineUsers() {

   		synchronized(onlineUsers) {
			Set<String> s = new HashSet<String>(onlineUsers.keySet());
   			return s;
   		}
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
    
    private void broadcast(String message, InetAddress broadcastAddress) throws IOException 
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
    
	//A FAIRE. 
	
	public int broadcastGetListActiveUser() {
		
		//send broadcast <1>
		return 0;
	}
	
	public int broadcastConnected() {
		return 0;
	}
	 
	public int broadcastUpdatePseudo() {
		return 0;
	}

	public int broadcastDisconnected() {
		return 0;
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
		this.NetworkManagerActive = false;
        outDgramSocket.close();
        inDgramSocket.close();
		System.out.println("Server successfully closed");
    }
}