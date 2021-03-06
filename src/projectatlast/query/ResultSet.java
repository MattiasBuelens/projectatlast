package projectatlast.query;

import java.util.*;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class ResultSet {
	private Objectify ofy;
	private Map<Class<?>, QueryResultIterable<Key<?>>> iterables;
	private Map<Class<?>, Set<?>> keys;
	private Map<Class<?>, Map<?, ?>> results;

	public ResultSet(Objectify ofy) {
		this.ofy = ofy;
		this.iterables = new HashMap<Class<?>, QueryResultIterable<Key<?>>>();
		this.keys = new HashMap<Class<?>, Set<?>>();
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
	 * Check whether the result set contains a query result for a given kind.
	 * 
	 * @param cls
	 *            The query kind.
	 * @return True if the result set contains a query result for the given
	 *         kind.
	 */
	public boolean contains(Class<?> cls) {
		return iterables.containsKey(cls);
	}

	/**
	 * Fetch the result set of all keys from a given query kind.
	 * 
	 * @param cls
	 *            The query kind.
	 * @return Set of entity keys, or an empty set if no such query kind was
	 *         found.
	 */
	public <T> Set<Key<T>> fetchKeys(Class<T> cls) {
		if (!iterables.containsKey(cls))
			return Collections.emptySet();
		if(keys.containsKey(cls)) {
			// Retrieve stored results
			@SuppressWarnings("unchecked")
			Set<Key<T>> classKeys = (Set<Key<T>>) keys.get(cls);
			return classKeys;
		} else {
			// Fetch keys
			Set<Key<T>> classKeys = new LinkedHashSet<Key<T>>();
			QueryResultIterable<Key<?>> it = iterables.get(cls);
			for (Key<?> rawKey : it) {
				Key<T> key = Key.typed(rawKey.getRaw());
				classKeys.add(key);
			}
			// Store keys and return
			keys.put(cls, classKeys);
			return classKeys;
		}
	}

	/**
	 * Fetch the result set from a given query kind.
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

	/**
	 * Fetch a single result from a given query kind.
	 * 
	 * @param cls
	 *            The query kind.
	 * @param key
	 *            The entity key to retrieve.
	 * @return The entity object.
	 */
	public <T> T fetch(Class<T> cls, Key<T> key) {
		Map<Key<T>, T> classResults = fetch(cls);
		if (classResults.containsKey(key)) {
			return classResults.get(key);
		}
		return null;
	}

	/**
	 * Fetch a subset of results from a given query kind.
	 * 
	 * @param cls
	 *            The query kind.
	 * @param keys
	 *            The entity keys to retrieve.
	 * @return Map of entities mapped by their keys.
	 */
	public <T> Map<Key<T>, T> fetch(Class<T> cls, Collection<Key<T>> keys) {
		Map<Key<T>, T> classResults = new LinkedHashMap<Key<T>, T>(fetch(cls));
		classResults.keySet().retainAll(keys);
		return classResults;
	}
}
