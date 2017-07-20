package com.accession.mapper;

import static com.accession.util.Constants.INVALID_ACCESSION_NUMBER;
import static com.accession.util.Constants.PATTERN;
import static com.accession.util.Constants.INVALID_LIST_OF_ACCESSION_NUMBER;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.accession.dto.AccessionResponseObject;
import com.accession.exception.AccessionNumberFormatException;
import com.accession.factory.AccessionResponseObjectFactory;

@Component
public class AccessionMapperImpl implements AccessionMapper {

	@Override
	public AccessionResponseObject mapToResponseObject(String numbers) {
		return AccessionResponseObjectFactory.getAccessionResponseObject(numbers);
	}

	@Override
	public List<String> mapToStringList(String list) throws AccessionNumberFormatException {
		if(list == null || list.isEmpty()) {
			throw new AccessionNumberFormatException(INVALID_LIST_OF_ACCESSION_NUMBER);
		}
		String[] tokens = list.trim().split(",");
		Pattern pattern = Pattern.compile(PATTERN);
		List<String> numbers = new ArrayList<>();
		for(String token : tokens) {
			if(token == null || token.trim().isEmpty()) {
				throw new AccessionNumberFormatException(INVALID_ACCESSION_NUMBER);
			}
			
			if(!pattern.matcher(token.trim()).matches()) {
				throw new AccessionNumberFormatException(INVALID_ACCESSION_NUMBER);
			}
			
			numbers.add(token.trim());
		}
		return numbers;
	}

}