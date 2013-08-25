package data.repository;

import java.util.Vector;

import data.NamedEntity;


public interface Repository<T extends NamedEntity> extends Iterable<T>{
	public void addInOrder(T entity);
	public T get(String name);
	public boolean exists(String name);
	public Vector<T> all();
	public void update(T entity);
	public void remove(T entity);
}
