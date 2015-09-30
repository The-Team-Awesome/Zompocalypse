package zompocalypse.ui.rendering;

public class LevelBuilder {

	RenderPanel renderPanel = new RenderPanel(0, null);

	public LevelBuilder(){
		new Gui();
	}

	public static void main(String[] args) {
		new LevelBuilder();
	}

}
