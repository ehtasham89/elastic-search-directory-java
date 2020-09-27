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
	
	public Hardware() {

    }
	
	public Hardware(String audioJack, String gps, String battery) {
        this.audioJack = audioJack;
        this.gps = gps;
        this.battery = battery;
    }
	
	public String getAudioJack() {
        return audioJack;
    }

    public void setAudioJack(String audioJack) {
        this.audioJack = audioJack;
    }
	    
	//check key exists
	public Boolean isFound(String key) {
		if (key.equals("audioJack") || key.equals("gps") || key.equals("battery")) {
			return true;
		}
		
		return false;	
	}
	
	@Override
    public String toString() {
        return "Hardware{" + "audioJack='" + audioJack + '\'' + ", gps='" + gps + '\'' + ", battery=" + battery + '}';
    }
}