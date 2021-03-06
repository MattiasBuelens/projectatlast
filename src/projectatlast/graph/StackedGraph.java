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
public class StackedGraph extends Graph {

	@Unindexed Parser parser;
	@Unindexed ParseField parseField;

	protected StackedGraph() {}

	public StackedGraph(String title, Student student, Query query,
			GraphType type, ParseField parseField, Parser parser) {

		super(title, student, query, type);

		this.parser = parser;
		this.parseField = parseField;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public ParseField getParseField() {
		return parseField;
	}

	public void setParseField(ParseField parseField) {
		this.parseField = parseField;
	}

	public GroupField getGroupField() {
		List<Group> groups = getQuery().getGroups();
		if (groups.isEmpty())
			return null;
		return groups.get(0).getField();
	}

	@Override
	public GraphData getData() {
		// Get the grouped results
		Groupable<Activity> results = getQueryResult();

		// Parse the results
		Grouped<Double> parsed = results.parse(getParser().asFunction(getParseField()));

		GraphData data = new StackedData(query.getGroups(), parsed);

		return data;
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
