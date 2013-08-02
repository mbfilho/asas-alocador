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
	
}
