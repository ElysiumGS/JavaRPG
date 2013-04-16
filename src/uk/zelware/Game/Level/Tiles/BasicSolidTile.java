package uk.zelware.Game.Level.Tiles;

public class BasicSolidTile extends BasicTile{

	public BasicSolidTile(int id, int x, int y, int tileColour, int levelColour) {
		super(id, x, y, tileColour, levelColour, false);
		this.solid = true;
	}

}
