/*
 * Author name: Wai Yan WONG
 * Student ID: 892083
 * User Name: waiw7
 * Sep 6th, 2018
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

	public static void main(String[] args) {

		ServerSocket listeningSocket = null;
		try {
			//check if there are two parameters
			if (args.length == 2) {
				try {
					//check if the first parameter is an integer
					Integer.parseInt(args[0]);
				}catch(Exception e) {
					System.out.println("Please input the correct port number");
					System.exit(0);
				}
				
				//create an object of dictionary, read words and their meanings
				ClientManager.CreatDic(args[1]);
				
				// Create a server socket listening on the given port
				listeningSocket = new ServerSocket(Integer.parseInt(args[0]));
				System.out.printf(
						Thread.currentThread().getName() + " - Server listening on port %d for a connection\n",
						Integer.parseInt(args[0]));
			} else {
				System.out.println("Please input the correct format");
				System.exit(0);
			}
		}catch (Exception e) {
			System.out.println("Failed to create the socket, because the port is in used.");
			System.exit(0);
		}
		
		try {
			//Keep track of the number of clients
			int clientNum = 0;
			
			//Listen for incoming connections for ever
			while (true) {
				
				//Accept an incoming client connection request
				Socket clientSocket = listeningSocket.accept();
				System.out.println(Thread.currentThread().getName() 
						+ " - Client conection accepted");
				clientNum++;

				//Create one thread per client connection, each thread will be
				//responsible for listening for messages from the client
				//and then 'handing' them to the client manager (coordinating singleton)
				//to process them
				ClientConnection clientConnection = new ClientConnection(clientSocket, clientNum);
				clientConnection.setName("Thread" + clientNum);
				clientConnection.start();
				
				//Register the new client connection with the client manager
				ClientManager.getInstance().clientConnected(clientConnection);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		} finally {
			if(listeningSocket != null) {
				try {
					listeningSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
