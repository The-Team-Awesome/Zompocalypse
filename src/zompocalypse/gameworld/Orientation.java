package zompocalypse.gameworld;

/**
 *	Represents the current orientation of the camera for a Player,
 *	or the orientation of an object in space.
 *
 * @author Pauline Kelly
 *
 */
public enum Orientation {
	NORTH, EAST, SOUTH, WEST;

	public static Orientation getCharacterOrientation(Orientation queued,
			Orientation worldOrientation) {

		// some sweet modulus action, I <3 maths
		int mer = (worldOrientation.ordinal() - queued.ordinal() + 4) % 4;

		switch (mer) {
		case 0:
			return NORTH;
		case 1:
			return EAST;
		case 2:
			return SOUTH;
		case 3:
			return WEST;
		default:
			throw new IllegalStateException("Must be a valid direction.");
		}
	}
}