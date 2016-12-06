package jun.learn.algorithm;

import java.util.*;

public class Radix {
	private int max;
	private int realVal = 0;
	public List<Integer> valueList;
	public Radix(int i){
		max = i;
	}
	
	public List<Integer> getList(){
		return valueList;
	}
	
	public boolean contains(int i){
		return valueList.contains(i);
	}
	
	public int get(int index){
		return valueList.get(index);
	}

	public void add(int value){
		formatListBy(value,10);
		realVal += getValByEx(max);
		formatList();
	}
	
	public void addR(int value){
		realVal  += value;
		formatList();
	}
	
	public void setVal(int value){
		formatListBy(value,10);
		realVal = getValByEx(max);
	}
	
	public void setRVal(int value){
		realVal = value;
		formatList();
	}
	
	private void formatList(){
		formatListBy(realVal,max);
	}
	
	private int getValByEx(int ex){
		int realVal_t = 0; 
		int max_t = 1;
		for(int val : valueList){
			if(val >= ex){
				throw new RuntimeException("�����˷Ƿ���ֵ");
			}
			realVal_t += val * max_t;
			max_t *= ex;
		}
		return realVal_t;
	}
	
	public int getVal(){
		return getValByEx(10);
	}
	
	public int getRVal(){
		return realVal;
	}
	
	public int size(){
		return valueList.size();
	}
	
	private void formatListBy(int value,int radix){
		valueList = new ArrayList<Integer>();
		int flag = 0;
		int radix_t = radix;
		while(flag < 2){
			int temp = value % radix_t / (radix_t/radix);
			valueList.add(temp);
			value -= temp;
			radix_t *= radix;
			if(value < radix_t){
				flag++;
			}
		}
	}
	
	public boolean compare(Radix radix){
		return realVal > radix.realVal;
	}
	
	public static void main(String[] args) {
		Radix r = new Radix(9);
		r.setVal(81*8);
		System.out.println(r.getVal());
		Radix r1 = new Radix(9);
		r1.setVal(81*6);
		System.out.println(r.compare(r1));
	}
}
