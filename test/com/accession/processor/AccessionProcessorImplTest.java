package com.accession.processor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.doThrow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.accession.util.Constants.*;

import com.accession.exception.AccessionNumberFormatException;

public class AccessionProcessorImplTest {
	
	private AccessionProcessorImpl apl;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		apl = new AccessionProcessorImpl();
	}

	@Test
	public void testProcessAsOrderedListWithEmptyList() throws AccessionNumberFormatException {
		Assert.assertEquals(EMPTY_STRING, apl.processAsOrderedList(Arrays.asList()));
	}
	
	@Test
	public void testProcessAsOrderedListWithNullList() throws AccessionNumberFormatException {
		Assert.assertEquals(EMPTY_STRING, apl.processAsOrderedList(null));
	}
	
	@Test
	public void testProcessAsOrderedListWithSingleNumber() throws AccessionNumberFormatException {
		Assert.assertEquals("A00000", apl.processAsOrderedList(Arrays.asList("A00000")));
	}
	
	@Test
	public void testProcessAsOrderedListWithEmptyNumber() throws AccessionNumberFormatException {
		List<String> list = new ArrayList<String>() {{
			add("A00000");
			add("A0001");
			add("");
			add("000112AAA");
			add("ERR000113");
			add("ERR000114");
			add("ERR000115");
		}};
		
		thrown.expect(AccessionNumberFormatException.class);
		thrown.expectMessage(INVALID_ACCESSION_NUMBER);
		apl.processAsOrderedList(list);
	}
	
	@Test
	public void testProcessAsOrderedListWithNullNumber() throws AccessionNumberFormatException {
		List<String> list = new ArrayList<String>() {{
			add("A00000");
			add("A0001");
			add(null);
			add("000112AAA");
			add("ERR000113");
			add("ERR000114");
			add("ERR000115");
		}};
		
		thrown.expect(AccessionNumberFormatException.class);
		thrown.expectMessage(INVALID_ACCESSION_NUMBER);
		apl.processAsOrderedList(list);
	}
	
	@Test
	public void testProcessAsOrderedListWithDataSet1() throws AccessionNumberFormatException {
		List<String> list = new ArrayList<String>() {{
			add("A00000");
			add("A0001");
			add("ERR000111");
			add("ERR000112");
			add("ERR000113");
			add("ERR000114");
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
	
		String expectedList = "A00000,A0001,ABCDEFG1,DRR2110012,ERR000111-ERR000116,ERR100114,ERR200000001-ERR200000003,SRR211001";
		
		String actualList = apl.processAsOrderedList(list);
		
		Assert.assertEquals(expectedList, actualList);
	}

	@Test
	public void testProcessAsOrderedListWithDataSet2() throws AccessionNumberFormatException {
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
	
		String expectedList = "A00000,A0001,ABCDEFG1,DRR2110012,ERR000111-ERR000113,ERR000115-ERR000116,ERR100114,ERR200000001-ERR200000003,SRR211001";
		
		String actualList = apl.processAsOrderedList(list);
		
		Assert.assertEquals(expectedList, actualList);
	}
}
