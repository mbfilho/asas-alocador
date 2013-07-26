import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Vector;


public class Main implements Iterable<Integer> {
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		Main x = new Main();
		for(Integer h : x){
			
		}
	}

	public Iterator<Integer> iterator() {
		Vector<Integer> x = new Vector<Integer>();
		x.add(1); x.add(2); x.add(3);
		System.out.println("Sim");
		return x.iterator();
	}
}
