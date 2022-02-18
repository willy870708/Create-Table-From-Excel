import java.util.ArrayList;
import java.util.List;

public class TableInfo {
	/** table schema */
	private String tableSchema;
	/** table name */
	private String tableName;
	/** table comment */
	private String tableComment;
	/** column list */
	private List<ColumnInfo> columnList = new ArrayList<ColumnInfo>();
	/** primary key */
	private List<ColumnInfo> primaryKey = new ArrayList<ColumnInfo>();
	/** index keys(no more than 10)*/
	private List<List<ColumnInfo>> indexKeyLists = new ArrayList<List<ColumnInfo>>();
	/**
	 * @return the tableSchema
	 */
	public String getTableSchema() {
		return tableSchema;
	}
	/**
	 * @param tableSchema the tableSchema to set
	 */
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the tableComment
	 */
	public String getTableComment() {
		return tableComment;
	}
	/**
	 * @param tableComment the tableComment to set
	 */
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	/**
	 * @return the colunmList
	 */
	public List<ColumnInfo> getColunmList() {
		return columnList;
	}
	/**
	 * @param colunmList the colunmList to set
	 */
	public void setColunmList(List<ColumnInfo> colunmList) {
		this.columnList = colunmList;
	}
	/**
	 * @return the primaryKey
	 */
	public List<ColumnInfo> getPrimaryKey() {
		return primaryKey;
	}
	/**
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(List<ColumnInfo> primaryKey) {
		this.primaryKey = primaryKey;
	}
	/**
	 * @return the indexKeyLists
	 */
	public List<List<ColumnInfo>> getIndexKeyLists() {
		return indexKeyLists;
	}
	/**
	 * @param indexKeyLists the indexKeyLists to set
	 */
	public void setIndexKeyLists(List<List<ColumnInfo>> indexKeyLists) {
		this.indexKeyLists = indexKeyLists;
	}
}