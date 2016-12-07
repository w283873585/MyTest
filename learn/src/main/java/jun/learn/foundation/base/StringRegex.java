package jun.learn.foundation.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRegex {
	public static void main(String[] args) {
		String str = "GET /VR_Show HTTP/1.1" + 
			"Host: localhost:8080" + 
			"Connection: keep-alive" + 
			"Cache-Control: max-age=0" + 
			"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" + 
			"Upgrade-Insecure-Requests: 1" + 
			"User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36" + 
			"Accept-Encoding: gzip, deflate, sdch" + 
			"Accept-Language: zh-CN,zh;q=0.8" + 
			"Cookie: VR_SHOW_CLIENT_EQU_applewebkit=a928dd95f63a6e08f7f6968d09b9feb1; CNZZDATA1257784839=1152565229-1462339825-%7C1462773187; Hm_lvt_8436df8948b048054776dbfc961196fe=1462587547,1462612793,1462759398,1462773188; Hm_lpvt_8436df8948b048054776dbfc961196fe=1462773274";
		
		Pattern patt = Pattern.compile("\\s([\\S]*)\\s+HTTP.*", Pattern.MULTILINE);
		/**
			An engine that performs match operations on a character sequence by interpreting a Pattern. 

			A matcher is created from a pattern by invoking the pattern's matcher method. Once created, a matcher can be used to perform three different kinds of match operations: 
			
			• The matches method attempts to match the entire input sequence against the pattern. 
			
			• The lookingAt method attempts to match the input sequence, starting at the beginning, against the pattern. 
			
			• The find method scans the input sequence looking for the next subsequence that matches the pattern. 
			
			Each of these methods returns a boolean indicating success or failure. More information about a successful match can be obtained by querying the state of the matcher. 
			
			A matcher finds matches in a subset of its input called the region. By default, the region contains all of the matcher's input. The region can be modified via theregion method and queried via the regionStart and regionEnd methods. The way that the region boundaries interact with some pattern constructs can be changed. See useAnchoringBounds and useTransparentBounds for more details. 
			
			This class also defines methods for replacing matched subsequences with new strings whose contents can, if desired, be computed from the match result. The appendReplacement and appendTail methods can be used in tandem in order to collect the result into an existing string buffer, or the more convenient replaceAll method can be used to create a string in which every matching subsequence in the input sequence is replaced. 
			
			The explicit state of a matcher includes the start and end indices of the most recent successful match. It also includes the start and end indices of the input subsequence captured by each capturing group in the pattern as well as a total count of such subsequences. As a convenience, methods are also provided for returning these captured subsequences in string form. 
			
			The explicit state of a matcher is initially undefined; attempting to query any part of it before a successful match will cause an IllegalStateException to be thrown. The explicit state of a matcher is recomputed by every match operation. 
			
			The implicit state of a matcher includes the input character sequence as well as the append position, which is initially zero and is updated by the appendReplacement method. 
			
			A matcher may be reset explicitly by invoking its reset() method or, if a new input sequence is desired, its reset(CharSequence) method. Resetting a matcher discards its explicit state information and sets the append position to zero. 
			
			Instances of this class are not safe for use by multiple concurrent threads. 
		*/
		Matcher matcher = patt.matcher(str);
		
		matcher.region(2, str.length());
		boolean a = matcher.lookingAt();
		System.out.println("lookingAt->" + a);
		
		boolean b = matcher.find();
		System.out.println("find->" + b);
		
		System.out.println(matcher.group(1));
	}
}
