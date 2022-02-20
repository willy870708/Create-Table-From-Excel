package Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Model.ColumnInfo;
import Model.IndexKey;
import Model.PrimaryKey;
import Model.TableInfo;

public class XSSFSheetSerivce {
	/** Max row number of Excel */
	static final int ROW_MAX = 1048576;

	/** Row number of table name */
	static final int ROW_TABLE_NAME = 1;

	/** Column number of table name */
	static final int COLUMN_TABLE_NAME = 1;

	/** Row number of table schema */
	static final int ROW_TABLE_SCHEMA = 0;

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

	/**
	 * 
	 * @param tableInfo
	 * @return String
	 * @remark generate create table SQL
	 */
	public String getCreateTableSQL(XSSFWorkbook book) {
		/** Get sheet */
		XSSFSheet sheet = book.getSheetAt(0);
		
		/** Set TableInfo field */
		TableInfo tableInfo = getTableInfo(sheet);

		StringBuilder sql = new StringBuilder();

		/** create column and primary constraint */
		sql.append(getCreateColumnSQL(tableInfo));

		/** create index */
		sql.append(getCreateIndexSql(tableInfo));

		/** create column comment */
		sql.append(getCreateComment(tableInfo));

		return sql.toString();
	}

	/**
	 * 
	 * @param sheet
	 * @return List<ColumnInfo>
	 * @remark get Column information list
	 */
	private List<ColumnInfo> getColumnInfoList(XSSFSheet sheet) {
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
	 * @return List<ColumnInfo> @
	 */
	private List<ColumnInfo> getKeyList(XSSFSheet sheet, int columnNumber) {
		List<ColumnInfo> columnInfoList = getColumnInfoList(sheet);
		List<ColumnInfo> keyList = new ArrayList<ColumnInfo>();

		/** Map<Sequence, Row> */
		Map<String, Integer> pkSequenceMap = new HashMap<String, Integer>();
		for (int row = ROW_COLUMN_NAME; row <= columnInfoList.size(); row++) {
			String pkSequence = sheet.getRow(row).getCell(columnNumber).getStringCellValue();

			if (StringUtils.isNotBlank(pkSequence)) {
				pkSequenceMap.put(pkSequence, row);
			}
		}

		for (int seqNum = 1; seqNum <= pkSequenceMap.size(); seqNum++) {
			keyList.add(columnInfoList.get(pkSequenceMap.get(Integer.toString(seqNum)) - 1));
		}

		return keyList;
	}

	/**
	 * 
	 * @param tableInfo
	 * @return StringBuilder
	 * @remark create column and primary constraint
	 */
	private StringBuilder getCreateColumnSQL(TableInfo tableInfo) {
		StringBuilder sql = new StringBuilder();

		sql.append("CREATE TABLE ");
		sql.append(tableInfo.getTableSchema() + "." + tableInfo.getTableName() + "(\n");
		tableInfo.getColunmList().stream().forEach(columnInfo -> {
			sql.append("\t" + columnInfo.getColumnName() + " ");
			sql.append(columnInfo.getDataType());

			if (StringUtils.isNotBlank(columnInfo.getDataLength())) {
				sql.append("(" + columnInfo.getDataLength() + ") ");
			}

			if (StringUtils.isNotBlank(columnInfo.getDataDefault())) {
				sql.append(" DEFAULT ");

				if ("DATE".equalsIgnoreCase(columnInfo.getDataType())
						|| "NUMBER".equalsIgnoreCase(columnInfo.getDataType())) {
					sql.append(columnInfo.getDataDefault() + " ");
				} else {
					sql.append("'" + columnInfo.getDataDefault() + "' ");
				}
			}

			if (!"Y".equals(columnInfo.getNullable())) {
				sql.append("NOT NULL");
			}

			sql.append(", \n");
		});

		/** primary key constraint */
		sql.append("\tCONSTRAINT \"PK_" + tableInfo.getTableName() + "\" ");
		sql.append("PRIMARY KEY (");
		sql.append(tableInfo.getPrimaryKey().getPrimaryKeyList().stream().map(ColumnInfo::getColumnName)
				.collect(Collectors.joining(", ")));
		sql.append(")");
		sql.append("\n);\n\n");

		return sql;
	}

	/**
	 * 
	 * @param tableInfo
	 * @return StringBuilder
	 * @remark create index
	 */
	private StringBuilder getCreateIndexSql(TableInfo tableInfo) {
		StringBuilder sql = new StringBuilder();

		tableInfo.getIndexKeyLists().stream().forEach(indexKey -> {
			sql.append("CREATE INDEX " + tableInfo.getTableSchema() + ".");
			sql.append("IX_" + tableInfo.getTableName() + "_"
					+ String.format("%02d", tableInfo.getIndexKeyLists().indexOf(indexKey) + 1));
			sql.append(" on " + tableInfo.getTableSchema() + "." + tableInfo.getTableName() + " (");
			sql.append(indexKey.getIndexKeyList().stream().map(ColumnInfo::getColumnName)
					.collect(Collectors.joining(", ")));
			sql.append(");\n");
		});

		sql.append("\n");

		return sql;
	}

	/**
	 * 
	 * @param tableInfo
	 * @return StringBuilder
	 * @remark create comment
	 */
	private StringBuilder getCreateComment(TableInfo tableInfo) {
		StringBuilder sql = new StringBuilder();

		tableInfo.getColumnList().stream().forEach(columnInfo -> {
			sql.append("COMMENT ON COLUMN " + tableInfo.getTableSchema() + "." + tableInfo.getTableName());
			sql.append("." + columnInfo.getColumnName() + " is '" + columnInfo.getComments() + "';\n");
		});

		/** table comment */
		sql.append("COMMENT ON TABLE " + tableInfo.getTableSchema() + "." + tableInfo.getTableName());
		sql.append(" is '" + tableInfo.getTableComment() + "';");

		return sql;
	}

	/**
	 * 
	 * @param sheet
	 * @return TableInfo
	 * @remark set tableInfo field
	 */
	private TableInfo getTableInfo(XSSFSheet sheet) {
		TableInfo tableInfo = new TableInfo();

		/** Set table name¡Bschema¡Bcomment */
		tableInfo.setTableName(sheet.getRow(ROW_TABLE_NAME).getCell(COLUMN_TABLE_NAME).getStringCellValue());
		tableInfo.setTableSchema(sheet.getRow(ROW_TABLE_SCHEMA).getCell(COLUMN_TABLE_SCHEMA).getStringCellValue());
		tableInfo.setTableComment(sheet.getRow(ROW_TABLE_COMMENT).getCell(COLUMN_TABLE_COMMENT).getStringCellValue());

		/** Set columns information list */
		tableInfo.setColunmList(getColumnInfoList(sheet));

		/** Set primary key */
		PrimaryKey primaryKey = new PrimaryKey();
		primaryKey.setPrimaryKeyList(getKeyList(sheet, COLUMN_PRIMARY_KEY));
		primaryKey.setPrimaryKeyName(sheet.getRow(0).getCell(COLUMN_PRIMARY_KEY).getStringCellValue());

		tableInfo.setPrimaryKey(primaryKey);

		/** Set index keys */
		List<IndexKey> indexKeyList = new ArrayList<IndexKey>();

		for (int columnNumber = COLUMN_INDEX_KEY_1; columnNumber <= COLUMN_INDEX_KEY_10; columnNumber++) {
			IndexKey indexKey = new IndexKey();
			List<ColumnInfo> indexKeys = getKeyList(sheet, columnNumber);
			if (indexKeys.isEmpty()) {
				break;
			}

			indexKey.setIndexKeyList(indexKeys);
			indexKey.setIndexName(sheet.getRow(0).getCell(columnNumber).getStringCellValue());

			indexKeyList.add(indexKey);
		}

		tableInfo.setIndexKeyLists(indexKeyList);

		return tableInfo;
	}
}