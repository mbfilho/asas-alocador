package data.writers;

import exceptions.WritingException;

public interface Writer <T>{
	public void Write(T argument) throws WritingException;
}
