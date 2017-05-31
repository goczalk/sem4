package goczal.klaudia.database;

class Equal extends Action{
	private String strToCompare;
	
	Equal(String s){
		strToCompare = s;
	};
	
	public boolean execute(String val){
		if(strToCompare.equals(val)) return true;
		else return false;
	};
	
	public boolean execute(Float val){
		Float numToCompare = Float.parseFloat(strToCompare);
		if(numToCompare.equals(val)) return true;
		else return false;
	};
};