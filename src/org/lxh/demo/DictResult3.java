package org.lxh.demo;

import java.util.List;

public class DictResult3 {
	private String word_name;

	private List<Symbols4> symbols;

	public String getWord_name() {
		return word_name;
	}

	public void setWord_name(String word_name) {
		this.word_name = word_name;
	}

	public List<Symbols4> getSymbols() {
		return symbols;
	}

	public void setSymbols(List<Symbols4> symbols) {
		this.symbols = symbols;
	}

	@Override
	public String toString() {
		return "DictResult [word_name=" + word_name + ", symbols=" + symbols
				+ "]";
	}

}
