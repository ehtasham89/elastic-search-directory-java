package com.ehtasham.elasticsearchdirectory.services;

import com.ehtasham.elasticsearchdirectory.models.ElasticSearchDirectory;
import com.ehtasham.elasticsearchdirectory.repository.ElasticSearchRepository;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    public ElasticSeacrhService(ElasticSearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public Iterable<ElasticSearchDirectory> search(HttpServletRequest httpServletRequest, Pageable pageable) {
        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();

        String query =  httpServletRequest.getQueryString();
        
        if (query != null && !query.isEmpty()) {
        	query = query.replaceAll("=", ":").replaceAll("&", " AND ");
        	
        	return searchRepository.search(QueryBuilders.queryStringQuery("("+ query +")"));
        } else {
        	NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            queryBuilder.withQuery(searchQuery).withPageable(pageable);
            
            return searchRepository.search(queryBuilder.build());
        }
        
    }
    
    //prepare query parameters
    private Object mapQuery(HttpServletRequest params) {
    	return Collections.list(params.getParameterNames())
        .stream()
        .collect(Collectors.toMap(parameterName -> parameterName, params::getParameterValues));
    }
}
