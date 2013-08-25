package data.readers;

import data.DataValidation;
import data.NamedEntity;
import data.repository.Repository;
import exceptions.InvalidInputException;

public interface DataReader<T extends NamedEntity> {
	DataValidation<Repository<T>> read() throws InvalidInputException; 
}
