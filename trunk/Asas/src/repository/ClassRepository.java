package repository;

import java.io.Serializable;
import basic.Class;

public class ClassRepository extends SimpleRepository<Class> implements Serializable{
	private static final long serialVersionUID = 5236538751327550744L;
	
	public int cont = 0;
	
	public ClassRepository() {
		super();
	}
	


}
