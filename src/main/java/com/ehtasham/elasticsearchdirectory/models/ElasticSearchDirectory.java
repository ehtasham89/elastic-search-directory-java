package com.ehtasham.elasticsearchdirectory.models;

import static org.springframework.data.elasticsearch.annotations.FieldType.Nested;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Elastic Search Directory Data Model
 *
 * @author Ehtasham Nasir <ehtasham.nasir89@gmail.com/>
 */
@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "mobiledirectory16")
public class ElasticSearchDirectory {
    @Field(type = FieldType.Text)
    @Id
    private String id;
    
    @Field(type = FieldType.Text)
    @JsonProperty("brand")
    private String brand;
    
    @Field(type = FieldType.Text)
    @JsonProperty("phone")
    private String phone;
    
    @Field(type = FieldType.Text)
    @JsonProperty("picture")
    private String picture;
    
    
    @JsonIgnore
    private Release release;
    
    @Field(type = Nested, includeInParent = true)
    @JsonProperty("release")
    private Release _release;
    
    @Field(type = FieldType.Text)
    @JsonProperty("sim")
    private String sim;
    
    @Field(type = FieldType.Text)
    @JsonProperty("resolution")
    private String resolution;
    
    
    @JsonIgnore
    private Hardware hardware;
    
    @Field(type = Nested, includeInParent = true)
    @JsonProperty("hardware")
    private Hardware _hardware;


    public ElasticSearchDirectory() {
 
    }
    
    public Hardware getHardware(){
        return new Hardware();
    }

    public Release getRelease(){
    	return new Release();
    }
    
    @SuppressWarnings("unchecked")
    @JsonProperty("release")
    private void unpackRelease(Map<String, Object> obj) {
    	_release = new Release(obj.get("announceDate").toString(), obj.get("priceEur").toString());
    	
    }
    
    @SuppressWarnings("unchecked")
    @JsonProperty("hardware")
    private void unpackHardware(Map<String, Object> obj) {
    	_hardware = new Hardware(obj.get("audioJack").toString(), obj.get("gps").toString(), obj.get("battery").toString());
    	
    }
    
    @Override
    public String toString() {
        return "ElasticSearchDirectory{" + "id='" + id + '\'' + ", brand='" + brand + '\'' + ", phone=" + phone + ", picture=" + picture + ", release=" + release + ", sim=" + sim + ", resolution=" + resolution + ", hardware=" + hardware + '}';
    }

}
