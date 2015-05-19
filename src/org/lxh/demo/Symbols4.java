package org.lxh.demo;

import java.util.List;

public class Symbols4 {
	private String ph_am;
	private String ph_en;
	private List<Parts> parts;
	
	public String getPh_am() {
		return ph_am;
	}
	public void setPh_am(String ph_am) {
		this.ph_am = ph_am;
	}
	public String getPh_en() {
		return ph_en;
	}
	public void setPh_en(String ph_en) {
		this.ph_en = ph_en;
	}
	public List<Parts> getParts() {
		return parts;
	}
	public void setParts(List<Parts> parts) {
		this.parts = parts;
	}
	@Override
	public String toString() {
		return "Symbols [ph_am=" + ph_am + ", ph_en=" + ph_en + ", parts="
				+ parts + "]";
	}
	

}
