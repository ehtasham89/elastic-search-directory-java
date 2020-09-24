package com.ehtasham.elasticsearchdirectory.services;

import com.ehtasham.elasticsearchdirectory.repository.ElasticSearchRepository;

import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public Page<?> search(Map<String, String[]> map, Pageable pageable) {
        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();
        if (map != null && !map.isEmpty()) {
            MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(map);
            searchQuery.must(multiMatchQuery);
        }
        
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(searchQuery).withPageable(pageable);
        
        return searchRepository.search(queryBuilder.build());
    }
}
