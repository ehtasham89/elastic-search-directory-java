package com.ehtasham.elasticsearchdirectory.services;

import com.ehtasham.elasticsearchdirectory.models.ElasticSearchDirectory;
import com.ehtasham.elasticsearchdirectory.repository.ElasticSearchRepository;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * Elastic Search Service
 *
 * @author Ehtasham Nasir <ehtasham.nasir89@gmail.com/>
 */
@Service
public class ElasticSeacrhService {
    private ElasticSearchRepository searchRepository;
    private ElasticSearchDirectory direcotoryModel;

    @Autowired
    public ElasticSeacrhService(ElasticSearchRepository searchRepository, ElasticSearchDirectory elasticSearchDirectory) {
        this.searchRepository = searchRepository;
        this.direcotoryModel = elasticSearchDirectory;
    }

    public ElasticSearchDirectory create(ElasticSearchDirectory document) {
    	//IndexRequest request = new IndexRequest("people");
        ///request.source(document, XContentType.JSON);
    	
    	System.out.println(document.toString());
    	
        return (ElasticSearchDirectory) searchRepository.save(document);
    }
    
    public Iterable<ElasticSearchDirectory> search(HttpServletRequest httpServletRequest, Pageable pageable) {
        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();

        String query =  httpServletRequest.getQueryString();
        
        if (query != null && !query.isEmpty()) {
        	System.out.println(this.mapQuery(httpServletRequest));
        	return searchRepository.search(QueryBuilders.queryStringQuery(this.mapQuery(httpServletRequest)));
        } else {
        	NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            queryBuilder.withQuery(searchQuery).withPageable(pageable);
            
            return searchRepository.search(queryBuilder.build());
        }
        
    }
    
    //prepare query parameters
    private String mapQuery(HttpServletRequest request) {
    	String queryStr = "(";
    	
    	Enumeration<String> parameterNames = request.getParameterNames();
    	
    	while (parameterNames.hasMoreElements()) {
    	    String key = (String) parameterNames.nextElement();
    	    String val = request.getParameter(key);
    		
    	    queryStr += " AND ";
    	    
	        if (this.direcotoryModel.getHardware().isFound(key)) {
	        	queryStr += "hardware.";
	        } 
	        else if (this.direcotoryModel.getRelease().isFound(key)) {
	        	queryStr += "release.";
	        }
	        	
	        queryStr += key + ":" + val;
	        
    	}
    	
    	queryStr = queryStr.replaceFirst(" AND ", "");
    	
    	return queryStr += ")";
    }
}
