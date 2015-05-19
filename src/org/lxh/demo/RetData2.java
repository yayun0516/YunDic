package org.lxh.demo;


public class RetData2 {
	private String from;
	private String to;
	DictResult3 dict_result;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public DictResult3 getDictResult() {
		return dict_result;
	}
	public void setDictResult(DictResult3 dictResult) {
		this.dict_result = dictResult;
	}
	@Override
	public String toString() {
		return "RetData [from=" + from + ", to=" + to + ", dictResult="
				+ dict_result + "]";
	}
	

	
}
