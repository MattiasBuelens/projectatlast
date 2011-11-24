package projectatlast.query;

import java.util.*;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class ResultSet {
	private Objectify ofy;
	private Map<Class<?>, QueryResultIterable<Key<?>>> iterables;
	private Map<Class<?>, Map<?, ?>> results;

	public ResultSet(Objectify ofy) {
		this.ofy = ofy;
		this.iterables = new HashMap<Class<?>, QueryResultIterable<Key<?>>>();
		this.results = new HashMap<Class<?>, Map<?, ?>>();
	}

	/**
	 * Add a query result to the result set.
	 * 
	 * @param cls
	 *            The query kind.
	 * @param iterable
	 *            The iterable result set.
	 */
	public void add(Class<?> cls, QueryResultIterable<Key<?>> iterable) {
		iterables.put(cls, iterable);
	}

	/**
	 * Fetches the result set from a given query kind.
	 * 
	 * @param cls
	 *            The query kind.
	 * @return Map of entity objects mapped by their keys, or an empty map if no
	 *         such query kind was found.
	 */
	public <T> Map<Key<T>, T> fetch(Class<T> cls) {
		if (!iterables.containsKey(cls))
			return Collections.emptyMap();
		if (results.containsKey(cls)) {
			// Retrieve stored results
			@SuppressWarnings("unchecked")
			Map<Key<T>, T> classResults = (Map<Key<T>, T>) results.get(cls);
			return classResults;
		} else {
			// Fetch keys
			Set<Key<T>> keys = fetchKeys(cls);
			// Fetch entities
			Map<Key<T>, T> classResults = ofy.get(keys);
			// Store results and return
			results.put(cls, classResults);
			return classResults;
		}
	}

	public <T> Set<Key<T>> fetchKeys(Class<T> cls) {
		if (!iterables.containsKey(cls))
			return Collections.emptySet();
		Set<Key<T>> keys = new HashSet<Key<T>>();
		QueryResultIterable<Key<?>> it = iterables.get(cls);
		for (Key<?> rawKey : it) {
			@SuppressWarnings("unchecked")
			Key<T> key = (Key<T>) rawKey;
			keys.add(key);
		}
		return keys;
	}

	/**
	 * Fetches a single result from a given query kind.
	 * 
	 * @param cls
	 *            The query kind.
	 * @param key
	 *            The entity key to retrieve.
	 * @return The entity object.
	 */
	public <T> T fetch(Class<T> cls, Key<T> key) {
		Map<Key<T>, T> classResults = fetch(cls);
		if (classResults != null && classResults.containsKey(key)) {
			return classResults.get(key);
		}
		return null;
	}
}
