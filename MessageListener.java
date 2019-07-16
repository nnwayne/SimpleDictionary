/*
 * Author name: Wai Yan WONG
 * Student ID: 892083
 * User Name: waiw7
 * Sep 6th, 2018
 */
package client;

import java.io.BufferedReader;
import java.net.SocketTimeoutException;
import java.net.SocketException;

public class MessageListener extends Thread {

	private BufferedReader reader;
	
	public MessageListener(BufferedReader reader) {
		this.reader = reader;
	}
	
	@Override
	public void run() {
		
		try {
			String msg = null;
			//Read messages from the server while the end of the stream is not reached
			while((msg = reader.readLine()) != null) {
				//Print the messages on the GUI
				GUI.display(msg);
			}
		}catch (SocketTimeoutException e) {
			System.out.println("Connection is interrupted.");
			System.exit(0);
		} catch (SocketException e) {
			System.out.println("Connection is interrupted.");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}