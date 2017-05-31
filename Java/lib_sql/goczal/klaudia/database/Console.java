package goczal.klaudia.database;

import java.util.*;
import goczal.klaudia.database.exceptions.*; //no subpackages in Java

public class Console {
	private Database db;
	private Scanner sc;
	
	private String help =   "read  filename  : reads database info from file \n" +
							"create  tableName (columnType columnName1, columnType columnName2 ...), columnTypes available: char, int, float\n" +
							"select  columnName | *  from  tableName where ... \n" +
							"insert  tableName (columnName1,  columnName2 ...) values (value1, value2)\n" +
							"update  tableName  set columnName1=val1, columnName2=val2 ... where ... \n" +
							"delete  *  from  tableName where ... \n" +
							"exit : exits database\n" + 
							"where columnName[ = | != | <= | >= | like regex | between val1 and val2 ], \n" + 
							"\t regex: _ (one char), % (zero or more char), [a-c] (chars from a to c), [^a-c] (not chars from a to c)";
	
	public Console(String databaseName){
		this.db = new Database(databaseName);
		this.sc = new Scanner(System.in);
	};
	
	//GETTERS
	public String getDatabaseName(){
		return db.getName();
	};
	
	//SETTERS

	//CHECKERS

	//QUERIES

	//ADDITIONAL
	private void printHelp(){
		System.out.println(this.help);
	};
	public boolean input() {
			boolean isExit = false;
		
			String line = this.sc.nextLine();
			String[] arrayLine = line.split(" ",2);
			String command = arrayLine[0].trim();
			String opt = "";
			
			try{
			//if(!command.matches("(\\s*|^exit|^help)")){
				if(command.matches("create|select|insert|update|delete|read")){
					try{
						opt = arrayLine[1].trim();
					}
					catch(ArrayIndexOutOfBoundsException err)
					{
						throw new CommandNoOptionsException();
					}
				};
				
					switch(command) {
					   case "create" :
						  db.create(opt);
						  break;
					   case "select" :
						  db.select(opt);
						  break;
					   case "insert" :
						  db.insert(opt);
						  break;
					   case "update" :
						  db.update(opt);
						  break;
					   case "delete" :
						  db.delete(opt);
						  break;
					   case "read" :
						  db.readFromFile(opt);
						  break;
					   case "exit" :
						  isExit = true;
						  break;
					   case "help" : 
						  this.printHelp();
						  break;
					   default :
					   //TODO change when '\n' not this message
						  throw new InvalidCommandSyntaxException();
					}
				} catch(InvalidCommandSyntaxException e){
					System.out.println(e.getMessage());
				}
			if(!isExit) db.writeToFile();
			return isExit;
	}
}

/*TODO
//TODO SPYTAJ: jak zrobic zeby exceptions byly TYLKO dla package database
//TODO SPYTAJ: czy jesli klasa jest bez przedrostka, a wiec w ramach pakietu to jest sens mowic o jej metodach, ze sa protected??
//metody: public ktokolwiek importujacy? protected - tylko dziedzic i z danego pakietu?
//klasa: private - nikt??

//TODO Actions do osobnego pakietu
//TODO ogarnij private i public i protected
//TODO wyswietl dostepne nazwy tabel! + ich kolumny
//TODO INSERT INTO table_name VALUES (value1, value2, value3, ...);
//TODO alfabetycznie uszereguj
//TODO komunikaty rows affected
//TODO add command drop table
//TODO ignore cases
//TODO skladnia more sql: 'create table', 'insert into'
//TODO better exceptions messages and idea of inheritance
//TODO unittests
//TODO better idea of writing to file and reading from?
//TODO docs

//TODO przemysl TreeMap i interfejs Comparable
//TODO insert i(i, k, k, i) values (1, 2, 3, 4) wsadzi dwa wiersze, a powinien jeden
//TODO InvalidTableName, InvalidColumnName
*/

