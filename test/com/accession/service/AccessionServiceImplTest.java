package com.accession.service;

import static com.accession.util.Constants.EMPTY_STRING;
import static com.accession.util.Constants.INVALID_ACCESSION_NUMBER;
import static com.accession.util.Constants.INVALID_LIST_OF_ACCESSION_NUMBER;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.accession.dto.AccessionResponseObject;
import com.accession.exception.AccessionNumberFormatException;
import com.accession.factory.AccessionResponseObjectFactory;
import com.accession.mapper.AccessionMapper;
import com.accession.processor.AccessionProcessor;

public class AccessionServiceImplTest {
	
	@InjectMocks
	private AccessionServiceImpl accessionServiceImpl;
	
	@Mock
	private AccessionMapper mockMapper;
	
	@Mock
	private AccessionProcessor mockProcessor;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetOrderedList() throws AccessionNumberFormatException {
		String s = "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, "
				+ "ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1";
		
		List<String> list = new ArrayList<String>() {{
			add("A00000");
			add("A0001");
			add("ERR000111");
			add("ERR000112");
			add("ERR000113");
			add("ERR000115");
			add("ERR000116");
			add("ERR100114");
			add("ERR200000001");
			add("ERR200000002");
			add("ERR200000003");
			add("DRR2110012");
			add("SRR211001");
			add("ABCDEFG1");
		}};
		
		String expected = "A00000, A0001, ABCDEFG1, DRR2110012, ERR000111-ERR000113, ERR000115-ERR000116, ERR100114, ERR200000001-ERR200000003, SRR211001";
		
		AccessionResponseObject aro = AccessionResponseObjectFactory.getAccessionResponseObject(expected);
		when(mockMapper.mapToStringList(s)).thenReturn(list);
		when(mockProcessor.processAsOrderedList(list)).thenReturn(expected);
		when(mockMapper.mapToResponseObject(expected)).thenReturn(aro);
		
		AccessionResponseObject actualObject = accessionServiceImpl.getOrderedList(s);
		
		verify(mockMapper).mapToStringList(s);
		verify(mockProcessor).processAsOrderedList(list);
		verify(mockMapper).mapToResponseObject(expected);
		
		Assert.assertEquals(aro.getList(), actualObject.getList());
	}
	
	@Test
	public void testGetOrderedListWithValidStringButEmptyStringList() throws AccessionNumberFormatException {
		String s = "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, "
				+ "ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1";
		
		List<String> list = new ArrayList<String>();
		
		String expected = EMPTY_STRING;
		
		AccessionResponseObject aro = AccessionResponseObjectFactory.getAccessionResponseObject(expected);
		when(mockMapper.mapToStringList(s)).thenReturn(list);
		when(mockProcessor.processAsOrderedList(list)).thenReturn(expected);
		when(mockMapper.mapToResponseObject(expected)).thenReturn(aro);
		
		AccessionResponseObject actualObject = accessionServiceImpl.getOrderedList(s);
		
		verify(mockMapper).mapToStringList(s);
		verify(mockProcessor).processAsOrderedList(list);
		verify(mockMapper).mapToResponseObject(expected);
		
		Assert.assertEquals(aro.getList(), actualObject.getList());
		Assert.assertTrue(actualObject.getList().isEmpty());
	}
	
	@Test
	public void testGetOrderedListWithValidStringButNullStringList() throws AccessionNumberFormatException {
		String s = "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, "
				+ "ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1";
		
		List<String> list = null;
		
		String expected = EMPTY_STRING;
		
		AccessionResponseObject aro = AccessionResponseObjectFactory.getAccessionResponseObject(expected);
		when(mockMapper.mapToStringList(s)).thenReturn(list);
		when(mockProcessor.processAsOrderedList(list)).thenReturn(expected);
		when(mockMapper.mapToResponseObject(expected)).thenReturn(aro);
		
		AccessionResponseObject actualObject = accessionServiceImpl.getOrderedList(s);
		
		verify(mockMapper).mapToStringList(s);
		verify(mockProcessor).processAsOrderedList(list);
		verify(mockMapper).mapToResponseObject(expected);
		
		Assert.assertEquals(aro.getList(), actualObject.getList());
		Assert.assertTrue(actualObject.getList().isEmpty());
	}
	
	@Test
	public void testGetOrderedListWithSingleNumber() throws AccessionNumberFormatException {
		String s = "A00000";
		
		List<String> list = Arrays.asList("A00000");
		
		String expected = "A00000";
		
		AccessionResponseObject aro = AccessionResponseObjectFactory.getAccessionResponseObject(expected);
		when(mockMapper.mapToStringList(s)).thenReturn(list);
		when(mockProcessor.processAsOrderedList(list)).thenReturn(expected);
		when(mockMapper.mapToResponseObject(expected)).thenReturn(aro);
		
		AccessionResponseObject actualObject = accessionServiceImpl.getOrderedList(s);
		
		verify(mockMapper).mapToStringList(s);
		verify(mockProcessor).processAsOrderedList(list);
		verify(mockMapper).mapToResponseObject(expected);
		
		Assert.assertEquals(aro.getList(), actualObject.getList());
		Assert.assertNotNull(actualObject.getList());
	}
	
	@Test
	public void testGetOrderedListWithInvalidNumber() throws AccessionNumberFormatException {
		String s = "A00000, A0001, ERR000111, 1234ABCD, ERR000112, ERR000113, ERR000115, "
				+ "ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1";
		
		
		doThrow(new AccessionNumberFormatException(INVALID_ACCESSION_NUMBER)).when(mockMapper).mapToStringList(s);
		thrown.expect(AccessionNumberFormatException.class);
		thrown.expectMessage(INVALID_ACCESSION_NUMBER);
		accessionServiceImpl.getOrderedList(s);
		
		verify(mockMapper).mapToStringList(s);
	}
	
	@Test
	public void testGetOrderedListWithEmptyList() throws AccessionNumberFormatException {
		String s = "";
		
		doThrow(new AccessionNumberFormatException(INVALID_LIST_OF_ACCESSION_NUMBER)).when(mockMapper).mapToStringList(s);
		thrown.expect(AccessionNumberFormatException.class);
		thrown.expectMessage(INVALID_LIST_OF_ACCESSION_NUMBER);
		accessionServiceImpl.getOrderedList(s);
		
		verify(mockMapper).mapToStringList(s);		
	}
	
	@Test
	public void testGetOrderedListWithNullList() throws AccessionNumberFormatException {
		String s = null;
		
		doThrow(new AccessionNumberFormatException(INVALID_LIST_OF_ACCESSION_NUMBER)).when(mockMapper).mapToStringList(s);
		thrown.expect(AccessionNumberFormatException.class);
		thrown.expectMessage(INVALID_LIST_OF_ACCESSION_NUMBER);
		accessionServiceImpl.getOrderedList(s);
		
		verify(mockMapper).mapToStringList(s);		
	}

}
