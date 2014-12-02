package edu.miamioh.cse283.htw;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Proxy client object.
 */
public class ClientProxy {

	/**
	 * This socket is connected to a client.
	 */
	private Socket s;

	/**
	 * Used to read from the client's socket.
	 */
	private BufferedReader in;

	/**
	 * Used to write to the client's socket.
	 */
	private PrintWriter out;

	private Room currentRoom;
	private boolean alive;
	private int arrows;
	private int gold;

	/**
	 * Constructor.
	 */
	public ClientProxy(Socket s) throws IOException {
		this.s = s;
		try {
			this.out = new PrintWriter(s.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.alive = true;
			this.arrows = Protocol.INITIAL_ARROWS;
			gold = 0;
		} catch (IOException ex) {
			try {
				s.close();
			} catch (IOException ex2) {
			}
			throw ex;
		}
	}

	/**
	 * Close the connection to the client.
	 */
	public void close() throws IOException {
		currentRoom.leaveRoom(this);
		s.close();
	}

	public int getArrows() {
		return arrows;
	}

	public void pickupArrows() {
		arrows += Protocol.ROOM_ARROWS;
		sendNotifications(String.format("You now have %d arrows!", arrows));
	}

	public void shootArrow() {
		--arrows;
	}

	public void increaseGold(int delta) {
		assert delta > 0;
		gold += delta;
		sendNotifications(String.format("You now have %d gold!", gold));
	}

	public int getGold() {
		return gold;
	}

	/**
	 * Send a handoff message to this client.
	 */
	public void handoff(InetAddress addr, int port) {
		String msg = Protocol.HANDOFF + " " + addr.getHostName() + " " + port;
		out.println(msg);
	}

	/**
	 * Send notification messages to the client.
	 */
	public void sendNotifications(String... notifications) {
		out.println(Protocol.BEGIN_NOTIFICATION);
		for (String i : notifications) {
			out.println(i);
		}
		out.println(Protocol.END_NOTIFICATION);
	}

	/**
	 * Send a block message of notifications to the client.
	 */
	public void sendNotifications(ArrayList<String> blockMsg) {
		sendNotifications(blockMsg.toArray(new String[blockMsg.size()]));
	}

	/**
	 * Send a block message of sensory information to the client.
	 */
	public void sendSenses(ArrayList<String> blockMsg) {
		out.println(Protocol.BEGIN_SENSES);
		for (String i : blockMsg) {
			out.println(i);
		}
		out.println(Protocol.END_SENSES);
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * Moves this ClientProxy from the current Room to the given Room and send senses to client
	 *
	 * @param toEnter the room to which this ClientProxy should move
	 */
	public synchronized void changeRoom(Room toEnter) {
		changeRoom(toEnter, true);
	}

	/**
	 * Moves this ClientProxy from the current Room to the given Room.
	 *
	 * @param toEnter  the room to which this ClientProxy should move
	 * @param sendInfo if this is true, send senses to the client, otherwise do not send senses to client
	 */
	public synchronized void changeRoom(Room toEnter, boolean sendInfo) {
		if (currentRoom != null) {
			currentRoom.leaveRoom(this);
		}
		currentRoom = toEnter;
		toEnter.enterRoom(this);
		if (sendInfo) {
			sendSenses(currentRoom.getSensed());
		}
	}

	/**
	 * Returns true if the player is alive.
	 */
	public synchronized boolean isAlive() {
		return alive;
	}

	/**
	 * Send a DIED message.
	 */
	public synchronized void kill() {
		out.println(Protocol.DIED);
		alive = false;
	}

	/**
	 * Returns true if this client has data that can be read.
	 */
	public boolean ready() throws IOException {
		return in.ready();
	}

	/**
	 * Get an action from the client.
	 */
	public String nextLine() throws IOException {
		return in.readLine();
	}
}
