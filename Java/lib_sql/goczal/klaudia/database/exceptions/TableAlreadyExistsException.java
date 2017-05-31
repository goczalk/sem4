package goczal.klaudia.database.exceptions;

public class TableAlreadyExistsException extends ObjectAlreadyExistsException{
	public TableAlreadyExistsException(){
		super("ERROR: Table already exists.");
	};
	
	public TableAlreadyExistsException(String tabName){
		super("ERROR: Table with name '" + tabName + "' already exists.");
	};
}