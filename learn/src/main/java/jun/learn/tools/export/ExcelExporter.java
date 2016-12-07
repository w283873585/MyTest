package jun.learn.tools.export;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelExporter implements Exporter{
	private HSSFCellStyle style;
	private HSSFCellStyle titleStyle;
	private HSSFWorkbook workbook = new HSSFWorkbook();
	
	private String title;
	private Filter filter;
	private DataPrivider privider;
	
	public ExcelExporter(String title, DataPrivider privider, Filter filter) {
		this.title = title;
		this.filter = filter;
		this.privider = privider;
	}
	
	@Override
	public void initalize() {
		// 生成一个样式   
        style = workbook.createCellStyle();   
        // 设置这些样式   
        style.setFillForegroundColor(HSSFColor.GOLD.index);   //设置单元格颜色  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);   //选择用户定义的填充模式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);    //设置下划线，参数是黑线的宽度  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);   //设置左边框  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);    //设置右边框  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);   //设置上边框  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //单元格中的内容水平方向居中
        
        // 生成一个字体   
        HSSFFont font = workbook.createFont();   
        font.setColor(HSSFColor.VIOLET.index); 
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
        // 把字体应用到当前的样式   
        style.setFont(font);   
        // 指定当单元格内容显示不下时自动换行   
        style.setWrapText(false);
        
        // 产生表格标题行   
        // 表头的样式 
        titleStyle = workbook.createCellStyle();// 创建样式对象 
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中 
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中 
        
        // 设置字体 
        HSSFFont titleFont = workbook.createFont(); // 创建字体对象 
        titleFont.setFontHeightInPoints((short) 15); // 设置字体大小 
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体 
        titleStyle.setFont(titleFont); 
	}

	@Override
	public void assemble() {
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为20个字节   
        sheet.setDefaultColumnWidth(20);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));//指定合并区域  
       
        HSSFRow rowHeader = sheet.createRow(0);   
        HSSFCell cellHeader = rowHeader.createCell(0); 
        cellHeader.setCellStyle(titleStyle); 
        cellHeader.setCellValue(new HSSFRichTextString(title));
        
        List<List<Object>> data = privider.getData(Convertor.getInstance());
        
        for (int i = 1; i < data.size() + 1; i++) {
        	List<Object> list = data.get(i);
        	HSSFRow row = sheet.createRow(i);
        	for (int j = 0; j < list.size(); j++) {
        		 HSSFCell cell = row.createCell(j);   
                 if (i == 1) cell.setCellStyle(style);
                 String	val = filter != null ? 
                		 filter.filter(i, list.get(j)) : list.get(j).toString();
                 HSSFRichTextString text = new HSSFRichTextString(val);   
                 cell.setCellValue(text); 
        	}
        }
	}
	
	public void write(OutputStream out) {
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	public static interface DataPrivider{
		public List<List<Object>> getData(Convertor c);
	}
	
	public static class Convertor{
		private static Convertor convertor = new Convertor();
		
		public static Convertor getInstance() {
			return convertor;
		}
		
		private Convertor() {}
		
		public List<List<Object>> convertBean(List<Object> beanList, String[] propertys) {
			List<List<Object>> result = new ArrayList<List<Object>>();
			for (Object obj : beanList) {
				result.add(convertBean(obj, propertys));
			}
			return result;
		}
		
		public List<List<Object>> convertMap(List<Map<String, Object>> beanList, String[] propertys) {
			List<List<Object>> result = new ArrayList<List<Object>>();
			for (Map<String, Object> obj : beanList) {
				result.add(convertMap(obj, propertys));
			}
			return result;
		}
		
		private List<Object> convertBean(Object obj, String[] propertys) {
			List<Object> list = new ArrayList<Object>();
			Class<?> clazz = obj.getClass();
			for (String name : propertys) {
				try {
					Field field = clazz.getField(name);
					field.setAccessible(true);
					list.add(field.get(obj));
				} catch (Exception e) {
					list.add("");
				}
			}
			return list;
		}
		
		private List<Object> convertMap(Map<String, Object> map, String[] propertys) {
			List<Object> list = new ArrayList<Object>();
			for (String name : propertys) {
				list.add(map.get(name));
			}
			return list;
		}
	}
	
	public static interface Filter{
		public String filter(int index, Object c);
	}
}