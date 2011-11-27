package projectatlast.query;

import java.util.List;

public interface Groupable<T> {
	public Object getKey();
	public List<Groupable<T>> getChildren();
	public List<T> getValues();
	public boolean isLeaf();
	public Groupable<T> group(Group group);
	public <V> Groupable<V> transform(Function<List<T>,List<V>> function);
}
