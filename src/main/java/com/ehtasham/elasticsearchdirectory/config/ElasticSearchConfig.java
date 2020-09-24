package com.ehtasham.elasticsearchdirectory.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * elastic search configuration
 *
 * @author Ehtasham Nasir <ehtasham.nasir89@gmail.com/>
 */

@Configuration
@EnableElasticsearchRepositories("com.ehtasham.elasticsearchdirectory.repository")
@ComponentScan({ 
	"com.ehtasham.elasticsearchdirectory.controllers", 
	"com.ehtasham.elasticsearchdirectory.services" 
})
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class })
public class ElasticSearchConfig {
	@Value("${elasticsearch.cluster-name}")
    private String clusterName;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.host}")
    private String host;
    
	@Bean
	RestHighLevelClient client() {
	   ClientConfiguration clientConfiguration = ClientConfiguration.builder()
			   										.connectedTo(host + ":" + port)
			   										.build();

	   return RestClients.create(clientConfiguration)
	          .rest();
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
	   return new ElasticsearchRestTemplate(client());
	}
}
