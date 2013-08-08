package basic;

public interface NamedEntity extends Cloneable{
	public String getName();
	public Object clone() throws CloneNotSupportedException;
}
