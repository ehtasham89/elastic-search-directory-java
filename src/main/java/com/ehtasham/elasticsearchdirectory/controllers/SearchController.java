package com.ehtasham.elasticsearchdirectory.controllers;

import java.util.Map;
import java.lang.String;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/mobile")
@CrossOrigin("*")

public class SearchController {
	
	/**
	 * search API (GET /mobile/search?) 
	 * that will allow the caller to retrieve one or more mobile handset record 
	 * based on the passed search criteria.
	 * The criteria can be any field in the handset data along with any value. 
	 * Examples:
	 * /search?priceEur=200. Will return 10 devices.
	 * /search?sim=eSim. Will return 18 devices.
	 * /search?announceDate=1999&price=200. Will return 2 devices. 
	*/
	
    @GetMapping("/search")
    public Map<String, String[]> search(HttpServletRequest httpServletRequest){
    	return httpServletRequest.getParameterMap();
    }
   
}
