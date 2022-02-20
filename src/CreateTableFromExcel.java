import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Service.XSSFSheetSerivce;

public class CreateTableFromExcel {

	public static void main(String[] args) {

		XSSFSheetSerivce xssfSheetSerivce = new XSSFSheetSerivce();
		/** Get file */
		XSSFWorkbook book = new XSSFWorkbook();
		try {
			book = new XSSFWorkbook("TableSchema.xlsx");
			book.close();
			System.out.println(xssfSheetSerivce.getCreateTableSQL(book));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}