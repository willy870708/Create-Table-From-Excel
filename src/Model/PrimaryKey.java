package Model;
import java.util.List;

public class PrimaryKey {
	/** primary key name */
	private String primaryKeyName;
	/** primary keys */
	private List<ColumnInfo> primaryKeys;
	/**
	 * @return the primaryKeyName
	 */
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}
	/**
	 * @param primaryKeyName the primaryKeyName to set
	 */
	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}
	/**
	 * @return the primaryKeyList
	 */
	public List<ColumnInfo> getPrimaryKeyList() {
		return primaryKeys;
	}
	/**
	 * @param primaryKeyList the primaryKeyList to set
	 */
	public void setPrimaryKeyList(List<ColumnInfo> primaryKeyList) {
		this.primaryKeys = primaryKeyList;
	}
}