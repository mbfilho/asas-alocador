package state.persistence.excelReaders;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import utilities.StringUtil;

public class WorkbookReader {
	private Workbook workbook;
	private Iterator<Row> rows;
	private Row currentRow;
	private DataFormatter cellFormater; 
	private int currentCellNumber;
	
	public WorkbookReader(File file, String sheetName) throws IOException, InvalidFormatException{
		workbook = WorkbookFactory.create(file);
		setCurrentSheet(workbook.getSheet(sheetName));
		cellFormater = new DataFormatter();
	}
	
	private void setCurrentSheet(Sheet newSheet){
		rows = newSheet.iterator();
		goToNextRow();
	}
	
	public void changeSheet(String newSheetName){
		setCurrentSheet(workbook.getSheet(newSheetName));
	}
	
	public void changeSheet(int newSheetNumber){
		setCurrentSheet(workbook.getSheetAt(newSheetNumber));
	}
	
	public void goToNextRow(){
		currentRow = rows.next();
		currentCellNumber = 0;
	}
	
	public String peekString(){
		String val = readString();
		--currentCellNumber;
		return val;
	}
	
	public String readString(){
		Cell theCell = currentRow.getCell(currentCellNumber++);
		return StringUtil.sanitize(cellFormater.formatCellValue(theCell));
	}
	
	public int readInt(int defaultValue){
		try{
			return readInt();
		}catch(NumberFormatException nfe){
			return defaultValue;
		}
	}
	
	public int readInt(){
		return Integer.parseInt(readString());
	}
	
	public double readDouble() {
		Cell currentCell = currentRow.getCell(currentCellNumber++);
		if(currentCell == null || currentCell.getCellType() != Cell.CELL_TYPE_NUMERIC) 
			return Double.NaN;
		return currentCell.getNumericCellValue();
	}
}
