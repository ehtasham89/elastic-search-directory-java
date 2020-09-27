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
	
	//check key exists
	public Boolean isFound(String key) {
		if (key.equals("announceDate") || key.equals("priceEur")) {
			return true;
		}
		
		return false;	
	}
	
	@Override
    public String toString() {
        return "Release{announceDate=" + announceDate + ", priceEur=" + priceEur + "}";
    }
}
