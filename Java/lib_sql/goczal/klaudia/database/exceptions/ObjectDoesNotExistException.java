package goczal.klaudia.database.exceptions;

public class ObjectDoesNotExistException extends GeneralDatabaseException{
	//String messagePart1 = "Object with name '", messagePart3 = "' does not exist."
	public ObjectDoesNotExistException(){
		super();
	};
	
	public ObjectDoesNotExistException(String message){
		super(message);
	};
}