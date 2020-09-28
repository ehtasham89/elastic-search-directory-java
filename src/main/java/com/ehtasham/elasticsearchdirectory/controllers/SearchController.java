package com.ehtasham.elasticsearchdirectory.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


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
	
    //elastic data search 
    @GetMapping("/el-search")
    public Iterable<ElasticSearchDirectory> eSearch(HttpServletRequest httpServletRequest, 
    		@PageableDefault(value = 10, page = 0) Pageable pageable){

    	return this.elasticSeacrhService.search(httpServletRequest, pageable);
    }
    
    //JSON document search with JAKSON API
    @GetMapping("/search")
    @Async
    public ArrayNode search(HttpServletRequest request) throws Exception {
    	String jsonUrl = "https://a511e938-a640-4868-939e-6eef06127ca1.mock.pstmn.io/handsets/list";
    	
    	String jsonDocument = readUrlData(jsonUrl);
	    
    	ObjectMapper mapper = new ObjectMapper();
    		
    	JsonNode nodes = mapper.readTree(jsonDocument);
    	
    	return (ArrayNode) this.searchForNodes(request, nodes);
    }
    
    //import JSON data into elastic search db.
    @GetMapping("/load-data")
    @Async
    public String JsonBulkImport() throws Exception {
    	try {
	    	int numberOfRecords=0;
	    	
	    	String jsonUrl = "https://a511e938-a640-4868-939e-6eef06127ca1.mock.pstmn.io/handsets/list";
	    	
	    	String jsonDocument = readUrlData(jsonUrl);
		    
	    	ObjectMapper mapper = new ObjectMapper();
	    	
	    	List<ElasticSearchDirectory> nodes = mapper.readValue(jsonDocument, new TypeReference<List<ElasticSearchDirectory>>(){});
			
	    	
			for(ElasticSearchDirectory node: nodes) {
				numberOfRecords += this.elasticSeacrhService.insertNew( node );
			}
			
			return "Total nummber of records loaded: " + numberOfRecords;
    	} catch(Exception e) {
    		return e.getMessage();
    	}
    }
    	
    //load json content from URL
    @Async
	private static String readUrlData(String urlString) throws Exception {
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
    
    //search nodes with query
    @SuppressWarnings("unchecked")
	private JsonNode searchForNodes(HttpServletRequest request, JsonNode nodes) {
    	String path;
    	
    	int count = 0, paramsCount = 0;
    	
    	ElasticSearchDirectory searchModel = new ElasticSearchDirectory();
    	
    	ObjectMapper mapper = new ObjectMapper();
    	ArrayNode arrayNode = mapper.createArrayNode();
    	
    	HashMap<String, String> params = new HashMap<String, String>();
    	
    	Enumeration<String> parameterNames = request.getParameterNames();
    	
    	while (parameterNames.hasMoreElements()) {
    	    String key = (String) parameterNames.nextElement();
    	    String val = request.getParameter(key);
    	    
    	    params.put(key, val);
    	    
    	    paramsCount++;
    	}
    	
    	for(JsonNode node : nodes) {
    		Iterator<?> iterator = params.entrySet().iterator();
    		
    		while(iterator.hasNext()) {	
    			@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) iterator.next();
    			
    			path = "";
    			
    			//nested path
    			if (searchModel.getHardware().isFound(entry.getKey().toString())) {
    				path = "/hardware";
    			} else if (searchModel.getRelease().isFound(entry.getKey().toString())) {
    				path = "/release";
    			}
    			
    			//if string contain match it will return true
	    		if (entry.getKey() != null && params.get(entry.getKey()) != null
	    				&& node.at(path + "/" + entry.getKey().toString()).asText().toLowerCase().contains(params.get(entry.getKey().toString()).toString().toLowerCase())
	    		){
	    			count++;
	    		}
    		}
    		
    		if (count == paramsCount) {
        		arrayNode.add(node);
    		}
    		
    		count = 0;
    	}
    
    	return paramsCount > 0 ? arrayNode : nodes;
    }
   
}
