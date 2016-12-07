package jun.learn.tools.export;

import java.io.OutputStream;

public interface Exporter {
	
	void initalize();
	
	void assemble();
	
	void write(OutputStream out);
	
	// 初始化
	// 装配数据
	// 导出内容
}
