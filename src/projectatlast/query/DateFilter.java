package projectatlast.query;

import projectatlast.tracking.Activity;
import projectatlast.tracking.ActivitySlice;

import java.util.*;

import com.googlecode.objectify.Query;

public class DateFilter extends Option {
	private static final long serialVersionUID = 1L;

	Date lower;
	Date upper;
	transient Date lowerRound;
	transient Date upperRound;

	public DateFilter() {}

	public DateFilter(Date from, Date to) {
		from(from);
		to(to);
	}

	/**
	 * Get the lower date boundary.
	 * 
	 * @return the lower boundary.
	 */
	public Date from() {
		return lower;
	}

	/**
	 * Set the lower date boundary.
	 * 
	 * @param from
	 *            the new lower boundary
	 * @return this (for chaining)
	 */
	public DateFilter from(Date from) {
		this.lower = from;
		return this;
	}

	/**
	 * Get the upper date boundary.
	 * 
	 * @return the upper boundary.
	 */
	public Date to() {
		return upper;
	}

	/**
	 * Set the upper date boundary.
	 * 
	 * @param to
	 *            the new upper boundary
	 * @return this (for chaining)
	 */
	public DateFilter to(Date to) {
		this.upper = to;
		return this;
	}

	@Override
	public Class<?> getKind() {
		return ActivitySlice.class;
	}

	/**
	 * Check whether this {@link DateFilter} can be applied to a given query
	 * kind.
	 * 
	 * <p>
	 * A <code>DateFilter</code> can be applied to {@link Activity} and
	 * {@link ActivitySlice} queries.
	 * 
	 * @param kind
	 *            The query kind.
	 * @return
	 */
	@Override
	public boolean appliesTo(Class<?> kind) {
		return ActivitySlice.class.isAssignableFrom(kind);
				// || Activity.class.isAssignableFrom(kind);
	}

	@Override
	public void apply(Class<?> kind, Query<?> query) {
		// Round boundaries
		roundBoundaries();

		if (ActivitySlice.class.isAssignableFrom(kind)) {
			// Filter slices on both date boundaries
			if (lowerRound != null) {
				query.filter("date >=", lowerRound);
			}
			if (upperRound != null) {
				query.filter("date <=", upperRound);
			}
			if (lowerRound != null || upperRound != null) {
				query.order("date");
			}
		}/* else if (Activity.class.isAssignableFrom(kind)) {
			// Filter slices on one date boundary
			if (lower != null) {
				query.filter("endDate >", lower);
				query.order("endDate");
			} else if (upper != null) {
				query.filter("startDate <", upper);
				query.order("startDate");
			}
		}*/
	}

	/**
	 * Process the resulting list of activities, making sure they fall within
	 * the specified date boundaries.
	 * 
	 * This processing only needs to be done when all of following conditions
	 * are met:
	 * <ul>
	 * <li>Both date boundaries are specified.
	 * <p>
	 * If one of the boundaries is not specified, the filters applied on the
	 * activity query will produce exact results.</li>
	 * <li>At least one of the boundaries was rounded.
	 * <p>
	 * If the boundaries are round hours and the distance between them is large
	 * enough, the filters applied on the activity slice query will produce
	 * exact queries.</li>
	 * </ul>
	 * 
	 * @param activities
	 *            The list of activities.
	 */
	@Override
	public <A extends Activity> void process(List<A> activities) {
		boolean isLowerRounded = (lower != null && !lower.equals(lowerRound));
		boolean isUpperRounded = (upper != null && !upper.equals(upperRound));
		if (isLowerRounded || isUpperRounded) {
			ListIterator<A> it = activities.listIterator();
			while (it.hasNext()) {
				A activity = it.next();
				if (isLowerRounded && !activity.getEnd().after(lower)) {
					// If activity ends before lower boundary, remove it
					it.remove();
				} else if (isUpperRounded && !activity.getStart().before(upper)) {
					// If activity starts after right boundary, remove it
					it.remove();
				} else {
					// Constrain activity between boundaries
					if (lower != null && activity.getStart().before(lower)) {
						activity.setStart(lower);
					}
					if (upper != null && activity.getEnd().after(upper)) {
						activity.setEnd(upper);
					}
				}
			}
		}
	}

	/**
	 * Round the date boundaries.
	 */
	protected void roundBoundaries() {
		Calendar cal = Calendar.getInstance();

		if (lower != null) {
			// Round lower boundary down to closest hour
			cal.setTime(lower);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			lowerRound = cal.getTime();
		} else {
			// Do not filter on lower boundary
			lowerRound = null;
		}

		if (upper != null) {
			if (lowerRound != null) {
				// Make sure distance between boundaries is at least one hour
				long distance = upper.getTime() - lowerRound.getTime();
				if (distance < 60 * 60 * 1000) {
					cal.setTime(lowerRound);
					cal.add(Calendar.HOUR_OF_DAY, +1);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					upperRound = cal.getTime();
				}
			} else {
				// Round upper boundary up to next hour
				cal.setTime(upper);
				cal.add(Calendar.HOUR_OF_DAY, +1);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				upperRound = cal.getTime();
			}
		} else {
			// Do not filter on upper boundary
			upperRound = null;
		}
	}
}
