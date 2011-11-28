package projectatlast.query;

import projectatlast.query.*;
import java.util.Map;

import java.util.Iterator;


/**
 * 
 * @author Erik De Smedt
 */

public class QueryFactory{
	
	Query query = new Query();
	
	
	private interface Interpreter<T> {
		T interpret(String value);
	}
	
	
	/**
	 * 
	 * @param queryOptions: A map containing all options that the query should use.
	 */
	public Query createQuery(Map<String, String> queryOptions){	
		
		//iter is an iterator that iterates over the hashMap
		Iterator<Map.Entry<String, String>> iter = queryOptions.entrySet().iterator();
		while(iter.hasNext()){
			
			Map.Entry<String, String> option = (Map.Entry<String,String>)iter.next();
			evaluate(option);
		}
		return query;
	}
	/*
	 * 
	 * evaluates an option
	 */
	public Option evaluate(Map.Entry<String,String> option){
		String key=option.getKey();
		
		//return new Option();
		return null;
		
	}
}
