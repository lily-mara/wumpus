package edu.miamioh.cse283.htw;

public class RoomCollection {
	private Room[] rooms;

	public RoomCollection() {
		rooms = new Room[20];
		for (int i = 0; i < 20; i++) {
			rooms[i] = new Room(i + 1);
		}

		for (int i = 0; i < 19; i++) {
			rooms[i].addBidirectionalConnection(rooms[i + 1]);
		}

		rooms[0].addBidirectionalConnection(rooms[19]);
	}

	/**
	 * Returns the {@link edu.miamioh.cse283.htw.Room} with the given number
	 *
	 * @param roomNumber the number to search for in this RoomCollection
	 * @return the first {@link edu.miamioh.cse283.htw.Room} with a number matching roomNumber, or null if one doesn't exist
	 */
	public Room get(int roomNumber) {
		for (Room r : rooms) {
			if (r.getNumber() == roomNumber) {
				return r;
			}
		}
		return null;
	}

	public int[] getAdjacentRooms(Room test) {
		Room[] adjacentRooms = test.getConnections();
		int[] adjacentNumbers = new int[adjacentRooms.length];

		for (int i = 0; i < adjacentRooms.length; i++) {
			adjacentNumbers[i] = adjacentRooms[i].getNumber();
		}

		return adjacentNumbers;
	}

	public Room getStartingRoom() {
		return rooms[0];
	}
}
