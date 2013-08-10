package warnings;

import warnings._Warning;
import basic.Class;

public class ClassWithoutSlotWarning extends _Warning {

	private Class theClass;
	
	public ClassWithoutSlotWarning(Class c){
		theClass = c;
	}
	
	public String getMessage() {
		return theClass.completeName();
	}
	
	public Class getTheClass(){
		return theClass;
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof ClassWithoutSlotWarning)) return false;
		ClassWithoutSlotWarning other = (ClassWithoutSlotWarning) obj;
		
		return theClass == other.getTheClass();
	}

}
