package uk.zelware.Game.Level.Tiles;

import uk.zelware.Game.Level.Level;
import uk.zelware.Game.gfx.Screen;

public class AnimatedTile extends BasicTile{

	private int[][] animationTileCoords;
	private int currentAnimationIndex;
	private long lastIterationTime;
	private int animationSwitchDelay;
	
	public AnimatedTile(int id, int[][] animationCoords,int tileColour, int levelColour, int animationSwitchDelay, boolean Deadly) {
		super(id, animationCoords[0][0], animationCoords[0][1], tileColour, levelColour, Deadly);
		this.animationTileCoords = animationCoords;
		this.currentAnimationIndex = 0;
		this.lastIterationTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
	}

	public void tick(){
		if((System.currentTimeMillis() - lastIterationTime) >= (animationSwitchDelay)){
			lastIterationTime = System.currentTimeMillis();
			currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
			this.tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1] * 32));
		}
	}
}
