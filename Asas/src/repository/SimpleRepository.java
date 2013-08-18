package repository;

import java.io.Serializable;
import java.text.Collator;
import java.util.Iterator;
import java.util.Vector;

import basic.NamedEntity;

public class SimpleRepository <T extends NamedEntity> implements Repository<T>, Serializable{
	private Vector<T> entities;
	
	public SimpleRepository(Vector<T> entities){
		this.entities = entities;
	}
	
	public SimpleRepository(){
		this(new Vector<T>());
	}

	public void addInOrder(T entity) {
		int idx = 0;
		int oldStrength = Collator.getInstance().getStrength(); 
		Collator.getInstance().setStrength(Collator.SECONDARY);
		while(idx < entities.size()){
			String added = entities.get(idx).getName(), toAdd = entity.getName();
			if(Collator.getInstance().compare(added, toAdd) <= 0) ++idx;
			else break;
		}
		entities.add(idx, entity);
		Collator.getInstance().setStrength(oldStrength);
	}

	public T get(String name) {
		for(T entity : entities){
			if(entity.getName().equals(name)) return entity;
		}
		return null;
	}

	public boolean exists(String name) {
		return get(name) != null;
	}

	public Vector<T> all() {
		return entities;
	}

	public void update(T entity) {
		//faz nada
	}

	public void remove(T entity) {
		entities.remove(entity);
	}

	public Iterator<T> iterator() {
		return entities.iterator();
	}
}
