package projectatlast.query;

import projectatlast.tracking.Activity;

import java.util.List;

public interface ParseMethod {

	public long parse(List<Activity> activities, ParseField parseField);
}
