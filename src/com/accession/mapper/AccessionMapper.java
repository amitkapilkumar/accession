package com.accession.mapper;

import java.util.List;

import com.accession.dto.AccessionResponseObject;
import com.accession.exception.AccessionNumberFormatException;

public interface AccessionMapper {

	AccessionResponseObject mapToResponseObject(String processedList);

	List<String> mapToStringList(String list) throws AccessionNumberFormatException;

}