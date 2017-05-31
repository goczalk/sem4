package goczal.klaudia.database.exceptions;

public class BadDatatypeException extends InvalidCommandSyntaxException{
	public BadDatatypeException(){
		super("ERROR: Bad type of data provided.");
	};
	
	public BadDatatypeException(String availableTypes){
		super("ERROR: Bad type of data provided. Should be: " + availableTypes + ".");
	};
}