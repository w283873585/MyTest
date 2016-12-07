package jun.learn.tools.export;

import java.io.OutputStream;

import jun.learn.tools.export.ExcelExporter.DataPrivider;
import jun.learn.tools.export.ExcelExporter.Filter;



/**
 * 导出表到excel工具类
 * @author xnxs
 *
 * @param 
 */
public class ExportUtil {
	
	public static void export(String title, DataPrivider privider, Filter filter,
			OutputStream out) {
		ExcelExporter util = new ExcelExporter(title, privider, filter);
		util.initalize();
		util.assemble();
		util.write(out);
	}
}
