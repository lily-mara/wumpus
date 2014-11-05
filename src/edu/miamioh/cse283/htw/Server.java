package edu.miamioh.cse283.htw;

import java.io.*;
import java.net.*;

public class Server {
	// networking code
	//
	// connecting
	// handoff

	protected ServerSocket socket;

	/**
	 * Create a new Server object that listens on the given port
	 * 
	 * @param port
	 *            the port number on which to listen
	 * @throws IOException
	 */
	public Server(int port) throws IOException {
		socket = new ServerSocket(port);
	}

	/**
	 * Listen on the socket and returns a new {@link Client} socket
	 * 
	 * @return new {@link Client} socket
	 */
	protected Socket getNewClient() {
		try {
			return socket.accept();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Send a text message from this server to the given client.
	 * 
	 * @param client
	 *            the {@link Client} to which the message should be sent
	 * @param message
	 *            the message that should be sent
	 */
	public void send(Socket client, String message) {
		try {
			new PrintWriter(client.getOutputStream()).println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Listen on the given socket for a message from the {@link Client}
	 * 
	 * @param client
	 *            the socket on which to listen
	 * @return the message that the {@link Client} sent. If there was a problem,
	 *         return "ERR"
	 */
	public String recieve(Socket client) {
		try {
			return new BufferedReader(new InputStreamReader(
					client.getInputStream())).readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "ERR";
		}
	}
}
