package zompocalypse.gameworld.characters;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import zompocalypse.gameworld.Direction;
import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.items.Container;
import zompocalypse.gameworld.items.Item;
import zompocalypse.gameworld.items.Weapon;
import zompocalypse.gameworld.world.World;

/**
 * Player is a human played character in the game.
 *
 * @author Kieran Mckay, 300276166
 */
public final class Player extends MovingCharacter {

	private static final long serialVersionUID = 9090914565024175293L;

	private List<Item> inventory;

	private final int PLAYER_HEALTH = 100;
	private final int PLAYER_SPEED = 5;
	private final int PLAYER_STRENGTH = 20;
	private final int BASE_ATTACK = 10;

	private int score;
	private final int OFFSETY = -20;

	private String playerName;

	private Item queuedUse;
	private Item queuedTake;
	private Container queuedContainer;

	// This is the currently equipped Item
	private Weapon equipped;

	private Orientation worldView;

	/**
	 * @param uid
	 * @param score
	 * @param playerName
	 * @param game
	 * @param xCoord
	 * @param yCoord
	 * @param direction
	 * @param filenames
	 */
	public Player(int xCoord, int yCoord, Orientation orientation, int uid,
			int score, String playerName, String[] filenames, World game) {
		super(uid, game, xCoord, yCoord, orientation, filenames);

		worldView = Orientation.NORTH;

		this.score = score;
		this.playerName = playerName;

		setHealth(PLAYER_HEALTH);
		setSpeed(PLAYER_SPEED);
		setStrength(PLAYER_STRENGTH);

		this.inventory = new ArrayList<Item>();
	}

	public void queueItem(Item item) {
		queuedUse = item;
	}

	public void queueTake(Item item, Container container) {
		queuedTake = item;
		queuedContainer = container;
	}

	public void useQueued() {
		if(queuedUse != null){
			queuedUse.use(this);
		}
		queuedUse = null;
	}

	public void dropQueued() {
		if(queuedUse != null){
			inventory.remove(queuedUse);
			PriorityQueue<GameObject>[][] objects = game.getObjects();
			objects[xCoord][yCoord].add(queuedUse);
		}
		queuedUse = null;
	}

	public void takeQueued() {
		if(queuedTake != null){
			queuedTake.use(this);
			queuedContainer.getHeldItems().remove(queuedTake);
		}

		queuedTake = null;
		queuedContainer = null;
	}

	/**
	 * Get this players score.
	 */
	public int score() {
		return score;
	}

	/**
	 * Calculates the players attack damage. This is a product of a random
	 * number multiplied by their strength, with their base attack added to the end.
	 *
	 * @return
	 */
	public int calculateAttack() {
		int attack = (int) (Math.random() * (getStrength()));
		attack += BASE_ATTACK;

		return attack;
	}

	/**
	 * Get this players strength
	 */
	@Override
	public int getStrength() {
		int strength = super.getStrength();

		if(equipped != null) {
			strength += equipped.getStrength();
		}

		return strength;
	}

	public int getScore() {
		return score;
	}

	/**
	 * Add to this players score.
	 */
	public void addScore(int points) {
		score += points;
	}

	/**
	 * Ticks the game over.
	 */
	@Override
	public void tick(World game) {
		if (!isDead()) {
			super.tick(game);

			if (game.tickTimer % 10 == 0) {
				addScore(1);
			}
		}
	}

	@Override
	public String toString() {
		return "Player [uid=" + getUid() + ", orientation=" + getFacing()
				+ ", score=" + score + ", health=" + getHealth() + ", speed="
				+ getSpeed() + ", strength=" + getStrength() + ", charactername=" + getFileName()
				+ "]";
	}

	/**
	 * The name of the character this player is playing as
	 * @return String - the name of this players character
	 */
	public String getName(){
		return playerName;
	}

	/**
	 * Picks up the item and puts it in the inventory.
	 * @param item
	 * @return
	 */
	public boolean pickUp(Item item) {
		return inventory.add(item);
	}

	/**
	 * This players inventory items
	 *
	 * @return An Arraylist containing this players inventory
	 */
	public List<Item> getInventory() {
		return inventory;
	}

	/**
	 * Gets the equipped weapon
	 * @return
	 */
	public Weapon getEquipped() {
		return equipped;
	}

	public void setEquipped(Weapon weapon) {
		this.equipped = weapon;
	}

	/**
	 * Moves the camera.
	 *
	 * @param north
	 * @param cameraDirection
	 */
	public void move(Orientation direction) {
		Orientation newDirection = Orientation.getCharacterOrientation(direction, worldView);

		System.out.println("direction "+direction.toString());
		System.out.println("world view  "+worldView.toString());
		System.out.println("new direction "+newDirection.toString());

		switch (newDirection) {
		case NORTH:
			this.moveNorth();
			break;
		case EAST:
			this.moveEast();
			break;
		case SOUTH:
			this.moveSouth();
			break;
		case WEST:
			this.moveWest();
			break;
		}
	}

	/**
	 * The player uses the first item where they are standing, or infront of them.
	 * Also used for attacking all Zombies infront of the player.
	 */
	public void use(){
		// Process any objects the player is standing on first
		for (GameObject o : getObjectsHere()) {
			if (o instanceof Item) {
				((Item) o).use(this);
				return;
			}
		}
		// Then, if no objects were used before, process any in front of the
		// player
		for (GameObject o : getObjectsInfront()) {
			if(o instanceof StrategyZombie) {
				StrategyZombie zombie = (StrategyZombie) o;
				int damage = calculateAttack();
				zombie.damaged(damage);
			} else if (o instanceof Item) {
				((Item) o).use(this);
				return;
			}
		}
	}

	/**
	 * Calls super on the draw method
	 */
	@Override
	public void draw(int x, int y, Graphics g, Orientation worldOrientation) {
		super.draw(x, y, OFFSETY, g, worldOrientation);
	}

	@Override
	public boolean occupiable() {
		// consider changing to false if we want players to be able to block
		return true;
	}

	public Orientation getWorldView(){
		return worldView;
	}

	public void setWorldView(Orientation worldView){
		this.worldView = worldView;
	}

	/**
	 * Rotates the perspective of the player view
	 * @param value
	 */
	public void rotatePerspective(Direction value) {
		switch (value) {

		case CLOCKWISE:
			updateCurrentOrientationClockwise();
			return;
		case ANTICLOCKWISE:
			updateCurrentOrientationAntiClockwise();
			return;
		default:
			throw new IllegalArgumentException(
					"Direction wasn't clockwise or anticlockwise");
		}
	}


	/**
	 * Updates the current orientation of the viewer to its anticlockwise
	 * counterpart.
	 */
	protected void updateCurrentOrientationAntiClockwise() {
		worldView = Orientation.getPrev(getFacing());
	}

	/**
	 * Updates the current orientation of the viewer to its clockwise
	 * counterpart.
	 */
	protected void updateCurrentOrientationClockwise() {
		worldView = Orientation.getNext(getFacing());
	}


	/**
	 * Set this players location
	 *
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void setLocation(int x, int y) {
		this.xCoord = x;
		this.yCoord = y;
	}

	/**
	 * Sets this players unique identifier
	 * @param uid - this players unique ID
	 */
	public void setUID(int uid) {
		this.uid = uid;
	}
}