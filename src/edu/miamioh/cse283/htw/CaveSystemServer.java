package edu.miamioh.cse283.htw;

import java.io.*;
import java.net.*;

/**
 * The server that manages the entire Cave System. Each Cave System is comprised
 * of multiple caves that are represented by {@link CaveServer}s.
 */
public class CaveSystemServer {
	// networking code
	//
	// manage new cave servers

	protected ServerSocket clientSocket;
	protected ServerSocket caveSocket;

	public static void main(String[] args) {
		try {
			CaveSystemServer s = new CaveSystemServer(Integer.parseInt(args[0]));
			s.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Construct a {@link CaveSystemServer} that listens on the specified ports
	 * 
	 * @param basePort
	 *            Port number that this should start listening on for
	 *            {@link Client}s. Will listen for {@link CaveServer}s on
	 *            basePort+1
	 * 
	 * @throws IOException
	 */
	public CaveSystemServer(int basePort) throws IOException {
		clientSocket = new ServerSocket(basePort);
		caveSocket = new ServerSocket(basePort + 1);
	}

	/**
	 * The main execution logic for the {@link CaveSystemServer}; start
	 * listening for {@link Client} and {@link CaveServer} connections.
	 * 
	 * @throws IOException
	 */
	public void run() throws IOException {
		while (true) {
			Socket client = clientSocket.accept();
			(new Thread(new ServerThread(client))).start();
		}
	}

	public class ServerThread implements Runnable {
		protected Socket client;

		public ServerThread(Socket client) {
			this.client = client;
		}

		public void run() {
			try {
				SocketCommunicator s = new SocketCommunicator(client);

				s.send("localhost");
				s.send("" + 12345);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
