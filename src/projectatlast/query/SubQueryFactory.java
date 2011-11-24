package projectatlast.query;

import projectatlast.tracking.*;

import com.googlecode.objectify.Objectify;

public class SubQueryFactory {
	protected Objectify ofy;
	public SubQueryFactory(Objectify ofy) {
		this.ofy = ofy;
	}
	
	public SubQuery<?> create(Class<?> cls) {
		if(ActivitySlice.class.isAssignableFrom(cls)) {
			return new SubQueryActivitySlice(cls, ofy);
		/*} else if(StudyActivity.class.isAssignableFrom(cls)) {
			return new SubQueryStudyActivity(cls);*/
		} else if(Activity.class.isAssignableFrom(cls)) {
			return new SubQueryActivity(cls, ofy);
		}
		return null;
	}
}
