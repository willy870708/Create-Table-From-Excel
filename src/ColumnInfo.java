
public class ColumnInfo {
	/** colunm name*/
	private String columnName;
	/** colunm type*/
	private String columnType;
	/** nullable*/
	private String nullable;
	/**
	 * @return the colunmName
	 */
	public String getColunmName() {
		return columnName;
	}
	/**
	 * @param colunmName the colunmName to set
	 */
	public void setColunmName(String colunmName) {
		this.columnName = colunmName;
	}
	/**
	 * @return the colunmType
	 */
	public String getColunmType() {
		return columnType;
	}
	/**
	 * @param colunmType the colunmType to set
	 */
	public void setColunmType(String colunmType) {
		this.columnType = colunmType;
	}
	/**
	 * @return the nullable
	 */
	public String getNullable() {
		return nullable;
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
	/** data default*/
	private String dataDefault;
	/** comments*/
	private String comments;
}