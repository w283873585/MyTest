package jun.learn.foundation.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;


/**
 * DirFilter����ڵ�Ψһԭ������ṩaccept������
 * ����������Ŀ�����ڰ�accept�����ṩ��list()ʹ��
 * ʹ��list() ���Իص�accept()
 * �������˲�ƥ����ļ�.
 * ������ֽṹҲ������Ϊ�ص�
 * �������˵������һ������ģʽ������
 * ��Ϊlist()ʵ�ֻ����Ĺ��ܣ����Ұ���FilenameFilter����ʽ�ṩ���������
 * �Ա�����list()���ṩ��������Ҫ���㷨
 * @author lenovo
 *
 */
public class DirFilter implements FilenameFilter{

	/**
	 * File.list() Դ��
	public String[] list(FilenameFilter filter){
		String names[] = list();
        if ((names == null) || (filter == null)) {
            return names;
        }
        List<String> v = new ArrayList<>();
        for (int i = 0 ; i < names.length ; i++) {
            
        	@AttentionHere �˴�����FilenameFilter�����ṩ�ķ���
        	if (filter.accept(this, names[i])) {
                v.add(names[i]);
            }
        }
        return v.toArray(new String[v.size()]);
	}
	*/
	private Pattern pattern;
	
	public DirFilter(String regex) {
		this.pattern = Pattern.compile(regex);
	}
	
	@Override
	public boolean accept(File dir, String name) {
		System.out.println(dir);
		return pattern.matcher(name).matches();
	}
	
	
	public static void main(String[] args) {
		File path = new File(".");
		String[] list;
		if (args.length == 0) {
			list = path.list();
		} else {
			list = path.list(new DirFilter(args[0]));
		}
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for (String str : list) {
			System.out.println(str);
		}
	}
	
	

}
