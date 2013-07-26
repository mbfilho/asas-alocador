package data;

import basic.DataValidation;
import basic.NamedEntity;
import exceptions.InvalidInputException;

public interface DataReader<T extends NamedEntity> {
	DataValidation<Repository<T>> read() throws InvalidInputException; 
}
