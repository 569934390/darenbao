package com.compses.common.converter.vo;

import java.util.ArrayList;
import java.util.List;

public class Config {
	
	private List<Dcc> msgs = new ArrayList<Dcc>();

	public List<Dcc> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<Dcc> msgs) {
		this.msgs = msgs;
	}
	private StringBuffer requestTxt;
	private StringBuffer responseTxt;

	public StringBuffer getRequestTxt() {
		return requestTxt;
	}

	public void setRequestTxt(StringBuffer requestTxt) {
		this.requestTxt = requestTxt;
	}

	public StringBuffer getResponseTxt() {
		return responseTxt;
	}

	public void setResponseTxt(StringBuffer responseTxt) {
		this.responseTxt = responseTxt;
	}

}
