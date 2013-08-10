package warnings;

import basic.Class;

public class ClassWithoutProfessorWarning extends Warning{
	private Class theClass;
	
	public ClassWithoutProfessorWarning(Class c){
		theClass = c;
	}
	
	public Class getTheClass(){
		return theClass;
	}
	
	public String getMessage() {
		return theClass.completeName();
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof ClassWithoutProfessorWarning)) return false;
		ClassWithoutProfessorWarning other = (ClassWithoutProfessorWarning) obj;
		return theClass == other.getTheClass();
	}
}
