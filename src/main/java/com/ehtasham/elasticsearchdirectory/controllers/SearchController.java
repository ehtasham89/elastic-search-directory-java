package com.ehtasham.elasticsearchdirectory.controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehtasham.elasticsearchdirectory.models.ElasticSearchDirectory;
import com.ehtasham.elasticsearchdirectory.services.ElasticSeacrhService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


@RestController
@RequestMapping("/mobile")
@CrossOrigin("*")
public class SearchController<JSONArray> {
	
	private ElasticSeacrhService elasticSeacrhService;

    @Autowired
    public SearchController(ElasticSeacrhService elasticSeacrhService) {
        this.elasticSeacrhService = elasticSeacrhService;
    }
    
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
    public Iterable<ElasticSearchDirectory> search(HttpServletRequest httpServletRequest, 
    		@PageableDefault(value = 10, page = 0) Pageable pageable){

    	return this.elasticSeacrhService.search(httpServletRequest, pageable);
    }
    
    
    @GetMapping("/load-data")
    @Async
    public Collection<ElasticSearchDirectory> JsonBulkImport(InputStream in) throws Exception {
    	int numberOfRecords=0;
    	
    	String jsonUrl = "https://a511e938-a640-4868-939e-6eef06127ca1.mock.pstmn.io/handsets/list";
    	
    	String jsonDocument;

	    jsonDocument = readUrl(jsonUrl);
	    	
	    	Gson gson = new Gson(); 
	    	
	    	Type collectionType = new TypeToken<Collection<ElasticSearchDirectory>>(){}.getType();
	    	Collection<ElasticSearchDirectory> enums = gson.fromJson(jsonDocument, collectionType);
	    	
	    	//ElasticSearchDirectory docs = gson.fromJson(jsonDocument, ElasticSearchDirectory.class);
	    	
	    	return enums;
    }
    	
    @Async
	private static String readUrl(String urlString) throws Exception {
    	BufferedReader reader = null;
    	
	    try {
		    URL url = new URL(urlString);
		    StringBuilder sb = new StringBuilder();
		    
		    reader = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName("UTF-8")));
		    
		    int cp;
			while ((cp = reader.read()) != -1) { 
				sb.append((char) cp);
	        }
			
			return sb.toString();  
	    } finally {
	    	reader.close();
	    }
	}
   
}
