package com.accession.builder;

import com.accession.dto.AccessionRequestObject;

public class AccessionRequestObjectBuilder {
	private String list;
	
	public AccessionRequestObjectBuilder withList(String list) {
		this.list = list;
		return this;
	}
	
	public AccessionRequestObject build() {
		AccessionRequestObject aro = new AccessionRequestObject();
		aro.setList(list);
		return aro;
	}
}
