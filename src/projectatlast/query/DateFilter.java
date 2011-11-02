package projectatlast.query;

import projectatlast.Activity;
import projectatlast.ActivitySlice;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Query;

public class DateFilter extends Option {

	Date from;
	Date to;

	public DateFilter(Date from, Date to) {
		from(from);
		to(to);
	}

	public DateFilter from(Date from) {
		this.from = from;
		return this;
	}

	public DateFilter to(Date to) {
		this.to = to;
		return this;
	}

	@Override
	public Class<?> getKind() {
		return ActivitySlice.class;
	}

	@Override
	public void apply(Query<?> query) {
		if (from != null) {
			query.filter("date >=", from);
		}
		if (to != null) {
			query.filter("date <", to);
		}
	}
	
	@Override
	public List<Activity> process(List<Activity> activities) {
		return activities;
	}
}
