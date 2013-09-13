package data.writers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public abstract class ExcelWriter<T> implements Writer<T>{
	private int cellNumber;
	private Row currentRow;
	private Sheet currentSheet;
	
	public ExcelWriter(Workbook workbook, String sheetName){
		currentSheet = workbook.getSheet(sheetName);
	}
	
	protected void goToRow(int row){
		currentRow = currentSheet.getRow(row);
		cellNumber = 0;
	}
	
	protected Cell getCell(){
		Cell cell = currentRow.getCell(cellNumber);
		if(cell == null)
			cell = currentRow.createCell(cellNumber);
		++cellNumber;
		return cell;
	}
	
	protected void writeField(String value){
		Cell cell = getCell();
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}
	
	protected void writeField(double value){
		Cell cell = getCell();
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}
	
	protected void ignoreField(String fieldIgnored){
		ignoreField();
	}
	
	protected void ignoreField(){
		++cellNumber;
	}
}
