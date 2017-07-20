package com.accession.factory;

import com.accession.dto.AccessionResponseObject;

public class AccessionResponseObjectFactory {
	public static AccessionResponseObject getAccessionResponseObject(String numbers) {
		AccessionResponseObject aro = new AccessionResponseObject();
		aro.setList(numbers.replaceAll(",", ", "));
		return aro;
	}
}