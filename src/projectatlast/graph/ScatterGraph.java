/**
 * A graph that can be represented with 2 datasets (X,Y)
 */

package projectatlast.graph;

import projectatlast.group.*;
import projectatlast.query.*;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindexed;

@Subclass
public class ScatterGraph extends Graph {

	@Unindexed ParseField parseFieldX;
	@Unindexed ParseField parseFieldY;

	@Unindexed Parser parserX;
	@Unindexed Parser parserY;

	protected ScatterGraph() {}

	protected static List<Group> getGroups() {
		List<Group> groups = new ArrayList<Group>();
		groups.add(new Group(GroupField.ACTIVITY));
		return groups;
	}

	public ScatterGraph(String title, Student student, Query query,
			GraphType type, ParseField parseFieldX, Parser parserX,
			ParseField parseFieldY, Parser parserY) {

		super(title, student, query, type);
		this.parseFieldX = parseFieldX;
		this.parserX = parserX;
		this.parseFieldY = parseFieldY;
		this.parserY = parserY;

		this.query.setGroups(getGroups());
	}

	public ParseField getParseFieldX() {
		return parseFieldX;
	}

	public void setParseFieldX(ParseField parseField) {
		this.parseFieldX = parseField;
	}

	public ParseField getParseFieldY() {
		return parseFieldY;
	}

	public void setParseFieldY(ParseField parseField) {
		this.parseFieldY = parseField;
	}

	public Parser getParserX() {
		return parserX;
	}

	public void setParserX(Parser parser) {
		this.parserX = parser;
	}

	public Parser getParserY() {
		return parserY;
	}

	public void setParserY(Parser parser) {
		this.parserY = parser;
	}

	@Override
	public GraphData getData() {
		// Get the grouped results
		Groupable<Activity> results = getQueryResult();

		// Parse the results
		Grouped<Long> parsedX = results.parse(getParserX()
				.asFunction(getParseFieldX()));
		Grouped<Long> parsedY = results.parse(getParserY()
				.asFunction(getParseFieldY()));

		// Create data object
		ScatterData data = new ScatterData(parsedX, parsedY);

		return data;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = super.toJSON();
		json = getData().toJSON(json);
		json.put("xaxis", getParseFieldX().humanReadable());
		json.put("yaxis", getParseFieldY().humanReadable());
		return json;
	}

}
