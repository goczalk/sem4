package vector;

public class InvalidVectorDataException extends Exception{
	public InvalidVectorDataException(){
		super("Exception. Ints should be given to Vector");
	};
}