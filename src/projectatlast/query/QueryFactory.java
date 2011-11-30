package projectatlast.query;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;


import projectatlast.course.*;
import projectatlast.data.Registry;
import projectatlast.student.Student;
import projectatlast.student.AuthController;


/**
 * The QueryFactory class is responsible for the creation of a query from 
 * the input of the frontend (userinterface). Practically this input are all 
 * strings. The QueryFactory is able to convert this strings into a Query element
 * that can be used for milestones or graphs.
 * 
 * @see projectatlast.query.Query
 * @see projectatlast.query.Option
 * @author Erik De Smedt
 * @version 30-11-2011
 */

public class QueryFactory{	
	
	
	private Query query;
	private HashMap<String, OptionParser> optionDictionary;
	
	/**
	 * 
	 */
	public QueryFactory(){
		query = new Query();
		fillDictionary();
	}
	
	/**
	 * The createQuery() method creates a query starting from a given HashMap. From now on this
	 * HashMap<String,String> will be called the option map. The keys and values will 
	 * be called respectively option keys and option values. Both the option keys and option values are not case sensitive.
	 * 
	 * The option map can contain the following keys.<br/>
	 * 
	 * <ul>
	 * 		<li><b>startdatefilter</b>:
	 * 			<ul>
	 * 				<li><b>use:</b> It will remove all activities that occured before the given startdate.</li>
	 * 				<li><b>key:  </b> A datestring in the form "dd-MM-yyyy". </li>
	 * 			</ul>
	 * 		</li>
	 * 
	 * 		<li><b>stopdatefilter</b>
	 * 			<ul>
	 * 				<li><b>use: </b> It will remove all activities that occured after the given stopdate. </li>
	 * 				<li><b>key:    </b> A datestring in the form "dd-MM-yyyy" </li>
	 * 			</ul>
	 * 		</li>
	 * 
	 *    	<li><b>coursefilter</b>
	 *    		<ul>
	 *    			<li><b>use: </b> It will add an option to the query to create a filter based on courses.</li>
	 *    			<li><b>key: </b> The id of the course. All other activities courses will be filtered out. </li>
	 *    		</ul>
	 *    	</li>
	 *    
	 *    	<li><b>studentfilter</b>
	 *    		<ul>
	 *    			<li><b>use: </b> It will add an option to the query to create a filter based on course. </li>
	 * 				<li><b>key: </b> "current","currentuser","currentstudent". This filter will only retain activities belonging to the current user. </li>
	 * 			</ul>
	 * 		</li>
	 * </ul>
	 * @param
	 *  	queryOptions   the option map.
	 *  
	 *  @returns
	 *  	A query on which all the given options are applied.
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
		boolean hasDateFilter=false;
		for (String key : keys){
			OptionParser optionParser = optionDictionary.get(key);
			if(optionParser!=null){
				optionParser.applyArgument(queryOptions.get(key));
				Option option = optionParser.getOption();
				
				if(option!=null && !hasDateFilter)
					hasDateFilter = (option instanceof DateFilter);
					query.addOption(option);
			}			
		}			
		return query;
	}
	
	/**
	 * This method (re)fills the dictionary. It is called every time a query is created to make sure all options are cleared.
	 */
	private void fillDictionary(){
		//clears the dictionary and the has
		optionDictionary  = new HashMap<String, OptionParser>();
		
		StopDateFilterParser    stopDateFilterParser     = new StopDateFilterParser();
		StartDateFilterParser   startDateFilterParser    = new StartDateFilterParser(stopDateFilterParser);
		CourseFilterParser      courseFilterParser       = new CourseFilterParser();
		StudentFilterParser     studentFilterParser      = new StudentFilterParser();
		
		optionDictionary.put("stopdatefilter"    , stopDateFilterParser  );
		optionDictionary.put("startdatefilter"   , startDateFilterParser );
		optionDictionary.put("coursefilter"      , courseFilterParser    );
		optionDictionary.put("studentfilter"     , studentFilterParser   );
		
	}
	
	/**
	 * An interface for all classes that parse options. This means they parse options based on a string value.
	 * @author Erik De Smedt
	 *
	 */
	private interface OptionParser{
		/**
		 * @return The option the optionParser has parsed. If their was never an argument applied the getOption() method will return the null pointer.
		 */
		public Option getOption();
		
		/**
		 * The apply argument operator applies a given value to the option.
		 * @param value		The value that will be applied.
		 * @return			True if the operation succeeded, elsewise it will return false.
		 */
		public boolean   applyArgument(String value);
	}
	
	
	/**
	 * 
	 * <p> The DateFilterFields is a class designed to adress the following problem. For the frontend the startDate and stopDate are
	 * 2 completely different entities. And it would be logical to send these two variables independently just as with other options.
	 * But for the database these 2 variables are closely connected. The database requires that these 2 options are grouped
	 * </p>
	 * 
	 * <p>
	 * The DateFilterFields class solves this problem. It contains the fields of an DateFilterParser object. This makes it possible 
	 * for 2 different classes to use the same fields.
	 * </p>
	 * 
	 * @author Erik De Smedt
	 * @version 30-11-2011
	 * @see StartDateFilterParser
	 * @see StopDateFilterParser
	 */
	private class DateFilterFields{
		private DateFilter filter = null;
		private SimpleDateFormat format;
		
		//constructor
		public DateFilterFields()
		{
			format = new SimpleDateFormat("dd - MM - yyyy");
		}
		
		public DateFilter getFilter(){
			return filter;
		}
		
		public boolean setFilter(DateFilter datefilter){
			this.filter=datefilter;
			return true;
		}
		
		public SimpleDateFormat getFormat()
		{
			return this.format;
		}
	}
	
	/**
	 * The DateFilterParser class is an abstract class which the StartDateFilterParser and StopDateFilterParser inherit from.
	 * @author fnac
	 *
	 */
	private abstract class DateFilterParser implements OptionParser{
		protected DateFilterFields fields;
		
		public DateFilterParser()
		{
			fields = new DateFilterFields();
		}
		
		public DateFilterParser(DateFilterParser dfp)
		{
			this.fields = dfp.fields;
		}
		
		public abstract boolean applyArgument(String value);
		
		public Option getOption(){
			return fields.getFilter();
		}
	}
		
	/**
	 * @author Erik De Smedt
	 */
	public class StartDateFilterParser extends DateFilterParser implements OptionParser{
		
		//The constructor
		public StartDateFilterParser()
		{
			super();
		}
		
		/**
		 * @param dfp A DateFilterParser which will share the same fields as the created one.
		 */
		public StartDateFilterParser(DateFilterParser dfp)
		{
			super(dfp);
		}
		
		public boolean applyArgument(String value){
			
			DateFilter dateFilter = fields.getFilter();
		
			try{
				if(dateFilter==null)
					dateFilter = new DateFilter();
				
				Date startDate    = (Date)fields.getFormat().parse(value);	
				dateFilter.from(startDate);
				
				fields.setFilter(dateFilter);
				return true;
			}
			catch (ParseException e){
				return false;
			}
		}	
	}
	
	public class StopDateFilterParser extends DateFilterParser implements OptionParser{
		
		public  StopDateFilterParser()
		{
			super();
		}
		
		public StopDateFilterParser(DateFilterParser dfp)
		{
			super(dfp);
		}
				
		public boolean applyArgument(String value){
			
			DateFilter dateFilter = fields.getFilter();
			
			try{
				if(dateFilter==null)
					dateFilter = new DateFilter();
				
				Date stopDate    = (Date)fields.getFormat().parse(value);	
				dateFilter.to(stopDate);
				
				fields.setFilter(dateFilter);
				return true;
			}
			catch (ParseException e){
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
				if(cu==null)
					bool = false;
				bool = applyArgument(cu);
			}
			else
				bool= false;
			return bool;
		}
		
		public boolean applyArgument(Student student){
			if(student==null)
				return false;
			
			else{
				option.student(student);
				return true;
			}
		}	
	}
}
