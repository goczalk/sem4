package goczal.klaudia.database.exceptions;

public class InvalidCommandSyntaxException extends GeneralDatabaseException{
	public InvalidCommandSyntaxException(){
		super("ERROR: Invalid command syntax. Type \"help\" if you need command syntax.");
	};
	
	//for classes that inherits
	public InvalidCommandSyntaxException(String message){
		super(message);
	};
}