package logic.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import data.configurations.ExcelPreferences;
import data.persistentEntities.FileHash;
import data.persistentEntities.Professor;
import data.persistentEntities.Class;
import data.writers.ExcelClassWriter;
import data.writers.ExcelProfessorWriter;
import data.writers.Writer;
import exceptions.WritingException;

public class ExcelWritingService {
	private Workbook workbook;
	private File openedFile;
	private Writer<Professor> profWriter;
	private Writer<Class> classWriter;
	
	public void open(File excelFile){
		this.openedFile = excelFile;
		
		try {
			FileInputStream in = new FileInputStream(excelFile);
			workbook = WorkbookFactory.create(in);
			in.close();
			ExcelPreferences prefs = StateService.getInstance().getCurrentState().excelPrefs;
			classWriter = new ExcelClassWriter(workbook, prefs);
			profWriter = new ExcelProfessorWriter(workbook, prefs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void save() throws WritingException{
		FileOutputStream out;
		try {
			out = new FileOutputStream(openedFile);
			workbook.write(out);
			out.close();
			StateService.getInstance().updateExcelHash();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new WritingException(e, 
					String.format("Não foi possível abrir o arquivo '%s'.\n" +
							" Certifique-se de que ele não esteja aberto por outra aplicação.", openedFile.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
			throw new WritingException(e, 
					String.format("Ocorreu um erro durante a escrita do arquivo '%s'.\n" +
							"Certifique-se de que ele não esteja aberto por outra aplicação.", openedFile.getAbsolutePath()));
		}
	}
	
	public void writeProfessorOnly(){
		ProfessorService profs = ProfessorService.createServiceFromCurrentState();
		for(Professor p : profs.all()){
			try {
				profWriter.Write(p);
			} catch (WritingException e) {e.printStackTrace();}
		}
	}
	
	public void writeClassesOnly(){
		ClassService classService = ClassService.createServiceFromCurrentState();
		for(Class c : classService.all()){
			try {
				classWriter.Write(c);
			} catch (WritingException e) {e.printStackTrace();}
		}
	}
	
	public void writeAll(){
		writeProfessorOnly();
		writeClassesOnly();
	}
	
	public boolean isFileCompatible(File target){
		FileHash hash = new FileHash(target);
		FileHash read = StateService.getInstance().getCurrentState().excelFileHash;
		if(!read.isSuccesfullyCalculated() || !hash.isSuccesfullyCalculated())
			return true;
		
		return read.equals(hash);
	}
}
