package network;

import java.net.SocketException;
import java.net.UnknownHostException;

public class MainApplication 
{
	private NetworkManager local_manager;
	private Account local_account;
	
	public MainApplication() throws SocketException, UnknownHostException
	{
		local_account = new Account();
		local_manager = new NetworkManager();
	}
	
	public static void main() throws SocketException, UnknownHostException
	{
		new MainApplication();
	}
}
