package goczal.klaudia.database;

class BiggerThan extends Action{
	private String strToCompare;
	private boolean andEqual = false;
	
	BiggerThan(String s){
		this.strToCompare = s;
	};
	
	BiggerThan(String s, boolean andEq){
		strToCompare = s;
		this.andEqual = andEq;
	};
	
	public boolean execute(String val){
		if(val.equals("null")) return false;
		
		int res = this.strToCompare.compareTo(val);
		if((res > 0) || (this.andEqual && res == 0)) return true;
		else return false;
	};
	
	public boolean execute(Float val){
		Float numToCompare = Float.parseFloat(strToCompare);
		if((val > numToCompare) || (this.andEqual && numToCompare.equals(val))) return true;
		else return false;
	};
};
