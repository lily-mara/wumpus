package edu.miamioh.cse283.htw;

import java.util.*;

public class Room {

	public static final int EMPTY = 0;
	public static final int BATS = 1;
	public static final int HOLE = 2;
	public static final int WUMPUS = 3;
	public static final int OTHER_PLAYERS = 4;

	/**
	 * Players currently in this room.
	 */
	protected ArrayList<ClientProxy> players;

	/**
	 * Rooms that this room is connected to.
	 */
	protected HashSet<Room> connected;

	/**
	 * ID number of this room.
	 */
	protected int roomId;

	protected int contents;
	protected CaveServer rooms;

	/**
	 * Constructor.
	 */
	public Room(int contents, CaveServer rooms) {
		players = new ArrayList<ClientProxy>();
		connected = new HashSet<Room>();
		this.contents = contents;
		this.rooms = rooms;
	}

	public Room(CaveServer rooms) {
		this(EMPTY, rooms);
	}

	/**
	 * Set this room's id number.
	 */
	public void setIdNumber(int n) {
		roomId = n;
	}

	/**
	 * Get this room's id number.
	 */
	public int getIdNumber() {
		return roomId;
	}

	/**
	 * Connect room r to this room (bidirectional).
	 */
	public void connectRoom(Room r) {
		if (r != this && !connected.contains(r)) {
			connected.add(r);
			r.connected.add(this);
		}
	}

	/**
	 * Called when a player enters this room.
	 */
	public synchronized void enterRoom(ClientProxy c) {
		ArrayList<String> notifications = new ArrayList<String>();
		switch (contents) {
			case EMPTY:
				players.add(c);
				break;
			case OTHER_PLAYERS:
				players.add(c);
				break;
			case WUMPUS:
				notifications.add("You hear a snarl and turn to see the horrible Wumpus!");
				notifications.add("The wumpus eats you, and you are dead!");
				c.sendNotifications(notifications);
				c.kill();
				break;
			case HOLE:
				notifications.add("You hear the wind rush around you as you enter the room");
				notifications.add("You fall down a bottomless pit and are never heard from again!");
				c.sendNotifications(notifications);
				c.kill();
				break;
			case BATS:
				notifications.add("You hear the screeching of the bats, and they carry you off");
				c.sendNotifications(notifications);
				c.changeRoom(rooms.randomRoom(), false);
				break;
		}
	}

	/**
	 * Returns a random Room connected to this Room
	 *
	 * @return
	 */
	private Room randomConnectedRoom() {
		int size = connected.size();
		int rand = Utils.random(size);
		Room[] buffer = connected.toArray(new Room[size]);
		return buffer[rand];
	}

	/**
	 * Shoots an arrow into this room and notifies the client of the outcome.
	 *
	 * @param c the client to notify of the outcome of shooting
	 */
	public void shoot(ClientProxy c) {
		c.shootArrow();
		if (c.getArrows() > 0) {
			ArrayList<String> notifications = new ArrayList<String>();
			shoot(c, 0, notifications);

			notifications.add(String.format("You have %d arrows remaining.", c.getArrows()));
			c.sendNotifications(notifications);
		} else {
			c.sendNotifications("You are out of arrows");
		}
	}

	/**
	 * Shoots an arrow into this room
	 *
	 * @param hopCount      the number of rooms that the arrow has already visited
	 * @param notifications the arraylist of notifications that should be sent to the player
	 */
	private void shoot(ClientProxy c, int hopCount, ArrayList<String> notifications) {
		if (hopCount < Protocol.MAX_HOPS) {
			switch (contents) {
				case OTHER_PLAYERS:
					notifications.add("You managed to hit another adventurer, I'm sure they're very happy for you.");
					break;
				case WUMPUS:
					notifications.add("You killed the Wumpus!!!");

					notifications.add("");
					notifications.add("From the ashes, A new Wumpus is born.");
					notifications.add("Before you can react, it dashes away into the caves.");
					contents = EMPTY;
					Room r;
					int i = 0;

					do {
						r = rooms.randomRoom();
						++i;
					} while (r.contents != EMPTY && i < rooms.rooms.size());
					r.contents = WUMPUS;

					c.increaseScore(Protocol.WUMPUS_VALUE);
					notifications.add(String.format("You now have %d gold", c.getScore()));

					break;
				default:
					notifications.add("Your arrow does not hit anything in room " + roomId);
					randomConnectedRoom().shoot(c, hopCount + 1, notifications);
					break;
			}
		}
	}

	/**
	 * Called when a player leaves this room.
	 */
	public synchronized void leaveRoom(ClientProxy c) {
		players.remove(c);
	}

	/**
	 * Returns a connected Room (if room is valid), otherwise returns null.
	 */
	public Room getRoom(int room) {
		for (Room r : connected) {
			if (r.getIdNumber() == room) {
				return r;
			}
		}
		return null;
	}

	/**
	 * Returns a string describing what a player sees in this room.
	 */
	public synchronized ArrayList<String> getSensed() {
		ArrayList<String> msg = new ArrayList<String>();
		msg.add(String.format("You are in room %d.", roomId));

		for (Room r : connected) {
			switch (r.contents) {
				case WUMPUS:
					msg.add("You smell the smelly smell of a Wumpus!");
					break;
				case BATS:
					msg.add("You hear the screech of the bats");
					break;
				case OTHER_PLAYERS:
					msg.add("You hear the stomping of adventurer's feet");
					break;
				case HOLE:
					msg.add("You feel the rush of the wind");
					break;
			}
		}

		String t = "You see tunnels to rooms ";
		int c = 0;
		for (Room r : connected) {
			++c;
			if (c == connected.size()) {
				t = t.concat("and " + r.getIdNumber() + ".");
			} else {
				t = t.concat("" + r.getIdNumber() + ", ");
			}
		}
		msg.add(t);
		return msg;
	}
}
