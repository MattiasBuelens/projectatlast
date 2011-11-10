package projectatlast.tracking;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public abstract class Activity {

	@Id Long id;

	protected Activity() { }

	public long getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Mood getMood() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Retrieves the associated <code>Key&lt;Activity&gt;</code> from
	 * an entity object.
	 * 
	 * <p><code>Activity</code> objects simply return their own key.
	 * Other entities might return the key of their parent or a key
	 * stored in some property.</p>
	 *  
	 * @param key - the key of the entity.
	 * @param obj - the entity object.
	 * @return the associated activity key.
	 */
	/* TODO: Find a better way to do this */
	@SuppressWarnings("unchecked")
	public static Key<Activity> keyFromObject(Key<?> key, Object obj) {
		if(obj instanceof Activity) {
			return (Key<Activity>)key;
		} else if(obj instanceof ActivitySlice) {
			return ((Key<ActivitySlice>)key).getParent();
		}
		return null;
	}

}
