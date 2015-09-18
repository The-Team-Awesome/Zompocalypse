package gameWorld;

public class Door extends Tile implements Item{
	private boolean open;
	private boolean locked;

	public void use() {
		if(locked){
			return;
		}
		open = !open;
	}

	private boolean unlock(boolean hasKey){
		if (hasKey){
			locked = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean canMove() {
		return false;
	}
}
