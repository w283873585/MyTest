package test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Atest {
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(URLEncoder.encode("-1", "UTF-8"));
	}
}
