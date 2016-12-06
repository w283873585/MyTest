package jun.learn.algorithm;

public class TestBF {
	public static boolean match(String str,String targetStr){
		if(str.equals(targetStr)){
			return true;
		}
		String[] arrayStr = str.split("");
		String[] arrayTargetStr = targetStr.split("");
		int length = arrayStr.length;
		int targetLen = arrayTargetStr.length;
		if(length>targetLen){
			return false;
		}else{
			int i = 0+1;
			int j = 0+1;
			int k = 0+1;
			while(i<length&&k+length-1<=targetLen){
				if(arrayStr[i].equals(arrayTargetStr[j])){
					j++;
					i++;
				}else{
					i=0+1;
					k++;
					j=k;
				}
			}
			if(i==length){
				return true;
			}
			
		}
		return false;
	}
	
	public static void main(String[] args) {
		String str = "ababa";
		String targetStr = "ababcabcabaababa";
		System.out.println(match(str, targetStr));
	}
	
}
