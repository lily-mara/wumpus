package edu.miamioh.cse283.htw;

public class CaveCollection {
	private Room[] rooms;

	public CaveCollection() {
		rooms = new Room[20];
		for (int i = 0; i < 20; i++) {
			rooms[i] = new Room(i + 1);
		}

		for (int i = 0; i < 19; i++) {
			rooms[i].addBidirectionalConnection(rooms[i + 1]);
		}

		rooms[0].addBidirectionalConnection(rooms[19]);
	}

	public Room get(int roomNumber) {
		return rooms[roomNumber - 1];
	}

	public int[] getAdjacentRooms(Room test) {
		Room[] adjacentRooms = test.getConnections();
		int[] adjacentNumbers = new int[adjacentRooms.length];

		for (int i = 0; i < adjacentRooms.length; i++) {
			adjacentNumbers[i] = adjacentRooms[i].getNumber();
		}

		return adjacentNumbers;
	}
}
