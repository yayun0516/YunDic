package org.lxh.demo;


public class Status1 {
	private int errNum;
	private String errMsg;
	RetData2 retData;//保证变量名与返回JSON的标签名一致，注意！！！！！！！！
	@Override
	public String toString() {
		return "Status [errNum=" + errNum + ", errMsg=" + errMsg + ", retData="
				+ retData + "]";
	}
	public int getErrNum() {
		return errNum;
	}
	public void setErrNum(int errNum) {
		this.errNum = errNum;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public RetData2 getRetData() {
		return retData;
	}
	public void setRetData(RetData2 retData) {
		this.retData = retData;
	}
	

	
	


}
