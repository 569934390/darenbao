package com.compses.common.converter.vo;

import java.util.List;

public class Response {
	private Diameter diameter;
	
	private List<Avp> avps;
	public Diameter getDiameter() {
		return diameter;
	}
	public void setDiameter(Diameter diameter) {
		this.diameter = diameter;
	}
	public List<Avp> getAvps() {
		return avps;
	}
	public void setAvps(List<Avp> avps) {
		this.avps = avps;
	}
	
}
