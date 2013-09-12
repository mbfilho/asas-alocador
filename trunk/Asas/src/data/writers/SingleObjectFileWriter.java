package data.writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import exceptions.WritingException;

public class SingleObjectFileWriter<T> implements Writer<T>{
	private File theFile;
	
	public SingleObjectFileWriter(String filePath){
		this(new File(filePath));
	}
	
	public SingleObjectFileWriter(File file){
		theFile = file;
	}
	
	@Override
	public void Write(T argument) throws WritingException {
		ObjectOutputStream out = null;
		
		try {
			out = new ObjectOutputStream(new FileOutputStream(theFile));
			out.writeObject(argument);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new WritingException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new WritingException(e);
		}finally{
			if(out != null){
				try {out.close();} 
				catch (IOException e) {}
			}
		}
	}

}
