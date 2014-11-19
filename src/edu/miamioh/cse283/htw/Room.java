package edu.miamioh.cse283.htw;

import java.util.*;

public class Room {
	public ArrayList<ClientProxy> players;
	private ArrayList<Room> connections;
	private int n;

	public Room(int n) {
		players = new ArrayList<ClientProxy>();
		connections = new ArrayList<Room>();
		this.n = n;
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
		players.add(client);
	}
}
