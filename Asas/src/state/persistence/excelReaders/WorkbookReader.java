package state.persistence.excelReaders;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkbookReader {
	private XSSFWorkbook workbook;
	private Iterator<Row> rows;
	private Row currentRow;
	private Iterator<Cell> cells;
	
	public WorkbookReader(String fileName, String sheetName) throws IOException{
		FileInputStream input = new FileInputStream(new File(fileName));
		workbook = new XSSFWorkbook(input);
		
		for(int i = 0; i < workbook.getNumberOfSheets(); ++i){
			System.out.println("> " + workbook.getSheetName(i));
		}
		setCurrentSheet(workbook.getSheet(sheetName));
	}
	
	private void setCurrentSheet(XSSFSheet newSheet){
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
		cells = currentRow.iterator();
	}
	
	public String readValue(){
		Cell currentCell = cells.next();
		
		switch(currentCell.getCellType()){
			case Cell.CELL_TYPE_BLANK:
				return "";
			case Cell.CELL_TYPE_BOOLEAN:
				return currentCell.getBooleanCellValue() + "";
			case Cell.CELL_TYPE_NUMERIC:
				return currentCell.getNumericCellValue() + "";
			case Cell.CELL_TYPE_STRING:
				return currentCell.getStringCellValue();
		}
		return null;
	}
	
	public String readString(){
		return readValue();
	}
	
	public int readInt(int defaultValue){
		try{
			return (int) Double.parseDouble(readValue());
		}catch(NumberFormatException nfe){
			return defaultValue;
		}
	}
	
	public int readInt(){
		return (int) Double.parseDouble(readValue());
	}

	
	public double readDouble() {
		Cell currentCell = cells.next();
		if(currentCell.getCellType() != Cell.CELL_TYPE_NUMERIC) return Double.NaN;
		return currentCell.getNumericCellValue();
	}
	
}
