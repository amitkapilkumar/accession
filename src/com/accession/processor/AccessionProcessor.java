package com.accession.processor;

import java.util.List;

import com.accession.exception.AccessionNumberFormatException;

public interface AccessionProcessor {

	String processAsOrderedList(List<String> numbers) throws AccessionNumberFormatException;
	
}
