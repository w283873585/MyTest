package learn.file.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;



public class ReplaceUtil {
	public void replaceAll(String filePath, String expression) throws IOException {
		String readContent = null;
		StringBuilder needSave = new StringBuilder();
		
		InputStream in = new FileInputStream(new File(filePath));
		InputStreamReader ir = new InputStreamReader(in);
		BufferedReader reader = new BufferedReader(ir);
		while ((readContent = reader.readLine()) != null) {
			if (!readContent.matches(expression)) {
				needSave.append(readContent + "\n");
			}
		}
		reader.close();
		
		OutputStream out = new FileOutputStream(filePath);
		out.write(needSave.toString().getBytes("utf-8"));
		out.close();
	}
}
