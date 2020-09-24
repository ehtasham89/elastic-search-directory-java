package com.ehtasham.elasticsearchdirectory.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.ehtasham.elasticsearchdirectory.models.ElasticSearchDirectory;

@Repository
public interface ElasticSearchRepository extends ElasticsearchRepository<ElasticSearchDirectory, String> {
}
