package uk.zelware.Game.gfx;

import java.awt.Color;

public class Colours {

	public static int get(int colour1, int colour2, int colour3, int colour4){
		return (get(colour4)<<24) + (get(colour3)<<16) + (get(colour2) << 8) + get(colour1);
	}
	
	private static int get(int colour){
		if(colour < 0){return 255;}
		int r = colour/100%10;
		int g = colour/10%10;
		int b = colour%10;
		return r*36 + g * 6 + b;
	}
	
	public static int brightness(int color, double scale) {
		Color c = new Color(color);
		int r = Math.min(255, (int) (c.getRed() * scale));
		int g = Math.min(255, (int) (c.getGreen() * scale));
		int b = Math.min(255, (int) (c.getBlue() * scale));
		return (b | g << 8 | r << 16);
	}

	public static int tint(int color, double red, double green, double blue) {
		Color c = new Color(color);
		int r = Math.min(255, (int) (c.getRed() * red));
		int g = Math.min(255, (int) (c.getGreen() * green));
		int b = Math.min(255, (int) (c.getBlue() * blue));
		return (b | g << 8 | r << 16);
	}

	public static int tint(int color, Tint tint) {
		return Colours.tint(color, tint.getRed(), tint.getGreen(), tint.getBlue());
	}
}
