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

import javax.print.attribute.standard.SheetCollate;
import javax.swing.JOptionPane;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.eventusermodel.XSSFReader;



public class Main implements Iterable<Integer> {
	public static String fileLocation = "C:\\Users\\Marcio Barbosa\\Dropbox\\2013.1\\tg\\implementacao\\dados\\Alocacao2013-2.horario.xlsm";
	
	
	public static void faz() throws IOException, InvalidFormatException{
		Workbook book = WorkbookFactory.create(new File(fileLocation));
		
		Sheet s = book.getSheet("Alocacao2013-2");
		for(Row r : s){
			for(Cell c : r)
				System.out.print(c + "|");
			System.out.println();
		}
		
		JOptionPane.showMessageDialog(null, "Lido");
	}
	
	public static void faz2() throws IOException, OpenXML4JException{
		XSSFReader reader = new XSSFReader(OPCPackage.open(new File(fileLocation)));
		System.out.println(reader.getSheet("Alocacao2013-2"));
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, OpenXML4JException {
	}

	public Iterator<Integer> iterator() {
		Vector<Integer> x = new Vector<Integer>();
		x.add(1); x.add(2); x.add(3);
		System.out.println("Sim");
		return x.iterator();
	}
}
