package jun.learn.foundation.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestInputStream {
	public static void main(String[] args) {
		
		try {
			
			String path = TestInputStream.class.getResource("").getFile();
			// String path = TestInputStream.class.getResource("/com/my/io").getPath();
			// ��js�ļ���utf-8����
			FileInputStream fis_utf8 = new FileInputStream(new File(path + "./utf-8.js"));
			
			// ��js�ļ���gbk����
			FileInputStream fis_gbk = new FileInputStream(new File(path + "./gbk.js"));
			
			// ��js�ļ�����ĸ    
			// ��Ļ�����ڱ�������
			// byte����һֱû�иı�, ��Ӧascii���
			FileInputStream fis_iso = new FileInputStream(new File(path + "./m.js"));
			
			byte[] b = new byte[9];
			
			// ���ֽ������� ������  byte����
			// �����ļ������Ѷ����Ʊ�ʾ
			// �ļ������ʽ,���ļ���һ������,�ļ��ж��������ʽ�ڱ�дʱ���Ѿ�ȷ��
			int len = fis_utf8.read(b);
			// ���ظû�����ռ�ռ�ĳ���
			System.out.println(len);
			
			// ���д�ӡbyte����
			print(b);
			
			// System.out.println(len);
			
			// ISO-8859-1 ����ı�byte����Ľṹ
			// ISO-8859-1 ���ܱ�ʾ����
			String ISOStr = new String(b, "ISO-8859-1");
			
			// utf��������׸ı�ԭbyte����Ľṹ����Ϊ���ṹ������
			String utfStr = new String(b, "utf-8");
			
			String gbkStr = new String(b, "gbk");
			
			System.out.println("gbStr = " + gbkStr);
			
			System.out.println("utfStr = " + utfStr);
			
			print(ISOStr.getBytes("ISO-8859-1"));   // === print(b)
			
			print(utfStr.getBytes("utf-8"));		// ���Ϊutf-8������string ��ȡ��utf-8�ֽڿ��ܲ����� print(b)
			
			print(gbkStr.getBytes("gbk"));			// ���Ϊgbk������string ��ȡ��gbk�ֽ�   === print(b)
			
			
			utfStr = new String(utfStr.getBytes("utf-8"), "gbk");
			System.out.println("utf-gbk ------" + utfStr);
			gbkStr = new String(gbkStr.getBytes("gbk"), "utf-8");
			System.out.println("gbk-utf ------" + gbkStr);
			
			String ISOStr_t = new String(ISOStr.getBytes("ISO-8859-1"), "utf-8");
			System.out.println("iso-> utf-8----------" + ISOStr_t);
			
			ISOStr = new String(ISOStr.getBytes("ISO-8859-1"), "gbk");
			System.out.println("iso-> gbk----------" + ISOStr);
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void print(byte[] b) {
		for (byte a : b) {
			System.out.print(a + ",");
		}
		System.out.println();
	}
}
