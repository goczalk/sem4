package goczal.klaudia.database.exceptions;

public class ObjectAlreadyExistsException extends GeneralDatabaseException{
	public ObjectAlreadyExistsException(){
		super();
	};
	
	public ObjectAlreadyExistsException(String message){
		super(message);
	};
}