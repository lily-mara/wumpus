package edu.miamioh.cse283.htw;

import java.io.*;

public class CaveServer {
	// networking code
	//
	// send cave description to client
	// client sends actions to server
	// (play the game)
	public final static int PORT = 4484;

	public static void main(String[] args) {
		CaveServer c = new CaveServer();
		c.run();
	}

	public void run() {
	}
}
