package goczal.klaudia.database;

import java.util.*;
import goczal.klaudia.database.exceptions.*;

//TODO toString?
class Column{
	private ArrayList<Field> arrayFields;
	private String name;
	private String type;

	public Column(){
		arrayFields = new ArrayList<Field>();
	};
	public Column(String opt) throws BadDatatypeException{
		String[] arrayS = opt.split(" ");
		type = arrayS[0];
		this.name = arrayS[1];

		switch(type) {
			case "char" :
				this.type = "String";
				break;
			case "int" :
				this.type = "Integer";
				break;
			case "float" :
				this.type = "Float";
				break;
			default: 
				throw new BadDatatypeException("char, int or float");
		};
		arrayFields = new ArrayList<Field>();
	};
	
	//GETTERS
	public String getCsvString(){
		String str = ";" + this.getName() + "," + this.getType();
		for(Field f : arrayFields){
			str += "," + f.getValue();
		}
		return str;
	};
	public String getName(){
		return this.name;
	};
	public int getSize(){
		return arrayFields.size();
	};
	public String getType(){
		return this.type;
	};
	public String getValueWithIndex(int i){
	//getClass() of Field.value?? <- bedzie ciezko bo generycznie trzeba by jeszcze w Table!
	//chyba, ze nie kazac mu przypisywac wartosci??
	//TODO zapis/print wiec moze zwracac Stringa, normalnie powinno rozrozniac kontent bo dzialania!
	//TODO a co z null??
		return String.valueOf(arrayFields.get(i).getValue());
	//public  <T> T getValueWithIndex(int i, /*Class<T>*/T type){ 
	//	return type.cast(arrayFields.get(i).getValue());
	};
	
	//SETTERS
	private <T> void setValForIndexes(T val, List<Integer> indexes, boolean ifNull){
		for(int i=0; i < indexes.size(); i++){
			arrayFields.get(indexes.get(i)).setValue(val, ifNull);
		};	
	};
	
	//QUERIES
	public void delete(List<Integer> indexes){
		Collections.sort(indexes);
		for(int i=indexes.size()-1; i >= 0; i--){
			this.arrayFields.remove(indexes.get(i).intValue());
		}
	};
	public void insert(String val) throws BadDatatypeException{
		//TODO mozne nie na switch case ->po this.type
		//TODO insert m(i, f) values (144, kkk) ustawi i=144, f=null
		//TODO pozwol mu przekazac Stringa o wartosci NULL/null
		boolean ifNull = false;
		if(val.equals("NULL") || val.equals("null")) ifNull = true; //...util.Objects.NotNull()
		
		//TODO jesli nie switch-case a if-elif to mozna nie powtarzac trzy razy add(field)
		switch(this.type){
			case "String": {
				Field<String> field = new Field<String>(val, ifNull);
				arrayFields.add(field);
				break;
				}
			case "Integer": {
				int i = 0;
				try{
					if(!ifNull) i = Integer.parseInt(val);
				} catch(NumberFormatException e){
					ifNull = true;
					throw new BadDatatypeException("int.\n'null' inserted instead");
				}finally{
					Field<Integer> field = new Field<Integer>(i, ifNull);
					arrayFields.add(field);
				}
				break;
				}
			case "Float": {
				float f = 0;
				try{
					if(!ifNull) f = Float.parseFloat(val);
				} catch(NumberFormatException e){
					ifNull = true;
					throw new BadDatatypeException("float.\n'null' inserted instead");
				}finally{
					Field<Float> field = new Field<Float>(f, ifNull);
					arrayFields.add(field);
				}
				break;
				}
			default : {
				throw new BadDatatypeException();
			}
		};
	};
	public void update(String val, List<Integer> indexes) throws BadDatatypeException{
		//NULL nie dziala
		//TODO mozne nie na switch case ->po this.type
		//TODO pozwol mu przekazac Stringa o wartosci NULL/null
		boolean ifNull = false;
		if(val.equals("NULL") || val.equals("null")) ifNull = true; //...util.Objects.NotNull()
		
		if(this.type.equals("Integer")){
				int num = 0;
				try{
					if(!ifNull) num = Integer.parseInt(val);
					setValForIndexes(num, indexes, ifNull);
				} catch(NumberFormatException e){
					throw new BadDatatypeException("int");
				}
				
		}
		else if(this.type.equals("Float")){
				float num = 0;
				try{
					if(!ifNull) num = Float.parseFloat(val);
					setValForIndexes(num, indexes, ifNull);
				} catch(NumberFormatException e){
					throw new BadDatatypeException("float");
				}
		}
		else{
			setValForIndexes(val, indexes, ifNull);}
	};
	
	//ADDITIONAL
	public void createFromCsv(String csvLine) throws InvalidFileSyntaxException{
		try{
			String[] arrayS = csvLine.split(",");

			this.name = arrayS[0];
			this.type = arrayS[1];
			
			for(int i=2; i < arrayS.length; i++){
					this.insert(arrayS[i]);
				} 
		}catch(BadDatatypeException e){
			throw new InvalidFileSyntaxException();
		}catch(ArrayIndexOutOfBoundsException e){
			throw new InvalidFileSyntaxException();
		}
	};	
}

