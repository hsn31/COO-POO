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
 * 
 * broadcast => 
 * 		ID == 1  | on veut se connecter => demande qui est connecté (?) : Text = ""
 * 		ID == 2  | connected : Text = pseudo
 *      ID == 3  | update_pseudo : Text = pseudo
 * 		ID == 4  | disconnected : Text = pseudo
 * 
 * 
 * unicast => 
 *  	ID == 5  | message of chat : Text = message => relever le temps auquel on le reçoit
 * 		ID == 11 | answer to 1 => (IPaddress , pseudonyme) : Text = <IPsourcerequete> <requete> <answer>
 * 
 * 
 * 
 * L'ID nous permet de differencier les differents messages possibles. 
 * 
 *
 */
 
//Network manager n'a plus besoin d'etre thread => processing thread liée a main application
public class NetworkManager //implements Runnable 
{
    private boolean NetworkManagerActive = false;
	private InetAddress local_address;
	private String stringLocalAdress = local_address.toString();
	
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
	
	//ListOfMessages recu par la thread. 
	public ArrayList<String> getListOfMessages()
	{
		return global_buffer;
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
    
    public void sendBroadcast(String message) throws IOException
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
    
    public void unicastSendChatMessage(String wantedMessage, String distantAddress)
    {
    	InetAddress distantAddress2 = distantAddress; // type mismatch String InetAddress
		sendMessage(wantedMessage, distantAddress2, inPort);
    }
    
    public void sendMessage(String message, InetAddress distantAddress, int distantPort) throws IOException
    {
        byte[] buffer = message.getBytes();
        
        outPacket = new DatagramPacket(buffer, buffer.length, distantAddress, distantPort);
        outDgramSocket.send(outPacket);
    }
    
    
	//Moi, Utilisateur Local, j'envoie les Broadcasts suivants pour avoir des infos ou avertir. 

	public int broadcastGetActiveUser() {
		
		//send broadcast <1>
		try {
			sendBroadcast("1<>broadcast<>"+stringLocalAdress+"<>_");
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		
	}
	
	public int broadcastConnected(String pseudo) {
		//send broadcast <2>
		try {
			sendBroadcast("2<>broadcast<>"+stringLocalAdress+"<>"+pseudo);
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}
	 
	public int broadcastUpdatePseudo(String pseudo) {
		//send broadcast <3>
		try {
			sendBroadcast("3<>broadcast<>"+stringLocalAdress+"<>"+pseudo);
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}

	public int broadcastDisconnected() {
		//send broadcast <4>
		try {
			sendBroadcast("4<>broadcast<>"+stringLocalAdress+"<>_");
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
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
