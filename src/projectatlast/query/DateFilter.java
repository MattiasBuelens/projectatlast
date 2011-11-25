package projectatlast.query;

import projectatlast.tracking.Activity;
import projectatlast.tracking.ActivitySlice;

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
		// TODO
		// Make sure there is at least a one hour difference between from and to
		// Keep track of the changed date boundary
		if (from != null) {
			query.filter("date >=", from);
		}
		if (to != null) {
			query.filter("date <", to);
		}
	}

	@Override
	public <T extends Activity> void process(List<T> activities) {
		// TODO
		// Make sure all activities are contained between 'from' and 'to'
		// Depending on the changed date boundary in apply, this can be done pretty fast
	}
}
