package projectatlast.query;

import java.util.List;

import projectatlast.Activity;

public interface ParseMethod {

	public long parse(List<Activity> activities, ParseField parseField);
}
