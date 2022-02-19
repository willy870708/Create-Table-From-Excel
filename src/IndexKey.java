import java.util.List;

public class IndexKey {
	/** index name */
	private String indexName;
	/** index keys */
	private List<ColumnInfo> indexKeys;
	/**
	 * @return the indexName
	 */
	public String getIndexName() {
		return indexName;
	}
	/**
	 * @param indexName the indexName to set
	 */
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	/**
	 * @return the indexKeyList
	 */
	public List<ColumnInfo> getIndexKeyList() {
		return indexKeys;
	}
	/**
	 * @param indexKeyList the indexKeyList to set
	 */
	public void setIndexKeyList(List<ColumnInfo> indexKeyList) {
		this.indexKeys = indexKeyList;
	}
}