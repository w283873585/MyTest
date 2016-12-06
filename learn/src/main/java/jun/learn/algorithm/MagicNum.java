package jun.learn.algorithm;

import java.util.*;

public class MagicNum {
	
	private Radix max;
	private Radix next;
	private Radix current;
	
	public MagicNum(int i){
		
		max = new Radix(i+1);
		max.setVal(getMax(i));
		
		current = new Radix(i+1);
		current.setVal(getMin(i));
		
		next = new Radix(i+1);
		next.setVal(getMin(i));
		loadNext();
	}
	
	public int next(){
		if(hasNext()){
			current.setVal(next.getVal());
		}
		loadNext();
		return current.getVal();
	}
	
	public boolean hasNext(){
		return next!=null;
	}
	
	public List<Integer> getList(){
		List<Integer> list = new ArrayList<Integer>();
		while(hasNext()){
			list.add(next());
		}
		return list;
	}
	
	private void loadNext(){
		next.addR(1);
		while(!isLegal(next) && !next.compare(max)){
			next.addR(1);
		}
		if(next.compare(max)){
			next = null;
		}
	}
	
	private boolean isLegal(Radix radix){
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(radix.getList());
		if(set.size() != radix.size()){
			return false;
		}
		if(radix.contains(0)){
			return false;
		}
		for (int i = 0; i < radix.size(); i++) {
			if(radix.size()-i == radix.get(i)){
				return false;
			}
		}
		return true;
	}
	
	private int getMin(int i){
		int min = 0;
		int count = 1;
		while(i>0){
			min += i * count;
			count *= 10;
			i--;
		}
		return min;
	}
	
	private int getMax(int i){
		int max = 0;
		int count = 1;
		int index = i+1;
		while(i>0){
			max += (index-i) * count;
			count *= 10;
			i--;
		}
		return max;
	}
	
	public static void main(String[] args) {
		System.out.println(new MagicNum(5).getList());
	}
}
