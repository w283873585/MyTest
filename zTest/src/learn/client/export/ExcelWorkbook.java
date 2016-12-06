package learn.client.export;

import java.io.OutputStream;


public interface ExcelWorkbook {
	void initalize();
	
	void setTitle(String title);
	
	void addCellVal(String value, boolean newRow);
	
	void write(OutputStream out);
}
