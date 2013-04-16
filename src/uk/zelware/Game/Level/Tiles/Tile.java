package uk.zelware.Game.Level.Tiles;

import uk.zelware.Game.Level.Level;
import uk.zelware.Game.gfx.Colours;
import uk.zelware.Game.gfx.Screen;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colours.get(000, -1,-1,-1), 0xFF000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colours.get(-1,222, -1, -1), 0xFF555555);
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colours.get(-1, 131, 141, -1), 0xFF00FF00, false);
	public static final Tile STONEBRICK = new BasicTile(4,3,0, Colours.get(-1, 333, 444, -1), 0xFFFF0000, false);
	public static final Tile WATER = new AnimatedTile(3, new int[][] {{0, 5}, {1, 5}, {2, 5}, {1, 5}}, Colours.get(-1, 004, 115, -1), 0xFF0000FF, 500, false);
	public static final Tile LAVA = new AnimatedTile(5, new int[][]{{0, 5}, {1, 5}, {2, 5}, {1, 5}}, Colours.get(-1, 410, 210, -1), 0xFFFF4E00, 300, true);
	public static final Tile DIRT = new BasicTile(6,4,0, Colours.get(-1, 210, 320, -1), 0xFF5a2800, false);

	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	private int colour;
	protected boolean deadly;
	
	public Tile(int id, boolean isSolid, boolean isEmitter, int colour, boolean isDeadly){
		this.id = (byte) id;
		if(tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		tiles[id] = this;
		this.colour = colour;
		this.deadly = isDeadly;
	}
	
	public byte getId(){
		return id;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public boolean isEmitter(){
		return emitter;
	}
	
	public boolean isDeadly(){
		return deadly;
	}
	
	public int getLevelColour(){
		return colour;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen, Level level, int x, int y);
}
