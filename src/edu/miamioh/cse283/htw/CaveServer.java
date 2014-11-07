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
	protected ServerSocket caveSystemServerSocket;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		CaveServer c = new CaveServer(Integer.parseInt(args[0]));
		c.run();
	}

	public CaveServer(int basePort) throws IOException {
		clientSocket = new ServerSocket(basePort);
		caveSystemServerSocket = new ServerSocket(basePort + 1);
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
				BufferedReader in = new BufferedReader(new InputStreamReader(
						client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream(),
						true);

				out.println("localhost");
				out.println("" + 1235);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
