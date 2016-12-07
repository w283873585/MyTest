package jun.learn.foundation.io;

import java.io.File;
import java.io.IOException;

public class ProcessFiles {
	public interface Strategy {
		void process(File file);
	}
	
	private Strategy strategy;
	private String ext;
	public ProcessFiles(Strategy strategy, String ext) {
		this.strategy = strategy;
		this.ext = ext;
	}
	
	public void start(String[] args) {
		try {
			if (args.length == 0) {
				processDirectoryTree(new File("."));
			} else {
				for (String arg : args) {
					if (!arg.endsWith("." + ext)) {
						arg += "." + ext;
					}
					strategy.process(
						new File(arg).getCanonicalFile());
				}
			}
		} catch(IOException e) {
			throw new RuntimeException();
		}
	}
	
	public void processDirectoryTree(File root) {
		for (File file : Directory.walk(root, ".*\\." + ext)) {
			strategy.process(file);
		}
	}
}
