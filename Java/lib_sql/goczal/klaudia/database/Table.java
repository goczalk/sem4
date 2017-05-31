package goczal.klaudia.database;

import java.util.*;
import goczal.klaudia.database.exceptions.*;

class Table{
	private ArrayList<Column> arrayColumns;
	private String name;

	private class Where{
		//TODO WHERE NOT, OR, ADD
		//TODO IN(val1, val2)
		//TODO wygodniejsze rozpoznanie na null
		private List<Integer> indexes;

		private Where(String opt) throws InvalidWhereSyntaxException, ColumnDoesNotExistException, BadDatatypeException{
			indexes = new ArrayList<Integer>();
			opt = opt.trim();
			Action action = null;
			String searchedCol = "";
			boolean notLike = true;
			boolean nullGiven = false;

			//TODO code optimalization not possible?
			try{
				if(opt.contains("!=")){
					String[] listS = opt.split("!=");
					searchedCol = listS[0].trim();
					if(listS[1].trim().equals("null")) nullGiven = true;
					action = new NotEqual(listS[1].trim());
				}
				else if(opt.contains(">")){
					String[] listS = opt.split(">");
					searchedCol = listS[0].trim();
					
					if (opt.contains("=")){
						listS[1] = listS[1].replace("=", "");
						if(listS[1].trim().equals("null")) nullGiven = true;
						action = new BiggerThan(listS[1].trim(), true);
					}
					else{
						if(listS[1].trim().equals("null")) nullGiven = true;
						action = new BiggerThan(listS[1].trim());
					}
				} 
				else if(opt.contains("<")){
					String[] listS = opt.split("<");
					searchedCol = listS[0].trim();
					
					if (opt.contains("=")){
						listS[1] = listS[1].replace("=", "");
						if(listS[1].trim().equals("null")) nullGiven = true;
						action = new SmallerThan(listS[1].trim(), true);
					}
					else{
						if(listS[1].trim().equals("null")) nullGiven = true;
						action = new SmallerThan(listS[1].trim());
					}
				} 
				else if (opt.contains("=")){
					String[] listS = opt.split("=");
					searchedCol = listS[0].trim();
					if(listS[1].trim().equals("null")) nullGiven = true;
					action = new Equal(listS[1].trim());
				}
				else if(opt.contains("between")){
					String[] listS = opt.split("between");
					searchedCol = listS[0].trim();
					
					String[] listSS = listS[1].split("and");
					if(listSS[0].trim().equals("null") || listSS[1].trim().equals("null")) nullGiven = true;
					action = new Between(listSS[0].trim(), listSS[1].trim());
				}
				else if(opt.contains("like")){
				String[] listS = opt.split("like");
				searchedCol = listS[0].trim();
				action = new Like(listS[1].trim());
				notLike = false;
				}
				else throw new InvalidWhereSyntaxException();
			} catch(ArrayIndexOutOfBoundsException e){
				throw new InvalidWhereSyntaxException();
			}
		
			try{
				checkIfColsInArrayColumnsList(new String[]{searchedCol});
			}catch(ColumnDoesNotExistException e){
				throw new ColumnDoesNotExistException(searchedCol);
			}
			boolean valIsNum = false;
			for(Column colArr : Table.this.arrayColumns){
				if(searchedCol.equals(colArr.getName())){
					if(colArr.getType().equals("Integer") || colArr.getType().equals("Float")) valIsNum = true;
					for(int i=0; i < colArr.getSize(); i++){
						String val = colArr.getValueWithIndex(i);
						if(valIsNum && !(val.equals("null")) && notLike && !nullGiven){
							try{
								Float num = Float.parseFloat(val);
								if(action.execute(num)) indexes.add(i);
							}catch(NumberFormatException e){
								throw new BadDatatypeException("float or int");
							}
						}
						else
							if(action.execute(val)) indexes.add(i);
					}
				}
			}
		};
		
		//GETTERS
		public List<Integer> getIndexesList(){
			return indexes;
		};
	};
	
	
	public Table(){
		//for sake of class Where
		arrayColumns = new ArrayList<Column>();
	};
	public Table(String opt) throws InvalidCommandSyntaxException{
		try{
			arrayColumns = new ArrayList<Column>();
			String[] arrayS = opt.split("\\(");
			this.name = arrayS[0].trim();

			if(!arrayS[1].endsWith(")")) throw new InvalidCommandSyntaxException();
			
			String[] listCols = arrayS[1].replace(")", "").split(",");

			for(String colOpt : listCols){
				colOpt = colOpt.trim();
				//TODO create m(int k val ) przejdzie
				String colName = colOpt.split(" ")[1].trim();
				String[] tempCol = new String[]{colName};
				try{
					checkIfColsInArrayColumnsList(tempCol);
					throw new ColumnAlreadyExistsException(colName);
				} catch(ColumnDoesNotExistException e){
					try{
						Column col = new Column(colOpt);
						arrayColumns.add(col);
					} catch(BadDatatypeException err){
						System.out.println(err.getMessage());
						System.out.println("Omitting column with name '" + colName + "'.");
					}
				} catch(ColumnAlreadyExistsException e){
					System.out.println(e.getMessage());
				}
			}
		} catch(ArrayIndexOutOfBoundsException e){
			throw new InvalidCommandSyntaxException();
		}
	};
	
	
	//GETTERS
	private String[] getListOfAllColumns(){
		String[] allCols = new String[arrayColumns.size()];
		int i = 0;
		for(Column col : arrayColumns){
			allCols[i] = col.getName();
			i++;
		}
		return allCols;
	};
	public String getCsvString(){
		String str = this.getName();
		for(Column col : arrayColumns){
			str += col.getCsvString();
		};
		str += "\n";
		return str;
	};
	public String getName(){
		return this.name;
	};
	public int getRowsNum(){
		return arrayColumns.get(0).getSize();
	};
	
	//SETTERS
	
	//CHECKERS
	private void checkIfColsInArrayColumnsList(String[] cols) throws ColumnDoesNotExistException{
		for(String col : cols){
			boolean found = false;
			for(Column colArray : arrayColumns){
				//TODO wywal trim gdy bedzie dzialalo trimAllElements
				if(col.trim().equals(colArray.getName())){
					found = true;
				}
			}
			if (found == false){
				throw new ColumnDoesNotExistException(col);
			}
		}
	};
	private boolean checkIfWhere(String opt){
		//TODO matches bo regexem zeby CAPITALAMI przyjal WHERE
		//if (opt.matches(".*where.*")) System.out.println("STH");
		if (opt.contains("where")) return true;
		else return false;

		//TODO why not working??
		//return ((opt.contains("where")) ? true : false);
	};
	
	//QUERIES
	public void delete(String opt) throws InvalidCommandSyntaxException{
		List<Integer> indexes = new ArrayList<Integer>();
		for(int k=0; k < getRowsNum();k++)
			indexes.add(k);
		
		try{
			if(checkIfWhere(opt)){
				try{
					String[] listOpt = opt.split("where");
					Where where = new Where(listOpt[1]);
					indexes = where.getIndexesList();
					opt =  listOpt[0].trim();
				}catch(ArrayIndexOutOfBoundsException e){
					throw new InvalidWhereSyntaxException();
				}
			}

			for(Column col : arrayColumns){
				col.delete(indexes);
			}
		}catch(BadDatatypeException e){
			System.out.println(e.getMessage());
		}catch(ColumnDoesNotExistException e){
				System.out.println(e.getMessage());
				System.out.println("Aborting query...");
		}
	};
	public void insert(String opt) throws InvalidCommandSyntaxException{
		//TODO po updatcie niech same nulle zostana
		//TODO przechodzi bez echa: insert m(i) values (1, 2); insert m(i, f) values (1, 2, 3);
		//TODO w pliku dodatkowa wartosc tez ignorowana, chyba, ze pierwsza
		try{
			if(!opt.endsWith(")")) throw new InvalidCommandSyntaxException();
			
			String[] listColVal = opt.replace(")", "").replace("(", "").split("values");
			String[] listCols = listColVal[0].split(",");		
			String[] listVals = listColVal[1].split(",");

			//TODO always trim! <- do working fun
			//listCols = trimAllElements(listCols);
			//listVals = trimAllElements(listVals);

			checkIfColsInArrayColumnsList(listCols);
			
			//TODO check number of args in cols and vals
		
			TreeMap<String, String> mapColVal = new TreeMap();
			for(int j=0; j < listCols.length; j++){
				//TODO delete when trimm func working
				mapColVal.put(listCols[j].trim(), listVals[j].trim());
			}
			
			//debug
			//print TreeMap
			/*
			Set set = mapColVal.entrySet();
			Iterator t = set.iterator();
			while(t.hasNext()) {
			 Map.Entry me = (Map.Entry)t.next();
			 System.out.print(me.getKey() + ": ");
			 System.out.println(me.getValue());
			}
			*/
			
			//TODO should for be a func??
			for(Column colArray : arrayColumns){
				boolean found = false;
				for(String col : listCols){
					//TODO delete when trimm func working
					col = col.trim();
					if(col.equals(colArray.getName())){
						found = true;
						try{
							colArray.insert(mapColVal.get(col));
						} catch (BadDatatypeException e){
							System.out.println(e.getMessage());
						}
					}
				}
				if(!found){
						try{
							colArray.insert("NULL");
						} catch (BadDatatypeException e){
							System.out.println(e.getMessage());
						}
				}
			}
		} catch(ArrayIndexOutOfBoundsException e){
			throw new InvalidCommandSyntaxException();
		} catch(ColumnDoesNotExistException e){
			System.out.println(e.getMessage());
			System.out.println("Aborting query...");
		}
	};
	public void select(String optCols, String opt) throws InvalidCommandSyntaxException{
		//TODO czy ArrayLista z indexes nie powinna bym ogolniej dostepna i clearowana??
		if(arrayColumns.size() > 0){		
			List<Integer> indexes = new ArrayList<Integer>();
			for(int k=0; k < this.getRowsNum();k++)
				indexes.add(k);

			try{
				if(checkIfWhere(opt)){
					try{
						String[] listOpt = opt.split("where");
						Where where = new Where(listOpt[1]);
						indexes = where.getIndexesList();
						opt =  listOpt[0].trim();
					}catch(ArrayIndexOutOfBoundsException e){
						throw new InvalidWhereSyntaxException();
					}
				}
			
				String[] listCols = optCols.split(",");
								
				if(listCols[0].equals("*")) listCols = this.getListOfAllColumns();
				else checkIfColsInArrayColumnsList(listCols);

				for(int i=0; i <= this.getRowsNum(); i++){
					if(indexes.contains(i-1) || i == 0){
						String line = "";
						for(String col : listCols){
							for(Column colArray : arrayColumns){
								//TODO delete when trimm func working
								if(col.trim().equals(colArray.getName())){
									if(i == 0) 
										line += colArray.getName();
									else {
											line += colArray.getValueWithIndex(i-1);
									}
									line += "\t\t";
								}
							}
						}
						System.out.println(line);
					}
				}
			}catch(BadDatatypeException e){
				System.out.println(e.getMessage());
			}catch(ColumnDoesNotExistException e){
				System.out.println(e.getMessage());
				System.out.println("Aborting query...");
			}
		}
	};
	public void update(String opt) throws InvalidCommandSyntaxException{
		List<Integer> indexes = new ArrayList<Integer>();
		for(int k=0; k < getRowsNum();k++)
			indexes.add(k);
		
		try{
			if(checkIfWhere(opt)){
				try{
					String[] listOpt = opt.split("where");
					Where where = new Where(listOpt[1]);
					indexes = where.getIndexesList();
					opt =  listOpt[0].trim();
				}catch(ArrayIndexOutOfBoundsException e){
					throw new InvalidWhereSyntaxException();
				}
			}
			
			String[] listColsValsEq = opt.split(",");
			ArrayList<String> listCols = new ArrayList<String>();
			ArrayList<String> listVals = new ArrayList<String>();
			
			for(int j=0; j < listColsValsEq.length; j++){
				//TODO delete when trimm func working
				listCols.add(listColsValsEq[j].split("=")[0].trim());
				listVals.add(listColsValsEq[j].split("=")[1].trim());
			};
			
			TreeMap<String, String> mapColVal = new TreeMap();
			for(int i=0; i < listCols.size(); i++){
				//TODO delete when trimm func working
				mapColVal.put(listCols.get(i), listVals.get(i));
			};
			
			/*
			Originally the code above used new String[list.size()].
			However, due to JVM optimizations, using new String[0] is better now
			*/

			checkIfColsInArrayColumnsList(listCols.toArray(new String[0]));
			
			//debug
			//print TreeMap
			/*
			Set set = mapColVal.entrySet();
			Iterator t = set.iterator();
			while(t.hasNext()) {
			 Map.Entry me = (Map.Entry)t.next();
			 System.out.print(me.getKey() + ": ");
			 System.out.println(me.getValue());
			}
			*/
			
			//TODO should for be a func?? same in insert??
			for(String col : listCols){
				for(Column colArray : arrayColumns){
					if(col.equals(colArray.getName())){
						try{
							colArray.update(mapColVal.get(col), indexes);
						} catch(BadDatatypeException e){
							System.out.println(e.getMessage());
						}
					}
				}
			}
		} catch(ArrayIndexOutOfBoundsException e){
			throw new InvalidCommandSyntaxException();
		}catch(BadDatatypeException e){
			System.out.println(e.getMessage());
		}catch(ColumnDoesNotExistException e){
			System.out.println(e.getMessage());
			System.out.println("Aborting query...");
		}
	};
	
	//ADDITIONAL
	public void createFromCsv(String csvLine) throws InvalidFileSyntaxException{
		try{
			String[] arrayS = csvLine.split(";");
			this.name = arrayS[0].trim();

			if(arrayS.length == 1) throw new InvalidFileSyntaxException(); //if no ';' at line
			
			for(int i=1; i < arrayS.length; i++){
				Column col = new Column();
				col.createFromCsv(arrayS[i]);
				arrayColumns.add(col);
			};
		}catch(ArrayIndexOutOfBoundsException e){
			throw new InvalidFileSyntaxException();
		}
	};
	private String[] trimAllElements(String[] elements){
		//TODO Make it work; references
		String[] trimmedElements = new String[elements.length];
		System.arraycopy(elements, 0, trimmedElements, 0, elements.length);
		
		for(String element : trimmedElements){
			element = element.trim();
		}
		return trimmedElements;
	};

}