package goczal.klaudia.database;

class Between extends Action{
	private String strToCompare1;
	private String strToCompare2;
	
	Between(String s, String ss){
		this.strToCompare1 = s;
		this.strToCompare2 = ss;
	};
	
	public boolean execute(String val){
		if(val.equals("null")) return false;
		
		int res1 = this.strToCompare1.compareTo(val);
		int res2 = this.strToCompare2.compareTo(val);
		if((res1 <= 0) && (res2 >= 0)) return true;
		else return false;
	};
	
	public boolean execute(Float val){
		Float numToCompare1 = Float.parseFloat(strToCompare1);
		Float numToCompare2 = Float.parseFloat(strToCompare2);
		if((val >= numToCompare1) && (val <= numToCompare2)) return true;
		else return false;
	};
};
