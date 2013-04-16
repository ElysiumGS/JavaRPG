package uk.zelware.Game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import uk.zelware.Game.Entities.Player;
import uk.zelware.Game.Entities.PlayerMP;
import uk.zelware.Game.Level.Level;
import uk.zelware.Game.Net.GameClient;
import uk.zelware.Game.Net.GameServer;
import uk.zelware.Game.Net.Packets.Packet00Login;
import uk.zelware.Game.gfx.Screen;
import uk.zelware.Game.gfx.SpriteSheet;
import uk.zelware.Sound.Sound;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 6;
	public static final String NAME = "Zelware";
	public static Game game;
	
	public static final Dimension DIMENSIONS = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
	
	public JFrame frame;
	
	private Thread thread;
	
	public boolean running = false;
	public int tickCount = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int[] colours = new int[6*6*6];
	
	public Screen screen;
	public InputHandler input;
	public WindowHandler windowHandler;
	
	public Level level;
	public Player player = null;
	
	public GameClient socketClient;
	public GameServer socketServer;
	
	public boolean debug = true;
	public boolean isApplet = false;
	
	public Game(){
		
	}
	
	public void init() {
        game = this;
        int index = 0;
        for (int r = 0; r < 6; r++) {
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);

                    colours[index++] = rr << 16 | gg << 8 | bb;
                }
            }
        }
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
        input = new InputHandler(this);
        level = new Level("/Levels/town_xeno.png");
        player = new PlayerMP(level, 100, 100, input, JOptionPane.showInputDialog(this, "Please enter a username"),
                null, -1);
        level.addEntity(player);
        if (!isApplet) {
            Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.x, player.y);
            if (socketServer != null) {
                socketServer.addConnection((PlayerMP) player, loginPacket);
            }
            loginPacket.writeData(socketClient);
        }
    }
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime)/nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >= 1){
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(shouldRender){
			frames++;
			render();
			}
			
			if(System.currentTimeMillis() - lastTimer > 1000){
				lastTimer += 1000;
				debug(DebugLevel.INFO, frames + "frames ," + ticks + "ticks");
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void tick(){
		tickCount++;
		level.tick();
	}
	
	public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        int xOffset = player.x - (screen.width / 2);
        int yOffset = player.y - (screen.height / 2);

        level.renderTiles(screen, xOffset, yOffset);
        level.renderEntities(screen);

        for (int y = 0; y < screen.height; y++) {
            for (int x = 0; x < screen.width; x++) {
                int colourCode = screen.pixels[x + y * screen.width];
                if (colourCode < 255)
                    pixels[x + y * WIDTH] = colours[colourCode];
            }
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, NAME+ "_main");
		thread.start();
		Sound.playSound("/Summer_Flowers.mid");
		if(JOptionPane.showConfirmDialog(this, "Do you want to run the server?") == 0){
			socketServer = new GameServer(this);
			socketServer.start();
		}
		socketClient = new GameClient(this, "127.0.0.1");
		socketClient.start();
	}
	
	public synchronized void stop() {
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new Game().start();
	}
	
	public void debug(DebugLevel level, String msg){
		switch(level){
		default:
		case INFO:
			if(debug){
				System.out.println("["+NAME+"]"+msg);
			}
			break;
		case WARNING:
			System.out.println("["+NAME+"][WARNING]"+msg);
			break;
		case SEVERE:
			System.out.println("["+NAME+"][SEVERE]"+msg);
			this.stop();
			break;
		}
	}
	
	public static enum DebugLevel{
		INFO, WARNING, SEVERE;
	}
	
	public static void OKDialog(String message){
		JOptionPane.showMessageDialog(game, message);
	}
}
