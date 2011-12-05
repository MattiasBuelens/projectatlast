/**
 * A graph that can be represented with 2 datasets (X,Y)
 */

package projectatlast.graph;

import projectatlast.group.*;
import projectatlast.query.*;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.List;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindexed;

@Subclass
public class XYGraph extends Graph {

	@Unindexed ParseField parseField;
	@Unindexed Parser parser;

	protected XYGraph() {}

	public XYGraph(String title, Student student, Query query, GraphType type,
			ParseField parseField, Parser parser) {

		super(title, student, query, type);

		this.parseField = parseField;
		this.parser = parser;

	}

	@Override
	public GraphData getData() {
		// Get the grouped results
		Groupable<Activity> results = getQueryResult();

		// Parse the results
		Grouped<Long> parsed = results.parse(getParser()
				.asFunction(getParseField()));

		// Create data object
		XYData data = new XYData(parsed);

		return data;
	}

	public ParseField getParseField() {
		return parseField;
	}

	public void setParseField(ParseField parseField) {
		this.parseField = parseField;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public GroupField getGroupField() {
		List<Group> groups = getQuery().getGroups();
		if (groups.isEmpty())
			return null;
		return groups.get(0).getField();
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = super.toJSON();
		json = getData().toJSON(json);
		json.put("xaxis", getGroupField().humanReadable());
		json.put("yaxis", getParseField().humanReadable());
		return json;
	}

}
