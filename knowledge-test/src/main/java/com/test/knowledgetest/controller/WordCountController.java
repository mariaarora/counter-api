package com.test.knowledgetest.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.knowledgetest.bean.SearchWrapper;
import com.test.knowledgetest.service.CounterService;

@RestController
@RequestMapping("/counter-api")
public class WordCountController {
	
	@Autowired
	private CounterService counterService;
	
	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/search")
	public Map getCount(@RequestBody SearchWrapper wrapper){
		Map<String, List> countResult = new HashMap<String, List>();
		countResult.put("counts", counterService.getCount(wrapper.getSearchText()));
		return countResult;
	}
	
	@GetMapping("/top/{count}")
	public String getTopNCount(@PathVariable int count, HttpServletResponse response){
		response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                "CounterTopNResult.txt");
        response.setHeader(headerKey, headerValue);
		return counterService.getTopNCount(count);
	}
}
