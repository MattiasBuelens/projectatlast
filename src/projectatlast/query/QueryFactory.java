package projectatlast.query;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.text.ParseException;


import projectatlast.course.*;
import projectatlast.data.Registry;
import projectatlast.student.Student;
import projectatlast.student.AuthController;


/**
 * 
 * @author Erik De Smedt
 */

public class QueryFactory{	
	
	private Query query;
	private HashMap<String, OptionParser> optionDictionary;
	private HashMap<String, Boolean>      hasOption;
	
	public QueryFactory(){
		query = new Query();
		fillDictionary();
	}
	
	/**
	 * The Map can contain the following keys.<br/><br/>
	 * <b>startdatefilter</b>: The value should be a datestring. This filter removes all activities that occured before the given date.<br/>
	 * <b>stopdatefilter</b>:  The value should be a datestring. This filter removes all activities that occured after the given date. <br/>
	 * <b>coursefilter</b>:    The value should be the id of a course. This filter will only retain activities about the given course. <br/>
	 * <b>studentfilter</b>:   The only possible value is "currentuser". This filter will only retain the activties of the currentuser. <br/>
	 * 
	 * @param
	 *  queryOptions   map containing all options that the query should use.
	 */
	
	public Query createQuery(Map<String, String> queryOptions){	
		query = new Query();
		fillDictionary();
		
		/**
		 * The implementation of this function works in 2 parts.
		 * 
	     * At first it applies all given options to the query.
		 * At second it applies all given groups to the query.
		 */
		
		//The first part in which all the options are applied starts here.
		Set<String> keys = queryOptions.keySet();
		for (String key : keys){
			OptionParser optionParser = optionDictionary.get(key);
			if(optionParser!=null){
				optionParser.applyArgument(queryOptions.get(key));
				Option option = optionParser.getOption();
				if(option!=null)
					query.addOption(option);
			}			
		}			
		return query;
	}
		
	private void fillDictionary(){
		optionDictionary  = new HashMap<String, OptionParser>();
		hasOption         = new HashMap<String, Boolean>();
		
		DateFilterParser        dateFilterParser         = new DateFilterParser();
		StopDateFilterParser    stopDateFilterParser     = new StopDateFilterParser(dateFilterParser);
		StartDateFilterParser   startDateFilterParser    = new StartDateFilterParser(dateFilterParser);
		CourseFilterParser      courseFilterParser       = new CourseFilterParser();
		StudentFilterParser     studentFilterParser      = new StudentFilterParser();
		
		optionDictionary.put("stopdatefilter"    , stopDateFilterParser  );
		optionDictionary.put("startdatefilter"   , startDateFilterParser );
		optionDictionary.put("coursefilter"      , courseFilterParser    );
		optionDictionary.put("studentfilter"     , studentFilterParser   );
		
		hasOption.put("stopdatefilter"    , false);
		hasOption.put("startdatefilter"   , false);
		hasOption.put("coursefilter"      , false);
		hasOption.put("studentfilter"     , false);
	}
	
	public interface OptionParser{
		public Option getOption();
		public boolean   applyArgument(String value);
	}
	
	
	public class DateFilterParser{
		private DateFilter filter;
		
		public DateFilterParser(){
			filter = new DateFilter();
		}
		
		public DateFilter getOption(){
			return filter;
		}
		
		public void setOption(DateFilter filter){
			this.filter = filter;
		}
	}
	
	public class StartDateFilterParser implements OptionParser{
		DateFilterParser dateFilterParser;
		
		public StartDateFilterParser(DateFilterParser dfp)
		{
			dateFilterParser = dfp;
		}
		
		public Option getOption()
		{
			return null;
		}
		
		public boolean applyArgument(String value){
			DateFilter option = dateFilterParser.getOption();
			if(option==null)
				option = new DateFilter();
			
			SimpleDateFormat format = new SimpleDateFormat("HH-dd-MM-YYYY");
			try{
				Date startDate    = (Date)format.parse(value);	
				option.from(startDate);
				
				dateFilterParser.setOption(option);
				return true;
			}
			catch (ParseException e){
				return false;
			}
		}	
	}
	
	public class StopDateFilterParser implements OptionParser{
		
		DateFilterParser dateFilterParser;
		
		public StopDateFilterParser(DateFilterParser dfp)
		{
			dateFilterParser = dfp;
		}
		
		public Option getOption(){
			return null;
		}
		
		public boolean applyArgument(String value){

			try{
				DateFilter option = dateFilterParser.getOption();
				SimpleDateFormat format = new SimpleDateFormat("HH-dd-MM-YYYY");
				Date stopDate    = (Date)format.parse(value);				
				option.to(stopDate);
				dateFilterParser.setOption(option);
				return true;
			}
			
			catch(ParseException e){
				return false;
			}
		}	
	}

	private class CourseFilterParser implements OptionParser{
		CourseFilter option = new CourseFilter();
		
		public Option getOption(){
			return option;
		}
		
		public boolean applyArgument(String courseId){
			Course course = Registry.courseFinder().getCourse(courseId);
			applyArgument(course);
			return true;
		}
		
		public boolean applyArgument(Course course){
			option.course(course);
			return true;
		}
	}
	
	private class StudentFilterParser implements OptionParser{
		StudentFilter option;
		
		public StudentFilterParser(){
			option = new StudentFilter();
		}
		
		public Option getOption(){
			return option;
		}
		
		public boolean applyArgument(String user){
			boolean bool;
			if(user=="currentuser" || user=="current" || user=="currentstudent"){
				Student cu = AuthController.getCurrentStudent();
				applyArgument(cu);
				bool= true;
			}
			
			else
				bool= false;
			return bool;
		}
		
		public boolean applyArgument(Student student){
			option.student(student);
			return true;
		}	
	}
	
}
