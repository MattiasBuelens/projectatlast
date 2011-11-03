package projectatlast.data;

public abstract class Finder {

	protected DAO dao;

	public Finder(DAO dao) {
		this.dao = dao;
	}

}
