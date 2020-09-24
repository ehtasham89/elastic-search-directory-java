package com.ehtasham.elasticsearchdirectory.models;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Release {

	@Field(type = FieldType.Text)
	@JsonProperty("announceDate")
    private String announceDate;
	
	@Field(type = FieldType.Double)
	@JsonProperty("priceEur")
    private Double priceEur;
	
}
