package com.accession.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accession.dto.AccessionResponseObject;
import com.accession.exception.AccessionNumberFormatException;
import com.accession.mapper.AccessionMapper;
import com.accession.processor.AccessionProcessor;

@Service
public class AccessionServiceImpl implements AccessionService {
	
	@Autowired
	private AccessionMapper accessionMapper;
	
	@Autowired
	private AccessionProcessor accessionProcess;

	@Override
	public AccessionResponseObject getOrderedList(String list) throws AccessionNumberFormatException {
		List<String> numbers = accessionMapper.mapToStringList(list);
		String processedList = accessionProcess.processAsOrderedList(numbers);
		return accessionMapper.mapToResponseObject(processedList);
	}
	
}