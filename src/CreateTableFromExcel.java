import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
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
	static final int ROW_NAME = 1;
	
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
        System.out.println(tableInfo.getColunmList().get(1).getColumnName());
        System.out.println(tableInfo.getColunmList().get(1).getDataType());
        System.out.println(tableInfo.getColunmList().get(1).getDataLength());
        System.out.println(tableInfo.getColunmList().get(1).getNullable());
        System.out.println(tableInfo.getColunmList().get(1).getDataDefault());
        System.out.println(tableInfo.getColunmList().get(1).getComments());
	}
	/**
	 * 
	 * @param sheet
	 * @return List<ColumnInfo>
	 * @remark get Column information list
	 */
	static private List<ColumnInfo> getColumnInfoList(XSSFSheet sheet){
		List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
		
        for(int row = ROW_NAME; row <= ROW_MAX; row++ ) {
        		if(StringUtils.isBlank((sheet.getRow(row).getCell(3).getStringCellValue()))) {
        			break;
        		}
        		ColumnInfo columnInfo = new ColumnInfo();

        		columnInfo.setColumnName(sheet.getRow(row).getCell(COLUMN_NAME).getStringCellValue());
        		columnInfo.setDataType(sheet.getRow(row).getCell(COLUMN_DATA_TYPE).getStringCellValue());
        		columnInfo.setDataLength(String.format("%.0f",sheet.getRow(row).getCell(COLUMN_DATA_LENGTH).getNumericCellValue()));
        		columnInfo.setNullable(sheet.getRow(row).getCell(COLUMN_NULLABLE).getStringCellValue());
        		columnInfo.setDataDefault(sheet.getRow(row).getCell(COLUMN_DATA_DEFAULT).getStringCellValue());
        		columnInfo.setComments(sheet.getRow(row).getCell(COLUMN_COMMENTS).getStringCellValue());
        		
        		columnInfoList.add(columnInfo);
        }
		
		return columnInfoList;
	}

}