package goczal.klaudia.database;

import goczal.klaudia.database.exceptions.*;

abstract class Action{
	public abstract boolean execute(String val) throws InvalidWhereSyntaxException;
	public abstract boolean execute(Float val);
};