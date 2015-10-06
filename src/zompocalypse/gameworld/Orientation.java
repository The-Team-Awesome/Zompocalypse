package zompocalypse.gameworld;

public enum Orientation {
	NORTH, EAST, SOUTH, WEST;

	public static Orientation getCharacterOrientation(Orientation queued,
			Orientation worldOrientation) {

		int mer = (queued.ordinal() + +4 - worldOrientation.ordinal()) % 4;

		// TODO this should be North, East, South, West, in that order!
		// Temporarily this way because it draws 'correctly'.

		switch (mer) {
		case 0:
			return NORTH;
		case 1:
			return WEST;
		case 2:
			return SOUTH;
		case 3:
			return EAST;
		}

		return NORTH;
	}
}