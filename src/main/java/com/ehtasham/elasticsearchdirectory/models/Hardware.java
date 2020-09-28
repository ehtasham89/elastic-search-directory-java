package com.ehtasham.elasticsearchdirectory.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hardware {

	@JsonProperty("audioJack")
    private String audioJack;
	
	@JsonProperty("gps")
	private String gps;
	
	@JsonProperty("battery")
	private String battery;
	
	public Hardware() {}
	
	
	public Hardware(String audioJack, String gps, String battery) {
        this.audioJack = audioJack;
        this.gps = gps;
        this.battery = battery;
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