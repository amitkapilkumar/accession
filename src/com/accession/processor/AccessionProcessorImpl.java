package com.accession.processor;

import static com.accession.util.Constants.EMPTY_STRING;
import static com.accession.util.Constants.INVALID_ACCESSION_NUMBER;
import static com.accession.util.Constants.PATTERN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.accession.exception.AccessionNumberFormatException;
import com.accession.model.Bounds;

@Component
public class AccessionProcessorImpl implements AccessionProcessor {

	private String getStringComponent(String s) throws AccessionNumberFormatException {
		if(s == null || s.isEmpty())
			throw new AccessionNumberFormatException(INVALID_ACCESSION_NUMBER);
		
		return s.substring(0, getStringBoundry(s));
	}
	
	private String getNumberComponent(String s) throws AccessionNumberFormatException {
		if(s == null || s.isEmpty())
			throw new AccessionNumberFormatException(INVALID_ACCESSION_NUMBER);
		
		return s.substring(getStringBoundry(s));
	}

	private int getStringBoundry(String s) throws AccessionNumberFormatException {
		int i=0;
		while(!checkIfInt(s.charAt(i))) {
			i++;
			if(i == s.length())
				throw new AccessionNumberFormatException(INVALID_ACCESSION_NUMBER);
		}
		return i;
	}
	
	private boolean isMatchingSequence(String s1, String s2) throws AccessionNumberFormatException {
		Pattern pattern = Pattern.compile(PATTERN);
		if(s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty() || !pattern.matcher(s1).matches() || !pattern.matcher(s2).matches()) {
			throw new AccessionNumberFormatException(INVALID_ACCESSION_NUMBER);
		}
		if(getStringComponent(s1).equals(getStringComponent(s2)) && s1.length() == s2.length()) {
			String nc1 = getNumberComponent(s1);
			String nc2 = getNumberComponent(s2);
			if(Integer.parseInt(nc1)+1 == Integer.parseInt(nc2)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String processAsOrderedList(List<String> numbers) throws AccessionNumberFormatException {
		if(numbers == null || numbers.isEmpty())
			return EMPTY_STRING;
		
		if(numbers.contains(null))
			throw new AccessionNumberFormatException(INVALID_ACCESSION_NUMBER);
		
		if(numbers.size() == 1)
			return numbers.get(0);
		
		Collections.sort(numbers);
		
		List<Bounds> bounds = new ArrayList<Bounds>();
		
		// identify accession boundry pair
		boolean found = false;
		int upperIndex=-1;
		int lowerIndex=-1;
		for(int i=1; i<numbers.size(); i++) {
			if(found) {
				if(isMatchingSequence(numbers.get(i-1), numbers.get(i))) {
					upperIndex = i;
				}
				else {
					found = false;
					bounds.add(new Bounds(lowerIndex, upperIndex));
					lowerIndex = -1;
					upperIndex = -1;
				}
			}
			else {
				if(isMatchingSequence(numbers.get(i-1), numbers.get(i))) {
					lowerIndex = i-1;
					upperIndex = i;
					found = true;
				}
			}
		}
		
		
		String s = String.join(",", numbers);
		for(Bounds b : bounds) {
			String token = numbers.get(b.getLowerBound()) + "-" + numbers.get(b.getUpperBound());
			StringBuilder sb = new StringBuilder();
			for(int i=b.getLowerBound(); i <=b.getUpperBound(); i++) {
				if(i == numbers.size()-1)
					sb.append(numbers.get(i));
				else	
					sb.append(numbers.get(i) + ",");
			}
			String finder = sb.toString();
			if(finder.charAt(finder.length()-1) == ',') {
				token = token + ",";
			}
			s = s.replaceAll(sb.toString(), token);
		}
		return s;
	}
	
	
	private boolean checkIfInt(char c) {
		if(c < 48 || c > 57) {
			return false;
		}
		return true;
	}
}
