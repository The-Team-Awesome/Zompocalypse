package zompocalypse.gameworld;

/**
 *	Represents the current orientation of the camera for a Player,
 *	or the orientation of an object in space.
 *
 * @author Pauline Kelly
 *
 */
public enum Orientation {
	NORTH,
	EAST,
	SOUTH,
	WEST;

	/**
	 * Combines the orientation of the object in space with the
	 * orientation of the object in the view to gets the orientation
	 * of the character/gameobject to be displayed.
	 *
	 * @param queued Orientation of the object in space
	 * @param worldOrientation Orientation of the camera
	 * @return The resulting orientation.
	 */
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