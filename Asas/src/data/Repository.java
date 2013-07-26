package data;

import java.util.Collection;

import basic.NamedEntity;

public interface Repository<T extends NamedEntity>{
	public void addInOrder(T entity);
	public T get(String name);
	public boolean exists(String name);
	public Collection<T> all();
	public void update(T entity);
	public void remove(T entity);
}
