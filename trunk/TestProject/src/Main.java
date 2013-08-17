import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class Main implements Iterable<Integer> {
	
	
	public void faz() throws InvalidFormatException, IOException{
		String fileLocation = "C:\\Users\\Marcio Barbosa\\Dropbox\\2013.1\\tg\\implementacao\\dados\\Alocacao2013-2.horario.xlsm";
		Workbook book = WorkbookFactory.create(new File(fileLocation));
		Sheet s = book.getSheet("Alocacao2013-2");
		Row r = s.getRow(51);
		for(int i = 0; i < 10; ++i){
			System.out.print(r.getCell(i) + "["+ (r.getCell(i).getCellType() == Cell.CELL_TYPE_BLANK) + "]|");
		}
		System.out.println();
		for(Cell c : r){
			System.out.print(c + "|");
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, InvalidFormatException {
		char ch[] = {'\t', '\r', ' ', '\n', };
		
		for(char c : ch)
			System.out.println(Character.isWhitespace(c) + " x " + Character.isSpace(c));
	}

	public Iterator<Integer> iterator() {
		Vector<Integer> x = new Vector<Integer>();
		x.add(1); x.add(2); x.add(3);
		System.out.println("Sim");
		return x.iterator();
	}
}
