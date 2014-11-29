package edu.miamioh.cse283.htw;

import java.io.*;
import java.net.*;

/**
 * Proxy cave object.
 */
public class CaveProxy {
	/**
	 * This socket is connected to a cave server.
	 */
	protected Socket s;

	/**
	 * Used to read from the cave's socket.
	 */
	protected BufferedReader in;

	/**
	 * Used to write to the cave's socket.
	 */
	protected PrintWriter out;

	/**
	 * Address that should be used by clients to connect to this cave server.
	 */
	protected InetAddress clientAddr;

	/**
	 * Port number that should be used by clients to connect to this cave server.
	 */
	protected int clientPort;

	/**
	 * Constructor.
	 */
	public CaveProxy(Socket s) throws IOException {
		this.s = s;
		try {
			this.out = new PrintWriter(s.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException ex) {
			if (s != null) {
				try {
					s.close();
				} catch (Exception ex2) {
				}
			}
			throw ex;
		}
	}

	/**
	 * Sets the remote client address of this CaveProxy.
	 */
	public void setClientAddress(InetAddress addr) {
		clientAddr = addr;
	}

	/**
	 * Sets the remote client port of this CaveProxy.
	 */
	public void setClientPort(int port) {
		clientPort = port;
	}

	/**
	 * Returns the remote client address of this CaveProxy.
	 */
	public InetAddress getClientAddress() {
		return clientAddr;
	}

	/**
	 * Returns the remote client port of this CaveProxy.
	 */
	public int getClientPort() {
		return clientPort;
	}

	/**
	 * Close this connection to the CaveServer.
	 */
	public void close() throws IOException {
		s.close();
	}

	/**
	 * Returns a new cave proxy, which has been handed off by this one.
	 */
	public CaveProxy handoff(InetAddress addr, int port) throws IOException {
		s.close();
		return new CaveProxy(new Socket(addr, port));
	}

	/**
	 * Send a move message to the CaveServer.
	 */
	public void move(String room) throws IOException {
		out.println(Protocol.MOVE_ACTION + " " + room);
	}

	/**
	 * Send a shoot message to the CaveServer.
	 */
	public void shoot(String room) throws IOException {
		out.println(Protocol.SHOOT_ACTION + " " + room);
	}

	/**
	 * Send a pickup message to the CaveServer.
	 */
	public void pickup() throws IOException {
		out.println(Protocol.PICKUP_ACTION);
	}

	/**
	 * Send a climb message to the CaveServer.
	 */
	public void climb() throws IOException {
		out.println(Protocol.CLIMB_ACTION);
	}

	/**
	 * Send a connect message to the CaveSystemServer.
	 */
	public void connect(String server) throws IOException {
		out.println(Protocol.CONNECT + " " + server);
	}

	/**
	 * Returns true if there is a message to be read from the CaveServer.
	 */
	public boolean ready() throws IOException {
		return in.ready();
	}

	/**
	 * Returns a line from the cave server.
	 */
	public String nextLine() throws IOException {
		return in.readLine();
	}
}
