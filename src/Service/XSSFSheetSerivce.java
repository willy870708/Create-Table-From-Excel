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
import Model.Location;
import Model.PrimaryKey;
import Model.TableInfo;

public class XSSFSheetSerivce {
	/** Max row number of Excel */
	static final int ROW_MAX = 1048576;

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

		for (int row = Location.COLUNM_NAME.getRow() + 1; row <= ROW_MAX; row++) {
			if (StringUtils.isBlank((sheet.getRow(row).getCell(Location.COLUNM_NAME.getCell()).getStringCellValue()))) {
				break;
			}

			ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setColumnName(sheet.getRow(row).getCell(Location.COLUNM_NAME.getCell()).getStringCellValue());
			columnInfo.setDataType(sheet.getRow(row).getCell(Location.DATA_TYPE.getCell()).getStringCellValue());
			columnInfo.setDataLength(sheet.getRow(row).getCell(Location.DATA_LENGTH.getCell()).getStringCellValue());
			columnInfo.setNullable(sheet.getRow(row).getCell(Location.NULLABLE.getCell()).getStringCellValue());
			columnInfo.setDataDefault(sheet.getRow(row).getCell(Location.DATA_DEFAULT.getCell()).getStringCellValue());
			columnInfo.setComments(sheet.getRow(row).getCell(Location.COMMENTS.getCell()).getStringCellValue());

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
		for (int row = Location.COLUNM_NAME.getRow() + 1; row <= columnInfoList.size(); row++) {
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
		tableInfo.setTableName(
				sheet.getRow(Location.TABLE_NAME.getRow()).getCell(Location.TABLE_NAME.getCell()).getStringCellValue());
		tableInfo.setTableSchema(sheet.getRow(Location.SCHEMA_NAME.getRow()).getCell(Location.SCHEMA_NAME.getCell())
				.getStringCellValue());
		tableInfo.setTableComment(sheet.getRow(Location.TABLE_COMMENT.getRow())
				.getCell(Location.TABLE_COMMENT.getCell()).getStringCellValue());

		/** Set columns information list */
		tableInfo.setColunmList(getColumnInfoList(sheet));

		/** Set primary key */
		PrimaryKey primaryKey = new PrimaryKey();
		primaryKey.setPrimaryKeyList(getKeyList(sheet, Location.PRIMARY_KEY.getCell()));
		primaryKey.setPrimaryKeyName(sheet.getRow(Location.PRIMARY_KEY.getRow()).getCell(Location.PRIMARY_KEY.getCell())
				.getStringCellValue());

		tableInfo.setPrimaryKey(primaryKey);

		/** Set index keys */
		List<IndexKey> indexKeyList = new ArrayList<IndexKey>();

		for (int columnNumber = Location.INDEX_01.getCell(); columnNumber <= Location.INDEX_10
				.getCell(); columnNumber++) {
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