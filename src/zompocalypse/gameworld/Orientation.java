package zompocalypse.gameworld;

import java.net.NoRouteToHostException;

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
		}

		// Cannot possibly ever get here!
		return NORTH;
	}

	public static Orientation getNext(Orientation current){
		if(current == NORTH){
			return EAST;
		}else if(current == EAST){
			return SOUTH;
		}else if(current == SOUTH){
			return WEST;
		}else {
			return NORTH;
		}
	}
}