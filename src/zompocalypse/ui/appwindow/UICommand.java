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
	EXAMINE("Examine"),
	USEITEM("use-"),
	TAKEITEM("take-"),
	BACKPACK("Backpack"),
	BACKPACKUSE("BackpackUse"),
	BACKPACKDROP("BackpackDrop"),
	CONTAINER("Chest"),
	SINGLEPLAYER("SinglePlayer"),
	MULTIPLAYER("Multiplayer"),
	SERVER("Server"),
	STARTSERVER("StartServer"),
	CLIENT("Client"),
	LOADGAME("LoadGame"),
	ENTERIP("EnterIP"),
	OPTIONS("Options"),
	SAVEPLAYER("SavePlayer");

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
