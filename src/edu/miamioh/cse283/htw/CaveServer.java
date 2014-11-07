package edu.miamioh.cse283.htw;

import java.io.*;
import java.net.*;

public class CaveServer {
	// networking code
	//
	// send cave description to client
	// client sends actions to server
	// (play the game)
	protected ServerSocket clientSocket;
	protected Socket caveSystemServerSocket;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		CaveServer c = new CaveServer(Integer.parseInt(args[0]),
				InetAddress.getByName(args[1]), Integer.parseInt(args[2]));
		c.run();
	}

	public CaveServer(int basePort, InetAddress CSSAddress, int CSSPort)
			throws IOException {
		clientSocket = new ServerSocket(basePort);
		caveSystemServerSocket = new Socket(CSSAddress, CSSPort);
	}

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

				s.send("MSG");
				s.send("You're on the cave server!");
				s.send("SENSE");
				s.send("You smell a Wumpus");

				s.send("CHOICES");
				s.send("CHOICE");
				s.send("1");
				s.send("CHOICE");
				s.send("2");
				s.send("CHOICE");
				s.send("3");
				s.send("END_CHOICES");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
