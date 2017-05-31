package goczal.klaudia.database;

import java.util.regex.*;//.regex.PatternSyntaxException
import goczal.klaudia.database.exceptions.*;

class Like extends Action{
	private String sqlRegex;
	
	Like(String s){
		this.sqlRegex = s;
		transformIntoNormalRegex();
	};
	
	private void transformIntoNormalRegex(){
		//escape all regex chars
		this.sqlRegex = this.sqlRegex.replace(".", "\\.")./*.replace("^", "\\^").*/
						replace("*", "\\*").replace("?", "\\?").replace("$", "\\$").
						replace("+", "\\+").replace("}", "\\}").replace("{", "\\{");
		//TODO gdy bedzie: \\% i \\_ tez zamieni!, niech pozwoli na wpisanie ^
		this.sqlRegex = "^" + this.sqlRegex.replace("%", ".*").replace("_", ".");
	};
	
	public boolean execute(String val) throws InvalidWhereSyntaxException{
		if(val.equals("null")) return false;
		try{
			if(val.matches(this.sqlRegex)) return true;
		else return false;
		}catch(PatternSyntaxException e){
			throw new InvalidWhereSyntaxException("ERROR: Invalid regex.");
		}
	};
	
	//for sake of Action, never executed
	//TODO should be changes to non-abstract in Action?
	public boolean execute(Float val){return false;};
};
