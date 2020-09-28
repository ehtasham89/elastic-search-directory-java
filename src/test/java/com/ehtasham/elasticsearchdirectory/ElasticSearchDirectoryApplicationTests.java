package com.ehtasham.elasticsearchdirectory;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.scheduling.annotation.Async;

import com.ehtasham.elasticsearchdirectory.models.ElasticSearchDirectory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class ElasticSearchDirectoryApplicationTests {

	@Test
	void contextLoads() {
	}

	private List<ElasticSearchDirectory> listOfElasticSearchDirectorys = new ArrayList<>();
    private RestHighLevelClient client = null;

    @Before
    public void setUp() throws UnknownHostException {
        ElasticSearchDirectory elasticSearchDirectory1 = new ElasticSearchDirectory();
        
        listOfElasticSearchDirectorys.add(elasticSearchDirectory1);

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
        			.connectedTo("localhost:9200")
					.build();

        client = RestClients.create(clientConfiguration).rest();
    }

    @Test
    public void searchFor_nodesTest() throws Exception {
    	int count = 0;
    	
    	String jsonUrl = "https://a511e938-a640-4868-939e-6eef06127ca1.mock.pstmn.io/handsets/list";
    	
    	String jsonDocument = readUrlData(jsonUrl);
    	ObjectMapper mapper = new ObjectMapper();
    	JsonNode nodes = mapper.readTree(jsonDocument);
    	
    
    	for(JsonNode node : nodes) {
			//if string contain match it will return true
    		if (node.at("/sim").asText().toLowerCase().contains("esim")){
    			count++;
    		}
    	}
    
    	assertEquals(count, 18);
    }
    
    @Test
    public void insertNewDataSet_thenIndexDocument() throws Exception {
    	this.setUp();
    	
        IndexRequest request = new IndexRequest("mobiledirectorysearch");
        
        String jsonUrl = "https://a511e938-a640-4868-939e-6eef06127ca1.mock.pstmn.io/handsets/list";
    	
    	String jsonDocument = readUrlData(jsonUrl);
	    
    	ObjectMapper mapper = new ObjectMapper();
    	
    	List<ElasticSearchDirectory> nodes = mapper.readValue(jsonDocument, new TypeReference<List<ElasticSearchDirectory>>(){});
		
    	request.source(nodes.get(0), XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        String index = response.getIndex();
        long version = response.getVersion();

        assertEquals(Result.CREATED, response.getResult());
        assertEquals(1, version);
        assertEquals("mobiledirectorysearch", index);
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

}
