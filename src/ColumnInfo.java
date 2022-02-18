
public class ColumnInfo {
	/** column name*/
	private String columnName;
	/** data type*/
	private String dataType;
	/** nullable*/
	private String nullable;
	/** data default*/
	private String dataDefault;
	/** comments*/
	private String comments;
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param colunmName the colunmName to set
	 */
	public void setColumnName(String colunmName) {
		this.columnName = colunmName;
	}
	/**
	 * @return the colunmType
	 */
	public String getColunmType() {
		return dataType;
	}
	/**
	 * @param colunmType the colunmType to set
	 */
	public void setColunmType(String colunmType) {
		this.dataType = colunmType;
	}
	/**
	 * @return the nullable
	 */
	public String getNullable() {
		return nullable;
	}
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * @param nullable the nullable to set
	 */
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	/**
	 * @return the dataDefault
	 */
	public String getDataDefault() {
		return dataDefault;
	}
	/**
	 * @param dataDefault the dataDefault to set
	 */
	public void setDataDefault(String dataDefault) {
		this.dataDefault = dataDefault;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}