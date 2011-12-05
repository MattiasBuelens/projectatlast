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

@Subclass
public class ScatterGraph extends Graph {

	protected ScatterGraph() {}

	ParseField parseFieldX;
	ParseField parseFieldY;
	
	Parser parserX;
	Parser parserY;

	public ScatterGraph(String title, Student student, Query query, GraphType type,
			ParseField parseFieldX, Parser parserX,ParseField parseFieldY, Parser parserY) {

		super(title, student, query, type);

		this.parseFieldX = parseFieldX;
		this.parserX = parserX;
		this.parseFieldY = parseFieldY;
		this.parserY = parserY;

	}

	/**
	 * Generate a XYData object
	 * 
	 * @return
	 */
	@Override
	public GraphData getData() {

		// Get the grouped results
		Groupable<Activity> results = getQueryResult();

		// Parse the results
		Grouped<Long> parsedX = results.parse(getParserX().asFunction(getParseFieldX()));
		Grouped<Long> parsedY = results.parse(getParserY().asFunction(getParseFieldY()));

		ScatterData data = new ScatterData(parsedX,parsedY);

		return data;

	}

	public ParseField getParseFieldY() {
		return parseFieldY;
	}

	public void setParseField(ParseField parseField) {
		this.parseFieldY = parseField;
	}
	
	
	public ParseField getParseFieldX() {
		return parseFieldX;
	}

	public void setParseFieldX(ParseField parseField) {
		this.parseFieldX = parseField;
	}

	public Parser getParserY() {
		return parserY;
	}
	
	public Parser getParserX() {
		return parserX;
	}

	public void setParserX(Parser parser) {
		this.parserX = parser;
	}
	
	public void setParserY(Parser parser) {
		this.parserY = parser;
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
		json.put("xaxis", getParseFieldX().humanReadable());
		json.put("yaxis", getParseFieldY().humanReadable());
		return json;
	}

}
