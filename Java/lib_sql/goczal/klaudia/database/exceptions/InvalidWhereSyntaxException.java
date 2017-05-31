package goczal.klaudia.database.exceptions;

public class InvalidWhereSyntaxException extends InvalidCommandSyntaxException{
	public InvalidWhereSyntaxException(){
		super("ERROR: Invalid 'where' statement syntax.");
	};
	
	public InvalidWhereSyntaxException(String message){
		super(message);
	};
}