package utilities;

import basic.NamedEntity;

public class StringUtil {
	public static <T extends NamedEntity> String joinListWithSeparator(Iterable<T> list, String separator){
		String joined = "", prefix = "";
		
		for(NamedEntity item : list){
			joined += prefix + item.getName();
			prefix = separator;
		}
		return joined;
	}
	
	public static String truncate(String arg, int chars){
		String suffix = chars < arg.length() ? "..." : "";
		return arg.substring(0, Math.min(chars, arg.length())) + suffix;
	}
	
}