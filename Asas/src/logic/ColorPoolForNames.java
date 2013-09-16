package logic;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import utilities.ColorUtil;

public class ColorPoolForNames {
	private HashMap<String, Color> colors;
	
	public ColorPoolForNames(List<String> names){
		colors = new HashMap<String, Color>();
		
		if(!names.isEmpty()){
			Iterator<Color> generatedColors = generateColors(names.size()).iterator();
			for(String name : names)
				colors.put(name, generatedColors.next());
		}
	}
	
	private List<Color> generateColors(int howMany){
		List<Color> difColors = new LinkedList<Color>();
		int step = Math.max(40, 360/howMany);
		for(int i = 0; i < 360/step; ++i){
			Color color = Color.getHSBColor((i*step)/360f, 1, 1);
			difColors.add(ColorUtil.mixWithWhite(color));
		}
		return difColors;
	}
	
	public Color getColor(String name){
		return colors.get(name);
	}
	
}
