package edu.miamioh.cse283.htw;

import java.io.*;
import java.net.*;

public class Client {
	// networking code
	//
	// connecting
	// handoff
	// send actions to server
	// receive messages from server

	protected Socket server;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		Client c = new Client();
		c.run(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
	}

	/**
	 * plays the game
	 * 
	 * @param server
	 *            IP address for a {@link CaveSystemServer}
	 * 
	 * @param port
	 *            Port # for a {@link CaveSystemServer}
	 * @throws IOException
	 */
	public void run(InetAddress serverAddress, int port) throws IOException {
		server = new Socket(serverAddress, port);
		SocketCommunicator serverComm = new SocketCommunicator(server);

		InetAddress caveAddr = InetAddress.getByName(serverComm.receive());
		int cavePort = Integer.parseInt(serverComm.receive());

		Socket cave = new Socket(caveAddr, cavePort);
		SocketCommunicator caveComm = new SocketCommunicator(cave);

		System.out.println(caveComm.receive());
	}

	/**
	 * connects to a given server
	 * 
	 * @param address
	 *            the address of the {@link Server}
	 * @param port
	 *            the port number of the {@link Server}
	 */
	public void connect(InetAddress address, int port) {

	}

	/**
	 * transfers this client to a new {@link Server}
	 * 
	 * @param address
	 *            the address of the {@link Server}
	 * @param port
	 *            the port number of the {@link Server}
	 */
	public void handoff(InetAddress address, int port) {

	}
}
