package data.writers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import exceptions.WritingException;

public abstract class ExcelWriter<T> implements Writer<T>{
	protected Workbook workbook;
	
	public ExcelWriter(File file) throws WritingException{
		try {
			workbook = WorkbookFactory.create(new FileInputStream(file));
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new WritingException(e,"A planilha selecionada possui formato desconhecido ou inválido.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new WritingException(e, "Erro ao tentar abrir o arquivo '" + file.getName() + "'");
		}
	}
}
