//javac Main.java goczal/klaudia/database/*.java goczal/klaudia/database/exceptions/*.java

import java.util.*;
import goczal.klaudia.database.Console;

//TODO Database name restriction?
class Main {
	public static void main(String[] args) {
		boolean isExit = false;
	
		System.out.println("Welcome to database");
		Scanner sc = new Scanner(System.in);
		System.out.println("Provide your database name: ");
		String dbName = sc.nextLine().trim() + "DB";
		System.out.println("Your database data will be saved in file: " + dbName);
		
		System.out.println("Type \"help\" if you need command syntax.");
		Console console = new Console(dbName);
		
		while(!isExit){
			isExit = console.input();
		}
		
		//TODO - klamstwo, no file created
		/*Your database data will be saved in file: exitDB
		Type "help" if you need command syntax.
		exit
		Your data have been saved to file: exitDB
		*/
		System.out.println("Your data have been saved to file: " + console.getDatabaseName());
	}
}
