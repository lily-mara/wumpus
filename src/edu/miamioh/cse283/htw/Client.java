package edu.miamioh.cse283.htw;

import java.net.*;

public class Client {
	// networking code
	//
	// connecting
	// handoff
	// send actions to server
	// receive messages from server

	public static void main(String[] args) {
		Client c = new Client();
		c.run(args);
	}

	/**
	 * plays the game
	 * 
	 * @param args
	 *            holds the IP address and port number for a CaveSystemServer
	 */
	public void run(String[] args) {

	}

	/**
	 * connects to a given server
	 * 
	 * @param address
	 *            the address of the server
	 * @param port
	 *            the port number of the server
	 */
	public void connect(InetAddress address, int port) {

	}

	/**
	 * transfers this client to a new server
	 * 
	 * @param address
	 * @param port
	 */
	public void handoff(InetAddress address, int port) {

	}
}
