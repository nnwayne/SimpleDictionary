/*
 * Author name: Wai Yan WONG
 * Student ID: 892083
 * User Name: waiw7
 * Sep 6th, 2018
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class client {
	
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public client(String ServerAddress, int ServerPort) {
		try {
			socket = new Socket(ServerAddress, ServerPort);
			System.out.println("Connection with server established");

			//Get the input/output streams for reading/writing data from/to the socket
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

			//Launch a new thread in charge of listening for any messages
			//that arrive through the socket's input stream (any data sent by the server)
			MessageListener ml = new MessageListener(reader);
			ml.start();
			
		} catch (Exception e) {
			System.out.println("Cannot find the server.");
			System.exit(0);
		} 
	}
	public void closeSocket() {
		try {
			socket.close();
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void search(String word) {
		try {
		writer.write("search_" + word + "\n");
		writer.flush();		
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void add(String word) {
		try {
		writer.write("add_" + word + "\n");
		writer.flush();		
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void delete(String word) {
		try {
		writer.write("delete_" + word + "\n");
		writer.flush();		
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
