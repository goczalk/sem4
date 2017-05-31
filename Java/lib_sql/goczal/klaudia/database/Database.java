package goczal.klaudia.database;

import java.util.*;
import java.io.*;
import goczal.klaudia.database.exceptions.*;

class Database {
	private String name;
	private ArrayList<Table> arrayTables = new ArrayList<Table>();

	public Database(String databaseName){
		this.name = databaseName;
	}

	//GETTERS
	private int getIndexOfTableWithName(String searchedTable) throws TableDoesNotExistException {
		for(int i=0; i < arrayTables.size(); i++){
			if(arrayTables.get(i).getName().equals(searchedTable))
				return i;
		}
		throw new TableDoesNotExistException(searchedTable);
	};
	public String getName(){
		return this.name;
	};
	
	//SETTERS
	
	//CHECKERS
	
	//QUERIES
	public void create(String opt) throws InvalidCommandSyntaxException{
		try{
			String searchedTable = opt.split("\\(")[0].trim();
			boolean alreadyExists = false;

			getIndexOfTableWithName(searchedTable);
			throw new TableAlreadyExistsException(searchedTable);
		} catch (TableDoesNotExistException e){
			Table table = new Table(opt);
			arrayTables.add(table);
		} catch (TableAlreadyExistsException e){
			System.out.println(e.getMessage());
		};
	};
	public void delete(String opt) throws InvalidCommandSyntaxException{
		try{
			String[] arrayS = opt.split("from ",2);
			
			if(arrayS.length == 1) throw new InvalidCommandSyntaxException();
			
			String options = arrayS[arrayS.length-1].trim();
			String searchedTable = ""; 

			if(options.contains("where")){
				searchedTable = options.split("where")[0].trim();
				String[] afterFrom = options.split(searchedTable,2);
				options = afterFrom[afterFrom.length-1].trim();
			}
			else {
				searchedTable = options;
			}
			int index = getIndexOfTableWithName(searchedTable);
			arrayTables.get(index).delete(options);
		} catch (TableDoesNotExistException e){
			System.out.println(e.getMessage());
		};
	};
	public void insert(String opt) throws InvalidCommandSyntaxException{
		try{
			String[] arrayS = opt.split("\\(",2);
			String searchedTable = arrayS[0].trim();

			int index = getIndexOfTableWithName(searchedTable);
			arrayTables.get(index).insert(arrayS[1].trim());
		} catch(ArrayIndexOutOfBoundsException e){
			throw new InvalidCommandSyntaxException();
		} catch (TableDoesNotExistException e){
			System.out.println(e.getMessage());
		};
	};	
	public void select(String opt) throws InvalidCommandSyntaxException{
	//TODO select and delete and update korzystaja z tego samego kawalka!!! <- oprocz wywolania funkcji
	//TODO inny sposob znalezienia where po from tableName
		try{
			String[] arrayS = opt.split("from ",2);
			
			if(arrayS.length == 1) throw new InvalidCommandSyntaxException();
			
			String options = arrayS[arrayS.length-1].trim();
			String searchedTable = ""; 

			if(options.contains("where")){
				searchedTable = options.split("where")[0].trim();
				String[] afterFrom = options.split(searchedTable,2);
				options = afterFrom[afterFrom.length-1].trim();
			}
			else {
				searchedTable = options;
			}
			int index = getIndexOfTableWithName(searchedTable);
			arrayTables.get(index).select(arrayS[0].trim(), options);
		} catch (TableDoesNotExistException e){
			System.out.println(e.getMessage());
		};
	};
	public void update(String opt) throws InvalidCommandSyntaxException{
		try{
			String[] arrayS = opt.split(" set ",2);
			
			if(arrayS.length == 1) throw new InvalidCommandSyntaxException();
			
			String searchedTable = arrayS[0].trim();

			int index = getIndexOfTableWithName(searchedTable);
			arrayTables.get(index).update(arrayS[1].trim());
		} catch(ArrayIndexOutOfBoundsException e){
			throw new InvalidCommandSyntaxException();
		} catch (TableDoesNotExistException e){
			System.out.println(e.getMessage());
		};
	};
	
	//ADDITIONAL
	public void readFromFile(String opt) throws InvalidCommandSyntaxException{
		//TODO if error in file in second line, first line will be read
		Scanner fileIn = null;
		try{
			String filename = opt.trim();
			fileIn = new Scanner(new File(filename));
			String line = new String();
			
			while(fileIn.hasNextLine()){
				line = fileIn.nextLine();
				Table table = new Table();
				table.createFromCsv(line);
				arrayTables.add(table);
			}
			fileIn.close();
		}catch(IOException ioe){
			System.err.println("ERROR: IOException: " + ioe.getMessage());
		}catch(InvalidFileSyntaxException e){
			System.err.println(e.getMessage());
			System.err.println("Database cannot be read.");
		}finally{
			try{
				fileIn.close();
			}catch(NullPointerException e){}
		}
		
		for(Table table : arrayTables){
			System.out.println("Table name: " + table.getName());
			this.select("* from " + table.getName());
		}
	};	
	public void writeToFile(){
	//TODO czy nie ma zadnego przypadku, ze nie zamknie pliku???
	//TODO bardzo brzydie zeby na stringu?? przekazywac dalej sterowanie plikiem?
		try{
			String filename = this.getName();
			FileWriter fw = new FileWriter(filename,false);

			for(Table table : arrayTables)
			{
				fw.write(table.getCsvString());
			}
			
			fw.close();
		}
		catch(IOException ioe)
		{
			System.err.println("IOException: " + ioe.getMessage());
		}
	};
};
