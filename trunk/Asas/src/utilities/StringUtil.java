package utilities;

public class StringUtil {
	public static <T> String joinListWithSeparator(Iterable<T> list, String separator){
		String joined = "", prefix = "";
		
		for(T item : list){
			joined += prefix + item.toString();
			prefix = separator;
		}
		return joined;
	}
	
	public static String truncate(String arg, int chars){
		String suffix = chars < arg.length() ? "..." : "";
		return arg.substring(0, Math.min(chars, arg.length())) + suffix;
	}
	
	public static boolean isNullOrEmpty(String arg){
		return arg == null || arg.trim().equals("");
	}
	
}
