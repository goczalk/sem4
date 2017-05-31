package vector;

import java.util.*;
import java.io.*;

public class VectorCustom{
	private ArrayList<Integer> data;
	
	public VectorCustom(){
		data = new ArrayList<Integer>();
	}
	
	public VectorCustom(String line) throws InvalidVectorDataException{
		//possible?
		//this.Vector();
		data = new ArrayList<Integer>();
        String[] numbersList = line.split(" ");

		try{
			for(String num : numbersList){
				data.add(Integer.parseInt(num));
			}
		}catch(NumberFormatException e){
			throw new InvalidVectorDataException();
		}
    };
	
	private ArrayList<Integer> getData(){
		return this.data;
	};
	
	private Integer getElementWithIndex(int i){
		return this.data.get(i);
	};
	
	private void addElement(Integer i){
		this.data.add(i);
	};
	
	private int getSize(){
		return this.data.size();
	}
	
	public static VectorCustom add(VectorCustom first, VectorCustom second) throws VectorsOfDifferentSizesException{
		if(first.getSize() != second.getSize()) throw new VectorsOfDifferentSizesException(first.getSize(), second.getSize());
		else{
			VectorCustom result = new VectorCustom();
			
			for(int i=0; i< first.getSize();i++){
				result.addElement(first.getElementWithIndex(i) + second.getElementWithIndex(i));
			}

			return result;
		}
    };
	
	public String toString(){
		String str = "";
		
		for(Integer element : this.data){
			str += element + " ";
		}
	
		return str;
	};

	//jak go zmusic do zamkniecia?!
	public void writeToFile(String filename){
		FileWriter fw=null;
		try{
			fw = new FileWriter(filename, false);
			fw.write(this.toString());
			
			fw.close();
		}catch (IOException e){
			System.err.println(e.getMessage());
		}	
    };
}