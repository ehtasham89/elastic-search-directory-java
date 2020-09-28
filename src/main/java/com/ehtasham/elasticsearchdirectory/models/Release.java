package com.ehtasham.elasticsearchdirectory.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Release {
	
	@JsonProperty("announceDate")
    private String announceDate;
	
	@JsonProperty("priceEur")
    private String priceEur;
	
	public Release() {}
	
	public Release(String announceDate, String priceEur) {
        this.announceDate = announceDate;
        this.priceEur = priceEur;
    }
	
	public String getPriceEur() {
		return this.priceEur;
	}
	
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
