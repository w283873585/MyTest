package jun.learn.algorithm;

public class TestMe {
	private String[] saStr;
	private String[] laStr;
	private int[] next;
	private int nextSize;
	
	public int getI(String sStr,String lStr){
		
		this.saStr = getStrArray(sStr);
		this.laStr = getStrArray(lStr);
		int sLength = saStr.length;
		int lLength = laStr.length;
		this.next = new int[lLength];
		int m = 0;
		for(int l=-1;l<next.length-1;l++){
			next[m] = l;
			m++;
		}
		nextSize = lLength;
		
		//int i = 0;
		int j = 0;
		while(j<sLength){
			
			getInt(j);
			if(nextSize==0){
				return -1;
			}
			//i = next[0];
			j++;
			
		}
		if(nextSize!=0&&j==sLength){
			return next[0]-sLength+1;
		}
		
		return -1;
	}
	
	
	public void getInt(int j){
		int z = 0;
		for(int k=0;k<nextSize;k++){
			
			int index = next[k]+1;
			if(index+saStr.length-j>laStr.length)continue;
			if(saStr[j].equals(laStr[index])){
				next[z]=index;
				z++;
			}
		}
		nextSize = z;
//		String str1 = "";
//		for(int o=0;o<nextSize;o++){
//			str1 = str1 +"-"+ next[o];
//		}
//		System.out.println(str1);
		
	}
	
	public String[] getStrArray(String str){
		
		String strA[] = str.split("");
		String strB[] = new String[strA.length-1];
		for(int i=1;i<strA.length;i++){
			strB[i-1] = strA[i];
		}
		return strB;
	}
	
	public static void main(String[] args) {
		String sStr = "abcdef";
		String lStr = "ababcababcdefbaba";
		int a = new TestMe().getI(sStr, lStr);
		System.out.println(a);
	}
}
