package edu.miamioh.cse283.htw;

import java.io.*;

public class CaveServer extends Server {
	// networking code
	//
	// send cave description to client
	// client sends actions to server
	// (play the game)
	public final static int PORT = 4484;

	public static void main(String[] args) {
		try {
			CaveServer c = new CaveServer();
			c.run();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("There was a problem, please try again later.");
		}
	}

	public CaveServer() throws IOException {
		super(PORT);
	}

	public void run() {
	}
}
