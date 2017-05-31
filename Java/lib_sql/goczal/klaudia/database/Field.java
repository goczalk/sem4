package goczal.klaudia.database;

class Field<T>{
	private boolean isNull = true;
	private T value;
	
	public Field(T val, boolean ifNull){ //Java nie wspiera defaultowych args! :(
		setValue(val, ifNull);
	};
	
	public T getValue(){
		if(!isNull)
			return this.value;
		else
			return null;
	};
	
	public void setValue(T val, boolean ifNull){
		this.isNull = (ifNull == true) ? true : false;
		this.value = val;
	};
}

