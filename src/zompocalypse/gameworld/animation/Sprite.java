package zompocalypse.gameworld.animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Retrieves a sprite from the sprite sheet or file.
 *
 * http://gamedev.stackexchange.com/questions/53705/how-can-i-make-a-sprite-sheet-based-animation-system
 * ^^this code contains exactly what we needed.
 * @author Pauline Kelly
 *
 */

public class Sprite {
	private static final int TILE_SIZE = 32;

	private BufferedImage spriteSheet;
	private String filePrefix;

	public Sprite(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	/**
	 * The file will be the prefix of the sprite sheet image.
	 * @param file
	 * @return
	 */
	private static ImageIcon loadSprite(String file){
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(new File("images/" + file + "_sheet.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return new ImageIcon(sprite);
	}

	/**
	 * Gets the appropriate sprite from the "2d array" of the sprite sheet.
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	public BufferedImage getSprite(int xPos, int yPos){
		if(spriteSheet == null){
			spriteSheet = loadSprite(filePrefix);
		}
		return spriteSheet.getSubimage(xPos * TILE_SIZE,
				yPos * TILE_SIZE,
				TILE_SIZE,
				TILE_SIZE);
	}
}