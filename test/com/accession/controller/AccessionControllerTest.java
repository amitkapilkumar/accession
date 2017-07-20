package com.accession.controller;

import static com.accession.util.Constants.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.accession.builder.AccessionRequestObjectBuilder;
import com.accession.dto.AccessionRequestObject;
import com.accession.dto.AccessionResponseObject;
import com.accession.exception.AccessionNumberFormatException;
import com.accession.factory.AccessionResponseObjectFactory;
import com.accession.service.AccessionService;

public class AccessionControllerTest {
	
	@InjectMocks
	private AccessionController accessionController;
	
	@Mock
	private AccessionService accessionService;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetOrderedList() throws AccessionNumberFormatException {
		String list = "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, "
				+ "ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1";
		AccessionRequestObject areqo = new AccessionRequestObjectBuilder().withList(list).build();
		
		String expectedList = "A00000, A0001, ABCDEFG1, DRR2110012, ERR000111-ERR000113, "
				+ "ERR000115-ERR000116, ERR100114, ERR200000001-ERR200000003, SRR211001";
		AccessionResponseObject aro = AccessionResponseObjectFactory.getAccessionResponseObject(expectedList);
		
		when(accessionService.getOrderedList(areqo.getList())).thenReturn(aro);
		AccessionResponseObject actualAro = accessionController.getOrderedList(areqo);
		
		verify(accessionService).getOrderedList(areqo.getList());
		Assert.assertEquals(aro.getList(), actualAro.getList());
	}
	
	@Test
	public void testGetOrderedListWithEmptyList() throws AccessionNumberFormatException {
		AccessionRequestObject areqo = new AccessionRequestObjectBuilder().withList("").build();
		
		doThrow(new AccessionNumberFormatException(INVALID_LIST_OF_ACCESSION_NUMBER))
		.when(accessionService).getOrderedList(areqo.getList());
		thrown.expect(AccessionNumberFormatException.class);
		thrown.expectMessage(INVALID_LIST_OF_ACCESSION_NUMBER);
		accessionController.getOrderedList(areqo);
		
		verify(accessionService).getOrderedList(areqo.getList());
	}

	@Test
	public void testGetOrderedListWithNullList() throws AccessionNumberFormatException {
		AccessionRequestObject areqo = new AccessionRequestObjectBuilder().withList(null).build();
		
		doThrow(new AccessionNumberFormatException(INVALID_LIST_OF_ACCESSION_NUMBER))
		.when(accessionService).getOrderedList(areqo.getList());
		thrown.expect(AccessionNumberFormatException.class);
		thrown.expectMessage(INVALID_LIST_OF_ACCESSION_NUMBER);
		accessionController.getOrderedList(areqo);
		
		verify(accessionService).getOrderedList(areqo.getList());
	}
	
	@Test
	public void testGetOrderedListWithInvalidList() throws AccessionNumberFormatException {
		AccessionRequestObject areqo = new AccessionRequestObjectBuilder().withList("ABC123, 234ABC").build();
		
		doThrow(new AccessionNumberFormatException(INVALID_ACCESSION_NUMBER))
		.when(accessionService).getOrderedList(areqo.getList());
		thrown.expect(AccessionNumberFormatException.class);
		thrown.expectMessage(INVALID_ACCESSION_NUMBER);
		accessionController.getOrderedList(areqo);
		
		verify(accessionService).getOrderedList(areqo.getList());
	}
}
