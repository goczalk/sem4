package goczal.klaudia.database.exceptions;

public class ColumnAlreadyExistsException extends ObjectAlreadyExistsException{
	public ColumnAlreadyExistsException(){
		super("ERROR: Column already exists.");
	};
	
	public ColumnAlreadyExistsException(String colName){
		super("ERROR: Column with name '" + colName + "' already exists.");
	};
}