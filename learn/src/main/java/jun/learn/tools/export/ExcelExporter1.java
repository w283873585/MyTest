package jun.learn.tools.export;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelExporter1 implements Exporter{
	private ExcelWorkbook workbook = null;
	
	private String title;
	private Filter filter;
	private DataPrivider privider;
	
	public ExcelExporter1(String title, DataPrivider privider, Filter filter) {
		this.title = title;
		this.filter = filter;
		this.privider = privider;
	}
	
	@Override
	public void initalize() {
		workbook.initalize();
	}

	@Override
	public void assemble() {
		workbook.setTitle(title);
		
		List<List<Object>> data = privider.getData(Convertor.getInstance());
        
        for (int i = 1; i < data.size() + 1; i++) {
        	List<Object> list = data.get(i);
        	for (int j = 0; j < list.size(); j++) {
                String val = filter != null ? 
                		 filter.filter(i, list.get(j)) : list.get(j).toString();
                workbook.addCellVal(val, j == 0);
        	}
        }
	}
	
	public void write(OutputStream out) {
		workbook.write(out);
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