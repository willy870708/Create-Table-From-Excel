import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateTableFromExcel {
	/** Max row number of Excel */
	static final int ROW_MAX = 1048576;

	/** Row number of table name */
	static final int ROW_TABLE_NAME = 0;

	/** Column number of table name */
	static final int COLUMN_TABLE_NAME = 1;

	/** Row number of table schema */
	static final int ROW_TABLE_SCHEMA = 1;

	/** Column number of table schema */
	static final int COLUMN_TABLE_SCHEMA = 1;

	/** Row number of table comment */
	static final int ROW_TABLE_COMMENT = 2;

	/** Column number of comments */
	static final int COLUMN_TABLE_COMMENT = 1;

	/** Row number of comments */
	static final int ROW_COLUMN_NAME = 1;

	/** Column number of column name */
	static final int COLUMN_NAME = 3;

	/** Column number of data type */
	static final int COLUMN_DATA_TYPE = 4;

	/** Column number of data length */
	static final int COLUMN_DATA_LENGTH = 5;

	/** Column number of nullable */
	static final int COLUMN_NULLABLE = 6;

	/** Column number of data default */
	static final int COLUMN_DATA_DEFAULT = 7;

	/** Column number of comments */
	static final int COLUMN_COMMENTS = 8;

	/** Column number of primary key */
	static final int COLUMN_PRIMARY_KEY = 9;

	/** Column number of index key 1 */
	static final int COLUMN_INDEX_KEY_1 = 10;

	/** Column number of index key 2 */
	static final int COLUMN_INDEX_KEY_2 = 11;

	/** Column number of index key 3 */
	static final int COLUMN_INDEX_KEY_3 = 12;

	/** Column number of index key 4 */
	static final int COLUMN_INDEX_KEY_4 = 13;

	/** Column number of index key 5 */
	static final int COLUMN_INDEX_KEY_5 = 14;

	/** Column number of index key 6 */
	static final int COLUMN_INDEX_KEY_6 = 15;

	/** Column number of index key 7 */
	static final int COLUMN_INDEX_KEY_7 = 16;

	/** Column number of index key 8 */
	static final int COLUMN_INDEX_KEY_8 = 17;

	/** Column number of index key 9 */
	static final int COLUMN_INDEX_KEY_9 = 18;

	/** Column number of index key 1 */
	static final int COLUMN_INDEX_KEY_10 = 19;

	public static void main(String[] args) {

		/** Get file */
		XSSFWorkbook book = new XSSFWorkbook();
		try {
			book = new XSSFWorkbook("TableSchema.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/** Get sheet */
		XSSFSheet sheet = book.getSheetAt(0);

		/** Get Table information */
		TableInfo tableInfo = new TableInfo();

		/** Set table name¡Bschema¡Bcomment */
		tableInfo.setTableName(sheet.getRow(ROW_TABLE_NAME).getCell(COLUMN_TABLE_NAME).getStringCellValue());
		tableInfo.setTableSchema(sheet.getRow(ROW_TABLE_SCHEMA).getCell(COLUMN_TABLE_SCHEMA).getStringCellValue());
		tableInfo.setTableComment(sheet.getRow(ROW_TABLE_COMMENT).getCell(COLUMN_TABLE_COMMENT).getStringCellValue());

		/** Set columns information list */
		tableInfo.setColunmList(getColumnInfoList(sheet));

		/** Set primary key */
		tableInfo.setPrimaryKey(getKey(sheet, COLUMN_PRIMARY_KEY));

		/** Set index keys */
		List<List<ColumnInfo>> indexKeyLists = new ArrayList<List<ColumnInfo>>();

		for (int columnNumber = COLUMN_INDEX_KEY_1; columnNumber <= COLUMN_INDEX_KEY_10; columnNumber++) {
			List<ColumnInfo> indexKeys = getKey(sheet, columnNumber);
			if (indexKeys.isEmpty()) {
				break;
			}

			indexKeyLists.add(indexKeys);
		}

		tableInfo.setIndexKeyLists(indexKeyLists);

	}

	/**
	 * 
	 * @param sheet
	 * @return List<ColumnInfo>
	 * @remark get Column information list
	 */
	static private List<ColumnInfo> getColumnInfoList(XSSFSheet sheet) {
		List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();

		for (int row = ROW_COLUMN_NAME; row <= ROW_MAX; row++) {
			if (StringUtils.isBlank((sheet.getRow(row).getCell(3).getStringCellValue()))) {
				break;
			}

			ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setColumnName(sheet.getRow(row).getCell(COLUMN_NAME).getStringCellValue());
    		columnInfo.setDataType(sheet.getRow(row).getCell(COLUMN_DATA_TYPE).getStringCellValue());
    		columnInfo.setDataLength(sheet.getRow(row).getCell(COLUMN_DATA_LENGTH).getStringCellValue());
    		columnInfo.setNullable(sheet.getRow(row).getCell(COLUMN_NULLABLE).getStringCellValue());
    		columnInfo.setDataDefault(sheet.getRow(row).getCell(COLUMN_DATA_DEFAULT).getStringCellValue());
    		columnInfo.setComments(sheet.getRow(row).getCell(COLUMN_COMMENTS).getStringCellValue());
    		
			columnInfoList.add(columnInfo);
		}

		return columnInfoList;
	}

	/**
	 * 
	 * @param sheet
	 * @param columnNumber
	 * @return List<ColumnInfo>
	 * @
	 */
	static private List<ColumnInfo> getKey(XSSFSheet sheet, int columnNumber) {
		List<ColumnInfo> primaryKey = new ArrayList<ColumnInfo>();
		List<ColumnInfo> columnInfoList = getColumnInfoList(sheet);

		/** Map<Sequence, Row> */
		Map<String, Integer> pkSequenceMap = new HashMap<String, Integer>();
		for (int row = ROW_COLUMN_NAME; row <= columnInfoList.size(); row++) {
			String pkSequence = sheet.getRow(row).getCell(columnNumber).getStringCellValue();

			if (StringUtils.isNotBlank(pkSequence)) {
				pkSequenceMap.put(pkSequence, row);
			}
		}

		for (int seqNum = 1; seqNum <= pkSequenceMap.size(); seqNum++) {
			primaryKey.add(columnInfoList.get(pkSequenceMap.get(Integer.toString(seqNum)) - 1));
		}

		return primaryKey;
	}

}