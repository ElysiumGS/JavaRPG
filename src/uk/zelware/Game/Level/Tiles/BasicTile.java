package uk.zelware.Game.Level.Tiles;

import uk.zelware.Game.Level.Level;
import uk.zelware.Game.gfx.Screen;

public class BasicTile extends Tile{
	
	protected int tileId;
	protected int tileColour;

	public BasicTile(int id, int x, int y, int tileColour, int levelColour, boolean Deadly) {
		super(id, false, false, levelColour, Deadly);
		this.tileId = x + y;
		this.tileColour = tileColour;
	}

	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColour, 1);
	}
	
	public void tick(){
		
	}

}
