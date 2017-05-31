package goczal.klaudia.database.exceptions;

public class TableDoesNotExistException extends ObjectDoesNotExistException{
	public TableDoesNotExistException(){
		super("ERROR: Table does not exist.");
	};
	
	public TableDoesNotExistException(String tabName){
		super("ERROR: Table with name '" + tabName + "' does not exist.");
	};
}