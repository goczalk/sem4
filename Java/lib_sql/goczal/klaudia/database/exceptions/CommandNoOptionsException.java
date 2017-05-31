package goczal.klaudia.database.exceptions;

public class CommandNoOptionsException extends InvalidCommandSyntaxException{
	public CommandNoOptionsException(){
		super("ERROR: No options given. Type \"help\" if you need command syntax.");
	};
	
	public CommandNoOptionsException(String message){
		super(message);
	};
}