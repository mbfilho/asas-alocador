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
	
	public static String sanitize(String arg){
		arg = arg.trim();
		int ini = 0, fim = arg.length() - 1;
		while(ini < arg.length()){
			char ch = arg.charAt(ini);
			if(!Character.isWhitespace((int) ch) && !Character.isSpaceChar(ch)) break;
			++ini;
		}
		
		while(fim >= ini){
			char ch = arg.charAt(fim);
			if(!Character.isWhitespace(ch) && !Character.isSpaceChar(ch)) break;
			--fim;
		}
		if(ini <= fim)
			return arg.substring(ini, fim+1);
		else
			return "";
			
	}
	
}
