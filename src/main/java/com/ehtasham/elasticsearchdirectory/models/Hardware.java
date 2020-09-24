package com.ehtasham.elasticsearchdirectory.models;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hardware {

	@Field(type = FieldType.Text)
	@JsonProperty("audioJack")
    private String audioJack;
	
	@Field(type = FieldType.Text)
	@JsonProperty("gps")
    private String gps;
	
	@Field(type = FieldType.Text)
	@JsonProperty("battery")
    private String battery;
	
	//check key exists
	public Boolean isFound(String key) {
		if (key.equals("audioJack") || key.equals("gps") || key.equals("battery")) {
			return true;
		}
		
		return false;	
	}
}