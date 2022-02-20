package Model;

public enum Location {
	SCHEMA_NAME(0, 1),
    TABLE_NAME(1, 1),
    TABLE_COMMENT(2,1),
    COLUNM_NAME(0, 3),
    DATA_TYPE(0, 4),
    DATA_LENGTH(0, 5),
	NULLABLE(0, 6),
	DATA_DEFAULT(0, 7),
	COMMENTS(0, 8),
	PRIMARY_KEY(0, 9),
	INDEX_01(0, 10),
	INDEX_02(0, 11),
	INDEX_03(0, 12),
	INDEX_04(0, 13),
	INDEX_05(0, 14),
	INDEX_06(0, 15),
	INDEX_07(0, 16),
	INDEX_08(0, 17),
	INDEX_09(0, 18),
	INDEX_10(0, 19);
	
    private final int ROW;
    private final int CELL;
	Location(int row, int cell){
		ROW = row;
		CELL = cell;
	}
	
	public int getRow() {
		return ROW;
	}
	
	public int getCell() {
		return CELL;
	}
}