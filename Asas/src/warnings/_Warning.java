package warnings;

import java.io.Serializable;
import basic.NamedEntity;

public abstract class _Warning implements Serializable, NamedEntity{
	
	public abstract String getMessage();
	public abstract boolean equals(Object obj);
	
	public String toString(){
		return getMessage();
	}
	
	public String getName(){
		return getMessage();
	}
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
}
