package com.accession.mapper;

import static org.junit.Assert.*;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.accession.util.Constants.*;

import com.accession.dto.AccessionResponseObject;
import com.accession.exception.AccessionNumberFormatException;
import com.accession.factory.AccessionResponseObjectFactory;
import com.accession.mapper.AccessionMapperImpl;

public class AccessionMapperImplTest {
	private AccessionMapperImpl accessionMapperImpl; 
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		accessionMapperImpl = new AccessionMapperImpl();
	}

	@Test
	public void testMapToResponseObject() {
		String s = "A00000, A0001, ERR000111, ERR000112, ERR000113";
		AccessionResponseObject aro = AccessionResponseObjectFactory.getAccessionResponseObject(s);
		
		AccessionResponseObject actual = accessionMapperImpl.mapToResponseObject(s);
		
		Assert.assertEquals(aro.getList(), actual.getList());
	}

	@Test
	public void testMapToStringList() throws AccessionNumberFormatException {
		String s = "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, "
				+ "ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1";
		List<String> expectedList = accessionMapperImpl.mapToStringList(s);
		
		Assert.assertEquals(expectedList.size(), 14);
	}
	
	@Test
	public void testMapToStringListWithSingleElement() throws AccessionNumberFormatException {
		String s = "A00000";
		List<String> expectedList = accessionMapperImpl.mapToStringList(s);
		
		Assert.assertEquals(expectedList.size(), 1);
	}
	
	@Test
	public void testMapToStringListForInvalidList() throws AccessionNumberFormatException {
		String s = "A00000, 123ACD, A0001, ERR000111, ERR000112, ERR000113, ERR000115, "
				+ "ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1";
		
		thrown.expect(AccessionNumberFormatException.class);
		thrown.expectMessage(INVALID_ACCESSION_NUMBER);
		accessionMapperImpl.mapToStringList(s);		
	}
	
	@Test
	public void testMapToStringListForNullList() throws AccessionNumberFormatException {
		String s = null;
		
		thrown.expect(AccessionNumberFormatException.class);
		thrown.expectMessage(INVALID_LIST_OF_ACCESSION_NUMBER);
		accessionMapperImpl.mapToStringList(s);		
	}
	
	@Test
	public void testMapToStringListForEmptyList() throws AccessionNumberFormatException {
		String s = "";
		
		thrown.expect(AccessionNumberFormatException.class);
		thrown.expectMessage(INVALID_LIST_OF_ACCESSION_NUMBER);
		accessionMapperImpl.mapToStringList(s);		
	}
}