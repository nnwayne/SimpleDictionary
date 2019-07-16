/*
 * Author name: Wai Yan WONG
 * Student ID: 892083
 * User Name: waiw7
 * Sep 6th, 2018
 */
package server;

import java.util.ArrayList;
import java.util.List;

//Singleton object in charge of processing messages received from
//clients and managing the server state
public class ClientManager {
	
	private static ClientManager instance;
	private static Dictionary dic;
	
	private List<ClientConnection> connectedClients;
	
	private ClientManager() {
		connectedClients = new ArrayList<ClientConnection>();
		
	}
	
	public static synchronized ClientManager getInstance() {
		if(instance == null) {
			instance = new ClientManager();
		}
		return instance;
	}
	public static void CreatDic(String dicName) {
		dic = new Dictionary(dicName);
		dic.readDic();
	}
	
	public synchronized void processMessage(String msg, int sendto) {
		// Process the message received and then
		// Send the feedback to the specific client
		String reString = null;
		for(ClientConnection clientConnection : connectedClients) {
			if(clientConnection.getClientNum() == sendto) {
				String[] doType = new String[2];
				doType = msg.split("_");
				
				if(doType[0].equals("search")) {
					reString = dic.search(doType[1]);
				}
				else if(doType[0].equals("add")) {
					reString = dic.add(doType[1]);
					dic.storeToFile();
				}
				else if(doType[0].equals("delete")) {
					reString = dic.delete(doType[1]);
					dic.storeToFile();
				}
			clientConnection.write(reString);
			}
		}
	}
	
	public synchronized void clientConnected(ClientConnection clientConnection) {
		connectedClients.add(clientConnection);
	}
	
	public synchronized void clientDisconnected(ClientConnection clientConnection) {
		connectedClients.remove(clientConnection);
	}

	public synchronized List<ClientConnection> getConnectedClients() {
		return connectedClients;
	}

}
