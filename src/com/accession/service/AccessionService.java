package com.accession.service;

import com.accession.dto.AccessionResponseObject;
import com.accession.exception.AccessionNumberFormatException;

public interface AccessionService {
	AccessionResponseObject getOrderedList(String list) throws AccessionNumberFormatException;
}
