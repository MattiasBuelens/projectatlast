package query;

import java.util.List;

import projectatlast.Activity;

public interface QueryParseMethod {

	public long parse(List<Activity> activities, QueryParseField parseField);
}
