package zompocalypse.ui.appwindow;

/**
 * Enumeration of UI component action command names.
 * @author Danielle Emygdio
 *
 */
public enum UICommand {
	ROTATECLOCKWISE("RotateClockwise"),
	ROTATEANTICLOCKWISE("RotateAnticlockwise"),
	NORTH("North"),
	SOUTH("South"),
	WEST("West"),
	EAST("East"),
	ITEMONE("ItemOne"),
	ITEMTWO("ItemTwo"),
	ITEMTHREE("ItemThree"),
	USE("Use"),
	SINGLEPLAYER("SinglePlayer"),
	MULTIPLAYER("Multiplayer"),
	LOADGAME("LoadGame"),
	ENTERIP("EnterIP"),
	OPTIONS("Options");

	private String value;

	private UICommand(final String value) {
		this.value = value;
	}

	public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

}
