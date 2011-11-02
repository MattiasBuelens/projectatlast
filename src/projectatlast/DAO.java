package projectatlast;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.objectify.*;
import com.googlecode.objectify.util.DAOBase;

public class DAO extends DAOBase {
	/**
	 * Registry of entity classes.
	 */
	static {
        ObjectifyService.register(Activity.class);
        ObjectifyService.register(ActivitySlice.class);
        ObjectifyService.register(StudyActivity.class);
        ObjectifyService.register(Mood.class);
    }

	private static DAO instance;
	
	/**
	 * Get the shared database access object (DAO).
	 * 
	 * @return the database access object.
	 */
	public static DAO inst() {
		if(instance == null) {
			instance = new DAO();
		}
		return instance;
	}
	
	/**
	 * Create a new {@link Objectify} instance.
	 * 
	 * @return the Objectify instance.
	 */
	public static Objectify begin() {
		return inst().fact().begin();
	}
	
	/**
	 * Create a new {@link Objectify} instance
	 * for use in a transaction.
	 * 
	 * @return the Objectify instance.
	 */
	public static Objectify beginTransaction() {
		return inst().fact().beginTransaction();
	}
	
	/**
	 * Get the key from an entity object.
	 * 
	 * @param ofy - {@link Objectify} instance with the factory to use.
	 * @return the entity key.
	 */
	public static <T> Key<T> key(T entity, Objectify ofy) {
        return ofy.getFactory().getKey(entity);
	}
	
	/**
	 * Get the key from an entity object.
	 * 
	 * @return the entity key.
	 * @see {@link DAO#key(Object, Objectify)}
	 */
	public static <T> Key<T> key(T entity) {
        return inst().fact().getKey(entity);
	}
	
	/**
	 * Get a map of keys and entity objects from
	 * a collection of entities.
	 * 
	 * @param entities - the entity objects.
	 * @param fact - the factory to create the keys.
	 * @return the map of keys and entity objects.
	 */
	public static Map<Key<Object>, Object> keyMap(Iterable<?> entities, ObjectifyFactory fact) {
        Map<Key<Object>, Object> map = new HashMap<Key<Object>, Object>();
        for(Object object : entities) {
        	map.put(fact.getKey(object), object);
        }
        return map;
	}

	/**
	 * Get a map of keys and entity objects from
	 * a collection of entities.
	 *
	 * @param entities - the entity objects.
	 * @param ofy - {@link Objectify} instance with the factory to use.
	 * @return the map of keys and entity objects.
	 * @see {@link DAO#keyMap(Iterable, ObjectifyFactory)}
	 */
	public static Map<Key<Object>, Object> keyMap(Iterable<?> entities, Objectify ofy) {
        return keyMap(entities, ofy.getFactory());
	}
	
	/**
	 * Get a map of keys and entity objects from
	 * a collection of entities.
	 *
	 * @param entities - the entity objects.
	 * @return the map of keys and entity objects.
	 * @see {@link DAO#keyMap(Iterable, ObjectifyFactory)}
	 */
	public static Map<Key<Object>, Object> keyMap(Iterable<?> entities) {
        return keyMap(entities, inst().fact());
	}
	
	/**
	 * Get the keys from a collection of entities.
	 * 
	 * @param entities - the entity objects.
	 * @param fact - the factory to create the keys.
	 * @return the entity keys.
	 */
	public static Iterable<Key<Object>> keys(Iterable<Object> entities, ObjectifyFactory fact) {
        return keyMap(entities, fact).keySet();
	}
	
	/**
	 * Get the keys from a collection of entities.
	 *
	 * @param entities - the entity objects.
	 * @param ofy - {@link Objectify} instance with the factory to use.
	 * @return the entity keys.
	 * @see {@link DAO#keys(Iterable, ObjectifyFactory)}
	 */
	public static Iterable<Key<Object>> keys(Iterable<Object> entities, Objectify ofy) {
        return keys(entities, ofy.getFactory());
	}
	
	/**
	 * Get the keys from a collection of entities.
	 *
	 * @param entities - the entity objects.
	 * @return the entity keys.
	 * @see {@link DAO#keys(Iterable, ObjectifyFactory)}
	 */
	public static Iterable<Key<Object>> keys(Iterable<Object> entities) {
        return keys(entities, inst().fact());
	}
}