package projectatlast.query;

import com.googlecode.objectify.Query;

public class KindFilter extends Option {
	private static final long serialVersionUID = 1L;

	Class<?> kind;
	
	protected KindFilter() {}

	public KindFilter(Class<?> kind) {
		this.kind = kind;
	}

	@Override
	public Class<?> getKind() {
		return kind;
	}

	@Override
	public void apply(Class<?> kind, Query<?> query) { }
}
