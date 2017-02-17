package vr.com.kernel.processor;

import java.util.ArrayList;
import java.util.List;

import vr.com.util.text.SplitUtil;
import vr.com.util.text.SplitUtil.FragProvider;

public class MixinProcessor implements ValueProcessor{
	
	static MixinProcessor newMixinProcess(String expression) {
		return new MixinProcessor(expression);
	}
	
	private String expression;
	public List<ValueProcessor> processors = new ArrayList<ValueProcessor>();
	
	private MixinProcessor(String expression) {
		addProcessors(expression);
	};
	
	private void addProcessors(String expression) {
		this.expression = expression;
		FragProvider provider = SplitUtil.split(expression, ",");
		while (provider.hasNext()) 
			processors.add(ValueProcessorFactory.getProcessor(provider.get()));
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
