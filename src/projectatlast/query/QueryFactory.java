package projectatlast.query;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


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
	private List<OptionParser>  optionDictionary;
	
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
	 * 		<li><b>startdate</b>:
	 * 			<ul>
	 * 				<li><b>use:</b> It will remove all activities that occured before the given startdate.</li>
	 * 				<li><b>key:  </b> A datestring in the form "dd-MM-yyyy". </li>
	 * 			</ul>
	 * 		</li>
	 * 
	 * 		<li><b>stopdate</b>
	 * 			<ul>
	 * 				<li><b>use: </b> It will remove all activities that occured after the given stopdate. </li>
	 * 				<li><b>key:    </b> A datestring in the form "ss-hh-dd-MM-yyyy" </li>
	 * 			</ul>
	 * 		</li>
	 * 
	 *    	<li><b>course</b>
	 *    		<ul>
	 *    			<li><b>use: </b> It will add an option to the query to create a filter based on courses.</li>
	 *    			<li><b>key: </b> The id of the course. All other activities courses will be filtered out. </li>
	 *    		</ul>
	 *    	</li>
	 *    
	 *    	<li><b>student</b>
	 *    		<ul>
	 *    			<li><b>use: </b> It will add an option to the query to create a filter based on course. </li>
	 * 				<li><b>key: </b> "current","currentuser","currentstudent". This filter will only retain activities belonging to the current user. </li>
	 * 			</ul>
	 * 		</li>
	 * </ul>
	 * 
	 * 
	 * 
	 * @param
	 *  	optionMap   the option map.
	 *  	groupStrings	A list containing all groups that the query should contain. 
	 *  					The string should be the name of SortField
	 *  
	 *  @returns
	 *  	A query on which all the given options are applied.
	 *  
	 *  @see SortField
	 *  @see Group
	 *  @see Option
	 */
	
	public Query createQuery(Map<String, String> optionMap, List<String> groupStrings){	
		query = new Query();
		fillDictionary();
		
		
		
		for(OptionParser optionParser: optionDictionary){
			Option option = optionParser.parse(optionMap);
			if(option!=null){
				query.addOption(option);
			}
			
		}
		
		
		
		//Sets the query Groups.
		ArrayList<Group> groups = new ArrayList<Group>();
		
		//loops over al Strings in groupstring
		for(String groupString: groupStrings){
			try{
				SortField sf = Enum.valueOf(SortField.class , groupString.trim().toUpperCase());
				groups.add( new Group(sf));
			}
			
			finally{}
		}
		
		//adds the groups to the query
		query.setGroups(groups);
			
		
		return query;
	}
	
	/**
	 * This method (re)fills the dictionary. It is called every time a query is created to make sure all options are cleared.
	 */
	private void fillDictionary(){
		optionDictionary = new ArrayList<OptionParser>();
		
		DateFilterParser           	dateFilterParser          	= new DateFilterParser();
		StudentFilterParser        	studentFilterParser       	= new StudentFilterParser();
		//StudyActivityFilterParser  	studyActivityFilterParser 	= new StudyActivityFilterParser();
		CourseFilterParser			courseFilterParser			= new CourseFilterParser();
		
		optionDictionary.add(dateFilterParser);
		optionDictionary.add(studentFilterParser);
		//optionDictionary.add(studyActivityFilterParser);
		optionDictionary.add(courseFilterParser);
		
		
		
	}
	

	
	/**
	 * An interface for all classes that parse options.
	 * @author Erik De Smedt
	 *
	 */
	private interface OptionParser{
		/**
		 *Parses an option from the optionMap
		 * 
		 * @param optionMap	The optionMap which the createQuery method receives
		 * @return	the Option that has been parsed. The null pointer if no option was parsed.
		 */
		public Option parse(Map<String, String> optionMap);
		
	}
	/**
	 * Parses a DateFilter from the optionMap.
	 * 
	 *  @author Erik De Smedt
	 *	@see DateFilter
	 */
	private class DateFilterParser implements OptionParser
	{
		public Option parse(Map<String, String> optionMap)
		{
			DateFilter option = new DateFilter();
			
			String startDateString 	= optionMap.get("startdate");
			String stopDateString 	= optionMap.get("stopdate");
			
			Date startDate = parseDate(startDateString );
			Date stopDate  = parseDate(stopDateString  );
			
			if(startDate !=null){
				option.from(startDate);
			}
				
			if(stopDate!=null)
			{
				option.to(stopDate);
			}
			
			return option;
		}
			
		private Date parseDate(String dateString)
		{
			DateFormat format = new SimpleDateFormat("ss-mm-HH-dd-MM-yyyy");
			
			Date date;
			if(dateString !=null && dateString != "")
			{
				try{
					date = (Date)format.parse(dateString );
				}
				
				catch(ParseException e){
					date =null;
				}
			}
			else 
				date = null;
			
			return date;
		}
	}
	
	/**
	 * This class is able to parse a Course filter
	 * @author Erik De Smedt
	 *
	 */
	private class CourseFilterParser implements OptionParser{
		
		public Option parse(Map<String, String> optionMap)
		{
			CourseFilter filter = null;
			String courseId = optionMap.get("course");
			Course course = Registry.courseFinder().getCourse(courseId);
			
			if(course!=null){
				filter = new CourseFilter(course);
			}
			return filter;

		}
	}
	
	private class StudentFilterParser implements OptionParser{
		
		public Option parse(Map<String, String> optionMap)
		{
			StudentFilter filter=null;;
			String value=optionMap.get("student").toLowerCase();
		
			if(value=="current"|| value=="currentuser" || value=="currentstudent")
			{
				Student cu = AuthController.getCurrentStudent();
				filter = new StudentFilter(cu);
			}
			
			return filter;
		}
	}	
	
}
