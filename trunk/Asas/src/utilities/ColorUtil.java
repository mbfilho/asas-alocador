package utilities;

import java.awt.Color;

public class ColorUtil {

	public static Color mixColors(Color ... colors) {
		float r = 0, g = 0, b = 0;
		for(Color c : colors){
			r += c.getRed()/255.0;
			g += c.getGreen()/255.0;
			b += c.getBlue()/255.0;
		}
		
		r /= colors.length;
		g /= colors.length;
		b /= colors.length;
		
		return new Color(r,g,b);
	}
	
	public static double getDistanceBetweenColors(Color c1, Color c2){
		double dist = 0;
		int v1[] = {c1.getRed(), c1.getGreen(), c1.getBlue()};
		int v2[] = {c2.getRed(), c2.getGreen(), c2.getBlue()};
		
		for(int i = 0; i < 3; ++i)
			dist += (v2[i] - v1[i]) * (v2[i] - v1[i]);
		
		dist /= 255*Math.sqrt(3);
		return dist;	
	}
}
