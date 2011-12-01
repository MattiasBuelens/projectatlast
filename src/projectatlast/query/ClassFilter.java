package projectatlast.query;

import com.googlecode.objectify.Query;

public class ClassFilter extends Option {
	private static final long serialVersionUID = 1L;

	Class<?> cls;
	
	protected ClassFilter() {}

	public ClassFilter(Class<?> cls) {
		this.cls = cls;
	}

	@Override
	public Class<?> getKind() {
		return cls;
	}

	@Override
	public void apply(Class<?> kind, Query<?> query) { }
}
