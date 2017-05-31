package goczal.klaudia.database;

class SmallerThan extends Action{
	private String strToCompare;
	private boolean andEqual = false;
	
	SmallerThan(String s){
		this.strToCompare = s;
	};
	
	SmallerThan(String s, boolean andEq){
		strToCompare = s;
		this.andEqual = andEq;
	};
	
	public boolean execute(String val){
		if(val.equals("null")) return true;
		
		int res = this.strToCompare.compareTo(val);
		if((res < 0) || (this.andEqual && res == 0)) return true;
		else return false;
	};
	
	public boolean execute(Float val){
		Float numToCompare = Float.parseFloat(strToCompare);
		if((val < numToCompare) || (this.andEqual && numToCompare.equals(val))) return true;
		else return false;
	};
};
