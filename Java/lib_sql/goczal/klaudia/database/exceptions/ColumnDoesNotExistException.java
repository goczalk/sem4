package goczal.klaudia.database.exceptions;

public class ColumnDoesNotExistException extends ObjectDoesNotExistException{
	public ColumnDoesNotExistException(){
		super("ERROR: Column does not exist.");
	};
	
	public ColumnDoesNotExistException (String colName){
		super("ERROR: Column with name '" + colName + "' does not exist.");
	};
}