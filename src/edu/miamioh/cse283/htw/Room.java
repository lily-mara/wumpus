package edu.miamioh.cse283.htw;

import java.util.*;

public class Room {
	public static final int EMPTY = 0;
	public static final int WUMPUS = 1;
	public static final int GOLD = 2;
	public static final int HOLE = 3;
	public static final int BATS = 4;
	public static final int OTHER_PLAYERS = 5;

	public ArrayList<ClientProxy> players;
	private ArrayList<Room> connections;
	private int n;
	private int contents;

	public Room(int n) {
		players = new ArrayList<ClientProxy>();
		connections = new ArrayList<Room>();
		this.n = n;
		contents = EMPTY;
	}

	public void addBidirectionalConnection(Room other) {
		connections.add(other);
		other.connections.add(this);
	}

	public Room[] getConnections() {
		return (Room[]) connections.toArray();
	}

	public int getNumber() {
		return n;
	}

	public void addPlayer(ClientProxy client) {
		switch (contents) {
			case WUMPUS:
				client.message("You hear a snarl and turn to see the crazed Wumpus just before he eats you alive!");
				client.kill();
				return;
		}
		players.add(client);
	}

	public void removePlayer(ClientProxy client) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) == client) {
				players.remove(i);
			}
		}
	}

	public boolean hasSense() {
		return contents != EMPTY;
	}

	public String getSense() {
		switch (contents) {
			case WUMPUS:
				return "You smell the smelly smell of a Wumpus";
			case OTHER_PLAYERS:
				return "You hear another adventurer knocking an arrow";
			case BATS:
				return "You hear the screech of the bats";
			case HOLE:
				return "You hear the whistling wind";
			case GOLD:
				return "You see the shimmering light of gold!";
		}
		return "You see nothing... nothing!";
	}

	public String[] getSenses() {
		ArrayList<String> senses = new ArrayList<String>();
		for (Room r : connections) {
			if (r.hasSense()) {
				senses.add(r.getSense());
			}
		}
		return senses.toArray(new String[senses.size()]);
	}

	public String tunnelMessage() {
		String tunnels = "Tunnels lead to";
		for (Room r : connections) {
			tunnels += " " + r.getNumber();
		}
		return tunnels;
	}
}
