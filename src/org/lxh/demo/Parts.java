package org.lxh.demo;

import java.util.Arrays;

public class Parts {
	private String part;
	public String getParts() {
		return part;
	}
	public void setParts(String parts) {
		this.part = parts;
	}
	public String[] getMeans() {
		return means;
	}
	public void setMeans(String[] means) {
		this.means = means;
	}
	@Override
	public String toString() {
		return "Parts [parts=" + part + ", means=" + Arrays.toString(means)
				+ "]";
	}
	String[] means;
	
	
	

}
