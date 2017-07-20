package com.accession.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accession.dto.AccessionRequestObject;
import com.accession.dto.AccessionResponseObject;
import com.accession.exception.AccessionNumberFormatException;
import com.accession.service.AccessionService;

@Controller
public class AccessionController {
	
	@Autowired
	private AccessionService accessionService;
	
	@RequestMapping(value = "/orderedlist", method = RequestMethod.PUT ,consumes = "application/json", produces = "application/json")
	@ResponseBody
	public AccessionResponseObject getOrderedList(@RequestBody AccessionRequestObject aro) throws AccessionNumberFormatException {
		return accessionService.getOrderedList(aro.getList());
	}
}