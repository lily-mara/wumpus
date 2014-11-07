package edu.miamioh.cse283.htw;

import java.io.*;
import java.net.*;

public class CaveSystemServer {
	// networking code
	//
	// manage new cave servers

	protected ServerSocket clientSocket;
	protected ServerSocket caveSocket;

	public static void main(String[] args) {
		try {
			CaveSystemServer s = new CaveSystemServer();
			s.run(Integer.parseInt(args[0]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main execution logic for the {@link CaveSystemServer}
	 * 
	 * @param basePort
	 *            Port number that this should start listening on for
	 *            {@link Client}s. Will listen for {@link CaveServer}s on
	 *            basePort+1
	 * 
	 * @throws IOException
	 */
	public void run(int basePort) throws IOException {
		clientSocket = new ServerSocket(basePort);
		caveSocket = new ServerSocket(basePort + 1);

		while (true) {
			clientSocket.accept();
		}
	}
}
