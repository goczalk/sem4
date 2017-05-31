package goczal.klaudia.database.exceptions;

public class InvalidFileSyntaxException extends GeneralDatabaseException{
	public InvalidFileSyntaxException(){
		super("ERROR: Invalid syntax within the file.");
	};
	
	public InvalidFileSyntaxException(String message){
		super(message);
	};
}