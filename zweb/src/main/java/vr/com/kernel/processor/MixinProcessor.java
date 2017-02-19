package vr.com.kernel.processor;

import java.util.ArrayList;
import java.util.List;

import vr.com.util.text.SplitUtil;
import vr.com.util.text.SplitUtil.FragProvider;

public class MixinProcessor implements ValueProcessor{
	
	static MixinProcessor newMixinProcess(String expression) {
		MixinProcessor mixinProcess = new MixinProcessor(expression);
		mixinProcess.addProcessors(expression);
		return mixinProcess;
	}
	
	private String expression;
	public List<ValueProcessor> processors = new ArrayList<ValueProcessor>();
	
	private MixinProcessor(String expression) {};
	
	private void addProcessors(String expression) {
		this.expression = expression;
		FragProvider provider = SplitUtil.split(expression, ",");
		while (provider.hasNext()) {
			ValueProcessor processor = ValueProcessorFactory.doGetProcessor(provider.get());
			if (processor != null)
				processors.add(processor);
		}
	}
	
	@Override
	public String getName() {
		return expression;
	}

	@Override
	public Object process(String value) {
		Object result = value;
		for (ValueProcessor processor : processors) {
			result = processor.process(result.toString());
		}
		return result;
	}
}
