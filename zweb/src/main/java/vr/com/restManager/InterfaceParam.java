package vr.com.restManager;

import java.util.List;

import vr.com.kernel.processor.ValueProcessor;

public class InterfaceParam {
	private String name;
	
	private String desc;
	
	private List<ValueProcessor> processors;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<ValueProcessor> getProcessors() {
		return processors;
	}

	public void setProcessors(List<ValueProcessor> processors) {
		this.processors = processors;
	}
}
