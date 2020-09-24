package com.ehtasham.elasticsearchdirectory.controllers;

import com.ehtasham.elasticsearchdirectory.models.ElasticSearchDirectory;
import com.ehtasham.elasticsearchdirectory.services.ElasticSeacrhService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.String;
import java.io.Reader;
import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/mobile")
@CrossOrigin("*")
public class SearchController {
	
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
    	//return httpServletRequest.getParameterMap();
    	
    	//Map<String, String[]> query = httpServletRequest.getQueryString();
    	
    	return this.elasticSeacrhService.search(httpServletRequest, pageable);
    }
    
    /*
    @GetMapping("/load-data")
    public void JsonBulkImport(InputStream in) throws IOException, ExecutionException, InterruptedException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        File jsonFilePath = new File("H:\\Work\\Data\\sample.json");
        int count=0,noOfBatch=1;
        //initialize jsonReader class by passing reader
        JsonReader jsonReader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        		
        Gson gson = new GsonBuilder().create();
        jsonReader.beginArray(); //start of json array
        int numberOfRecords = 1;
        while (jsonReader.hasNext()){ //next json array element
            Document document = gson.fromJson(jsonReader, Document.class);
            //do something real
            try {
                XContentBuilder xContentBuilder = jsonBuilder()
                        .startObject()
                        .field(DOC_TYPE, document.getDocType())
                        .field(DOC_AUTHOR, document.getDocAuthor())
                        .field(DOC_TITLE, document.getDocTitle())
                        .field(IS_PARENT, document.isParent())
                        .field(PARENT_DOC_ID, document.getParentDocId())
                        .field(DOC_LANGUAGE, document.getDocLanguage())
                        .endObject();
                bulkRequest.add(client.prepareIndex(indexName, indexTypeName, String.valueOf(numberOfRecords))
                        .setSource(xContentBuilder));
                if (count==50_000) {
                    addDocumentToESCluser(bulkRequest, noOfBatch, count);
                    noOfBatch++;
                    count = 0;
                }
            }catch (Exception e) {
                e.printStackTrace();
                //skip records if wrong date in input file
            }
            numberOfRecords++;
            count++;
        }
        
        jsonReader.endArray();
        
        if(count!=0){ //add remaining documents to ES
            addDocumentToESCluser(bulkRequest,noOfBatch,count);
        }
        //System.out.println("Total Document Indexed : "+numberOfRecords);
    }
    */
   
}
