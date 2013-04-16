package uk.zelware.Game.Entities;

import java.net.InetAddress;

import uk.zelware.Game.InputHandler;
import uk.zelware.Game.Level.Level;

public class PlayerMP extends Player{
	
	public InetAddress ipAddress;
	public int port;

	public PlayerMP(Level level, int x, int y, InputHandler input, String username, InetAddress ipAddress, int port) {
		super(level, x, y, input, username);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public PlayerMP(Level level, int x, int y, String username, InetAddress ipAddress, int port) {
		super(level, x, y, null, username);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	@Override
	public void tick(){
		super.tick();
	}
}
