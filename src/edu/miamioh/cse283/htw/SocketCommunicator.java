package edu.miamioh.cse283.htw;

import java.io.*;
import java.net.*;

public class SocketCommunicator {
	protected Socket s;
	protected BufferedReader in;
	protected PrintWriter out;

	public SocketCommunicator(Socket s) throws IOException {
		this.s = s;
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}

	public void send(String msg) {
		out.println(msg);
	}

	public String receive() throws IOException {
		return in.readLine();
	}
}
