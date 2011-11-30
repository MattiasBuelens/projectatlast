package projectatlast.query;

import java.util.List;
import java.util.Set;

public interface Groupable<T> {
	public Object getKey();
	public Set<Object> getKeys();
	public Set<Object> getKeys(int depth);
	public List<Groupable<T>> getChildren();
	public List<T> getValues();
	public boolean isLeaf();
	public Groupable<T> group(Group group);
	public <V> Groupable<V> transform(Function<List<T>,List<V>> function);
}
