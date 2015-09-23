package userInterface.renderWindow;

public class LevelBuilder {

	RenderPanel renderPanel = new RenderPanel(null, null);

	public LevelBuilder(){
		new Gui();
	}

	public static void main(String[] args) {
		new LevelBuilder();
	}

}
