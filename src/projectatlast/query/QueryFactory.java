package projectatlast.query;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.group.Group;
import projectatlast.group.GroupField;
import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.tracking.FreeTimeActivity;
import projectatlast.tracking.StudyActivity;

import java.text.*;
import java.util.*;

/**
 * A factory to create queries out of string parameters received from the
 * front-end.
 * 
 * @author Erik De Smedt
 * @author Mattias Buelens
 * @version 2011-12-07
 */

public class QueryFactory {

	public static final String dateFormat = "dd-MM-yyyy";

	private Query query;
	private List<OptionParser> optionParsers;

	public QueryFactory() {}

	/**
	 * Create a query with options from the given option map and groups from the
	 * given groups list.
	 * 
	 * <p>
	 * The option map can contain the following keys:
	 * <ul>
	 * 
	 * <li>student
	 * <dl>
	 * <dt>usage</dt>
	 * <dd>Only select activities from a certain student.</dd>
	 * <dt>value</dt>
	 * <dd>"current","currentuser","currentstudent"</dd>
	 * </dl>
	 * 
	 * <li>kind
	 * <dl>
	 * <dt>usage</dt>
	 * <dd>Only select a certain kind of activities.</dd>
	 * <dt>value</dt>
	 * <dd>"study" for study activities, "freetime" for free time activities.
	 * </dl>
	 * </li>
	 * 
	 * <li>startdate
	 * <dl>
	 * <dt>usage</dt>
	 * <dd>Only keep activities after a given date.</dd>
	 * <dt>value</dt>
	 * <dd>Date string in the format "dd-MM-yyyy".</dd>
	 * </dl>
	 * </li>
	 * 
	 * <li>stopdate
	 * <dl>
	 * <dt>usage</dt>
	 * <dd>Only keep activities before a given date.</dd>
	 * <dt>value</dt>
	 * <dd>Date string in the format "dd-MM-yyyy".</dd>
	 * </dl>
	 * </li>
	 * 
	 * <li>course
	 * <dl>
	 * <dt>usage</dt>
	 * <dd>Only select study activities for a certain course.</dd>
	 * <dt>value</dt>
	 * <dd>Course identifier as given by {@link Course#getId}.</dd>
	 * </dl>
	 * </li>
	 * 
	 * </ul>
	 * 
	 * @param optionMap
	 *            The option map.
	 * @param groupIds
	 *            List of group identifiers.
	 * 
	 * @returns A query on which all the given options are applied.
	 */
	public Query createQuery(Map<String, String> optionMap,
			List<String> groupIds) {
		query = new Query();

		setOptions(optionMap);
		if(groupIds!=null){
			setGroups(groupIds);
		}

		return query;
	}

	/**
	 * Creates an option map from the given query.
	 * 
	 * @param query
	 *            The query.
	 * @return The option map.
	 */
	public Map<String, String> getOptions(Query query) {
		createParsers();

		Map<String, String> optionMap = new LinkedHashMap<String, String>();
		for (Option option : query.getOptions()) {
			// Try to parse this option with all option parsers
			boolean result = false;
			Iterator<OptionParser> it = optionParsers.iterator();
			while (!result && it.hasNext()) {
				OptionParser optionParser = it.next();
				result = optionParser.stringify(option, optionMap);
			}
		}

		return optionMap;
	}

	/**
	 * Creates a list of group identifiers from the given query.
	 * 
	 * @param query
	 *            The query.
	 * @return The list of group identifiers.
	 */
	public List<String> getGroups(Query query) {
		List<String> groupIds = new ArrayList<String>();
		for (Group group : query.getGroups()) {
			groupIds.add(group.getField().id());
		}
		return groupIds;
	}

	protected void setOptions(Map<String, String> optionMap) {
		createParsers();

		query.setOptions(null);
		for (OptionParser optionParser : optionParsers) {
			Option option = optionParser.parse(optionMap);
			// If option parser created option, add it
			if (option != null) {
				query.addOption(option);
			}
		}
	}

	protected void setGroups(List<String> groupIds) {
		query.setGroups(null);
		for (String groupId : groupIds) {
			GroupField groupField = GroupField.fromId(groupId.trim());
			// If group field is valid, add it
			if (groupField != null) {
				query.addGroup(new Group(groupField));
			}
		}
	}

	/**
	 * Create new option parsers before building a new query.
	 */
	private void createParsers() {
		optionParsers = new ArrayList<OptionParser>();
		optionParsers.add(new StudentFilterParser());
		optionParsers.add(new KindFilterParser());
		optionParsers.add(new DateFilterParser());
		optionParsers.add(new CourseFilterParser());
		optionParsers.add(new TypeFilterParser());
	}

	/**
	 * An interface for all classes that parse options.
	 * 
	 * @author Erik De Smedt
	 */
	private interface OptionParser {
		/**
		 * Parses an option from the option map.
		 * 
		 * @param optionMap
		 *            The option map to parse.
		 * @return An {@link Option} if the parsing was successful, or null if
		 *         no option could be parsed.
		 */
		public Option parse(Map<String, String> optionMap);

		/**
		 * Places an option in the option map.
		 * 
		 * @param option
		 *            The option to parse.
		 * @param optionMap
		 *            The option map being built.
		 * @return True if the option has been added, false otherwise.
		 */
		public boolean stringify(Option option, Map<String, String> optionMap);
	}

	/**
	 * {@link StudentFilter} parser.
	 * 
	 * @author Erik De Smedt
	 */
	private class StudentFilterParser implements OptionParser {
		@Override
		public Option parse(Map<String, String> optionMap) {
			StudentFilter filter = null;
			String value = optionMap.get("student");
			if (value == null)
				return filter;

			if (value.equalsIgnoreCase("current")
					|| value.equalsIgnoreCase("currentuser")
					|| value.equalsIgnoreCase("currentstudent")) {
				Student student = AuthController.getCurrentStudent();
				filter = new StudentFilter(student);
			}

			return filter;
		}

		@Override
		public boolean stringify(Option option, Map<String, String> optionMap) {
			if (option instanceof StudentFilter) {
				Student student = ((StudentFilter) option).student();
				if (student.equals(AuthController.getCurrentStudent())) {
					optionMap.put("student", "currentstudent");
				}
				return true;
			}
			return false;
		}
	}

	/**
	 * {@link KindFilter} parser.
	 * 
	 * @author Erik De Smedt
	 */
	private class KindFilterParser implements OptionParser {
		@Override
		public Option parse(Map<String, String> optionMap) {
			KindFilter filter = null;
			String value = optionMap.get("kind");
			if (value == null)
				return filter;

			if (value.equalsIgnoreCase("study"))
				filter = new KindFilter(StudyActivity.class);
			else if (value.equalsIgnoreCase("freetime"))
				filter = new KindFilter(FreeTimeActivity.class);

			return filter;
		}

		@Override
		public boolean stringify(Option option, Map<String, String> optionMap) {
			if (option instanceof KindFilter) {
				Class<?> kind = ((KindFilter) option).getKind();
				if (StudyActivity.class.isAssignableFrom(kind)) {
					optionMap.put("kind", "study");
				} else if (FreeTimeActivity.class.isAssignableFrom(kind)) {
					optionMap.put("kind", "freetime");
				}
				return true;
			}
			return false;
		}
	}

	/**
	 * {@link DateFilter} parser.
	 * 
	 * @author Erik De Smedt
	 */
	private class DateFilterParser implements OptionParser {
		@Override
		public Option parse(Map<String, String> optionMap) {
			DateFilter option = new DateFilter();
			Calendar cal = Calendar.getInstance();

			String startDateString = optionMap.get("startdate");
			String stopDateString = optionMap.get("stopdate");

			// Parse dates
			Date startDate = parseDate(startDateString);
			Date stopDate = parseDate(stopDateString);
			// Set stop time to 23:59:59
			if (stopDate != null) {
				cal.setTime(stopDate);
				cal.add(Calendar.DATE, 1);
				cal.add(Calendar.MILLISECOND, -1);
				stopDate = cal.getTime();
			}

			if (startDate != null) {
				option.from(startDate);
			}

			if (stopDate != null) {
				option.to(stopDate);
			}

			return option;
		}

		@Override
		public boolean stringify(Option option, Map<String, String> optionMap) {
			if (option instanceof DateFilter) {
				DateFilter filter = (DateFilter) option;
				Date startDate = filter.from();
				Date stopDate = filter.to();
				if (startDate != null) {
					optionMap.put("startdate", formatDate(startDate));
				}
				if (stopDate != null) {
					optionMap.put("stopdate", formatDate(stopDate));
				}
				return true;
			}
			return false;
		}

		private Date parseDate(String dateString) {
			DateFormat format = new SimpleDateFormat(dateFormat);
			Date date = null;
			if (dateString != null && dateString != "") {
				try {
					date = format.parse(dateString);
				} catch (ParseException e) {
					date = null;
				}
			}
			return date;
		}

		private String formatDate(Date date) {
			return new SimpleDateFormat(dateFormat).format(date);
		}
	}

	/**
	 * {@link CourseFilter} parser.
	 * 
	 * @author Erik De Smedt
	 */
	private class CourseFilterParser implements OptionParser {
		@Override
		public Option parse(Map<String, String> optionMap) {
			CourseFilter filter = null;
			String courseId = optionMap.get("course");
			Course course = Registry.courseFinder().getCourse(courseId);
			if (course != null) {
				filter = new CourseFilter(course);
			}
			return filter;
		}

		@Override
		public boolean stringify(Option option, Map<String, String> optionMap) {
			if (option instanceof CourseFilter) {
				Course course = ((CourseFilter) option).course();
				if (course != null) {
					optionMap.put("course", course.getId());
				}
				return true;
			}
			return false;
		}
	}

	/**
	 * {@link TypeFilter} parser.
	 * 
	 * @author Mattias Buelens
	 */
	private class TypeFilterParser implements OptionParser {
		@Override
		public Option parse(Map<String, String> optionMap) {
			TypeFilter filter = null;
			String type = optionMap.get("type");
			if (type != null) {
				filter = new TypeFilter(type);
			}
			return filter;
		}

		@Override
		public boolean stringify(Option option, Map<String, String> optionMap) {
			if (option instanceof TypeFilter) {
				String type = ((TypeFilter) option).type();
				if (type != null && !type.isEmpty()) {
					optionMap.put("type", type);
				}
				return true;
			}
			return false;
		}
	}

}
