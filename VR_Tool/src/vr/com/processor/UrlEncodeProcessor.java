package vr.com.processor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEncodeProcessor implements ValueProcessor{
	private String charset = "utf-8";
	
	@Override
	public String process(String value) {
		try {
			return URLEncoder.encode(value, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getName() {
		return "urlencode";
	}
}
