package zompocalypse.gameworld.characters;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import zompocalypse.gameworld.GameObject;
import zompocalypse.gameworld.Orientation;
import zompocalypse.gameworld.items.Container;
import zompocalypse.gameworld.items.Item;
import zompocalypse.gameworld.items.Key;
import zompocalypse.gameworld.items.Money;
import zompocalypse.gameworld.items.Torch;
import zompocalypse.gameworld.items.Weapon;
import zompocalypse.gameworld.world.World;

/**
 * Player is a human played character in the game.
 *
 * @author Kieran Mckay, 300276166
 */
public final class Player extends MovingCharacter {

	private static final long serialVersionUID = 1L;

	private List<Item> inventory;

	private final int PLAYER_HEALTH = 100;
	private final int PLAYER_SPEED = 5;
	private final int PLAYER_STRENGTH = 20;
	private final int BASE_ATTACK = 10;

	private int score;
	private final int OFFSETY = -20;

	private Item queuedUse;
	private Item queuedTake;
	private Container queuedContainer;

	// This is the currently equipped Item
	private Weapon equipped;

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

		this.score = score;

		setHealth(PLAYER_HEALTH);
		setSpeed(PLAYER_SPEED);
		setStrength(PLAYER_STRENGTH);

		this.inventory = new ArrayList<Item>();

		// TODO: This is just temporary, adding objects to the Players
		// inventory so something is visible when viewing their backpack
		/*inventory.add(new Key("gold_key_inv.png", 1));
		inventory.add(new Key("gold_key_inv.png", 2));
		inventory.add(new Torch("torch.png", 3));
		inventory.add(new Money("coins_gold.png", 4, 10));*/
		//equipped = new Weapon("sword_1.png", "A curved blade. Vicious!", 5, 5);
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
	
	public void takeQueued() {
		
		System.out.println("yes");
		
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
	 * Get this players speed
	 */
	@Override
	public int getStrength() {
		return super.getStrength() + equipped.getStrength();
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
		}
	}

	@Override
	public String toString() {
		return "Player [uid=" + getUid() + ", orientation=" + getOrientation()
				+ ", score=" + score + ", health=" + getHealth() + ", speed="
				+ getSpeed() + ", strength=" + getStrength() + ", charactername=" + getFileName()
				+ "]";
	}

	/**
	 * Gets the priority queue
	 */
	public PriorityQueue<GameObject> getObjectsHere() {
		int myX = getX();
		int myY = getY();

		World game = getWorld();
		PriorityQueue<GameObject>[][] worldObs = game.getObjects();

		return worldObs[myX][myY];
	}

	/**
	 * Gets a priority queue for drawing the objects in the right order.
	 * @return
	 */
	public PriorityQueue<GameObject> getObjectsInfront() {
		int frontX = getX();
		int frontY = getY();

		PriorityQueue<GameObject>[][] worldObs = game.getObjects();

		if (getOrientation() == Orientation.NORTH && frontY > 0) {
			return worldObs[frontX][frontY - 1];
		} else if (getOrientation() == Orientation.EAST && frontX < game.width() - 1) {
			return worldObs[frontX + 1][frontY];
		} else if (getOrientation() == Orientation.SOUTH
				&& frontY < game.height() - 1) {
			return worldObs[frontX][frontY + 1];
		} else if (getOrientation() == Orientation.WEST && frontX > 0) {
			return worldObs[frontX - 1][frontY];
		}
		// if we are facing the edge of the world return an empty queue of
		// objects
		return new PriorityQueue<GameObject>();
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
	public void move(Orientation dir, Orientation cameraDirection) {
		Orientation ori = Orientation.getCharacterOrientation(dir, cameraDirection);
		switch (ori) {
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
}