package projectatlast.graph;

public enum GraphType {

	COLUMN("pie"), BAR("bar"), PIE("pie"), LINE("line"), SCATTER("scatter");

	private String highchartsForm;

	private GraphType(String highchartsForm) {
		this.highchartsForm = highchartsForm;
	}

	/**
	 * Retrieve the HighCharts from of this graph type.
	 * 
	 * @return The HighCharts form.
	 */
	public String highchartsForm() {
		return highchartsForm;
	}

	/**
	 * Retrieve the graph type with the given identifier.
	 * 
	 * @param id
	 *            The identifier.
	 * @return The graph type.
	 */
	public static GraphType fromId(String id) {
		try {
			return GraphType.valueOf(id.toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}
}
