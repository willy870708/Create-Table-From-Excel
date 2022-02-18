import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;


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
	static final int COLUMN_COLUMN_NAME = 3;
	
	/** Row number of comments */
	static final int ROW_COLUMN_COMMENTS = 1;
	
	/** Column number of comments */
	static final int COLUMN_COLUMN_COMMENTS = 7;

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

        
        System.out.println(tableInfo.getTableName());
        System.out.println(tableInfo.getTableSchema());
        System.out.println(tableInfo.getTableComment());
        
	}
	/**
	 * 
	 * @param sheet
	 * @return List<ColumnInfo>
	 * @remark get Column information list
	 */
	static private List<ColumnInfo> getColumnInfoList(XSSFSheet sheet){
		List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
		
        for(int row = ROW_COLUMN_NAME; row <= ROW_MAX; row++ ) {
        	for(int column = ROW_COLUMN_NAME; column <= COLUMN_COLUMN_COMMENTS; column++) {
        		if(StringUtils.isBlank((sheet.getRow(row).getCell(column).getStringCellValue()))) {
        			break;
        		}
        		
        	}
        }
		
		return columnInfoList;
	}

}
