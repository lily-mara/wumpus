package edu.miamioh.cse283.htw;

import java.net.*;
import java.util.*;

/**
 * The CaveServer class takes the following command-line parameters:
 * <p/>
 * <Hostname of CaveSystemServer> <port number of CaveSystemServer> <port number
 * of this CaveServer>
 * <p/>
 * E.g., "localhost 1234 2000"
 */
public class CaveServer {

	/**
	 * Port base for this cave server.
	 */
	protected int portBase;

	/**
	 * Socket for accepting connections from players.
	 */
	protected ServerSocket clientSocket;

	/**
	 * Proxy to the CaveSystemServer.
	 */
	protected CaveSystemServerProxy caveSystem;

	/**
	 * Rooms in this CaveServer.
	 */
	protected RoomCollection rooms;

	/**
	 * Constructor.
	 */
	public CaveServer(CaveSystemServerProxy caveSystem, int portBase) {
		this.caveSystem = caveSystem;
		this.portBase = portBase;
		rooms = new RoomCollection();
	}

	/**
	 * Returns the port number to use for accepting client connections.
	 */
	public int getClientPort() {
		return portBase;
	}

	/**
	 * This is the thread that handles a single client connection.
	 */
	public class ClientThread implements Runnable {
		/**
		 * This is our "client" (actually, a proxy to the network-connected
		 * client).
		 */
		protected ClientProxy client;

		/**
		 * Constructor.
		 */
		public ClientThread(ClientProxy client) {
			this.client = client;
		}

		/**
		 * Play the game with this client.
		 */
		public void run() {
			try {
				client.message("Welcome!");

				Room start = rooms.getStartingRoom();
				client.setCurrentRoom(start);

				while (client.isAlive()) {
					// now, in a loop while the player is alive:
					// -- send the player their "senses":
					client.message("You are in room " + client.getCurrentRoom().getNumber());
					for (String s : client.getCurrentRoom().getSenses()) {
						client.senses(s);
					}
					client.message("");
					// -- and retrieve their action:
					String action = client.getAction();
					// -- and perform the action
				}

			} catch (Exception ex) {
				// If an exception is thrown, we can't fix it here -- Crash.
				ex.printStackTrace();
				System.exit(1);
			}
		}
	}

	/**
	 * Runs the CaveSystemServer.
	 */
	public void run() {
		try {
			clientSocket = new ServerSocket(getClientPort());
			caveSystem.register(clientSocket);

			while (true) {
				ClientProxy client = new ClientProxy(clientSocket.accept());
				(new Thread(new ClientThread(client))).start();
			}
		} catch (Exception ex) {
			// If an exception is thrown, we can't fix it here -- Crash.
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Main method (run the CaveServer).
	 */
	public static void main(String[] args) throws Exception {
		InetAddress addr = InetAddress.getByName("localhost");
		int cssPortBase = 1234;
		int cavePortBase = 2000;

		if (args.length > 0) {
			addr = InetAddress.getByName(args[0]);
			cssPortBase = Integer.parseInt(args[1]);
			cavePortBase = Integer.parseInt(args[2]);
		}

		// first, we need our proxy object to the CaveSystemServer:
		CaveSystemServerProxy caveSystem = new CaveSystemServerProxy(
				new Socket(addr, cssPortBase + 1));

		// now construct this cave server, and run it:
		CaveServer cs = new CaveServer(caveSystem, cavePortBase);
		cs.run();
	}
}
