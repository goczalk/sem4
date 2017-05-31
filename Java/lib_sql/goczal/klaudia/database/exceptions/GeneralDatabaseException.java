package goczal.klaudia.database.exceptions;

public class GeneralDatabaseException extends Exception{
	public GeneralDatabaseException(){
		super();
	};
	
	public GeneralDatabaseException(String message){
		super(message);
	};
}