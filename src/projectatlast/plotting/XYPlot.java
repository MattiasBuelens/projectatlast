package projectatlast.plotting;

import projectatlast.query.*;
import projectatlast.tracking.Activity;

import java.util.*;

public class XYPlot {

	SortField sortField;
	ParseField parseField;
	Parser parser;
	List<Activity> activities;

	public XYPlot(List<Activity> activities, SortField sortField,
			ParseField parseField, Parser parser) {
		super();
		this.sortField = sortField;
		this.parseField = parseField;
		this.parser = parser;
		this.activities = activities;
	}

	public XYData generateXYData() {
		// group the activities
		Map<Object, List<Activity>> grouped = new Group(sortField)
				.group(this.activities);

		System.out.println(grouped);
		List<Object> groups = new ArrayList<Object>(grouped.keySet());

		// use parser on every group;

		ArrayList<Long> parseResult = new ArrayList<Long>();

		for (Object currentGroup : groups) {
			activities = grouped.get(currentGroup);
			long result = this.parser.parse(activities, parseField);
			parseResult.add(result);
		}

		// generate XYData
		XYData data = new XYData(groups, parseResult);

		return data;

	}

}