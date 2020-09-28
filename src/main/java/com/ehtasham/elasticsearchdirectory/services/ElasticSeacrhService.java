package com.ehtasham.elasticsearchdirectory.services;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.ehtasham.elasticsearchdirectory.models.ElasticSearchDirectory;
import com.ehtasham.elasticsearchdirectory.repository.ElasticSearchRepository;

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

    public int insertNew(ElasticSearchDirectory document) {
    	try {
    		searchRepository.save(document); //insert into elastic search db
    		
    		return 1;
    	}catch(Exception e) {
    		return 0;
    	}
    }
    
    public Iterable<ElasticSearchDirectory> search(HttpServletRequest httpServletRequest, Pageable pageable) {
        String query =  httpServletRequest.getQueryString();
        
        if (query != null && !query.isEmpty()) {
        	return searchRepository.search(QueryBuilders.queryStringQuery(this.mapQuery(httpServletRequest)));
        } else {
        	BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();
        	
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
