package edu.miamioh.cse283.htw;

import java.io.*;

public class CaveSystemServer extends Server {
	// networking code
	//
	// manage new cave servers
	public final static int PORT = 4484;

	public static void main(String[] args) {
		try {
			CaveSystemServer c = new CaveSystemServer();
			c.run();
		} catch (IOException e) {
			// e.printStackTrace();
			System.err.println("There was a problem, please try again later.");
		}
	}

	/**
	 * Construct a new {@link CaveSystemServer}
	 * 
	 * @throws IOException
	 */
	public CaveSystemServer() throws IOException {
		super(PORT);
	}

	/**
	 * The main execution logic for the {@link CaveSystemServer}
	 */
	public void run() {

	}
}
