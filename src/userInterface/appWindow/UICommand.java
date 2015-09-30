package userInterface.appWindow;

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
	SINGLEPLAYER("SinglePlayer"),
	MULTIPLAYER("Multiplayer"),
	LOADGAME("LoadGame"),
	ENTERIP("EnterIP");

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
