package vr.com.util.text;

public class SplitUtil {
	
	public static FragProvider split(String origin, String separate) {
		return new FragProvider(origin, separate);
	}
	
	public static FragProvider split(String origin, String separate, int model) {
		return new FragProvider(origin, separate, model);
	}
	
	public static class FragProvider{
		public static final int skipEmpty = 1;
		public static final int returnEmptyForOverstep = 2;
		
		@SuppressWarnings("unused")
		private int model;
		private int index;
		private String body[];
		
		public FragProvider(String origin, String separate, int model){
			this.body = origin.split(separate);
			this.model = model;
		}
		
		public FragProvider(String origin, String separate){
			this(origin, separate, 1);
		}
		
		public String get() {
			if (index < body.length) {
				String current = body[index++];
				return isModel(skipEmpty) && isEmpty(current) ? get() : current;
			}
			
			if (isModel(returnEmptyForOverstep)) 
				return "";
			
			throw new NullPointerException();
		}
		
		public int size() {
			return body.length;
		}
		
		public boolean hasNext() {
			return index < body.length;
		}
		
		private boolean isModel(int model) {
			return (model & returnEmptyForOverstep) == returnEmptyForOverstep;
		}
		
		private boolean isEmpty(String str) {
			return "".equals(str);
		}
	}
}
