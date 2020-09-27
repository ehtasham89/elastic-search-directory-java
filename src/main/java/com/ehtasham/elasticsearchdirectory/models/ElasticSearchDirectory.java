package com.ehtasham.elasticsearchdirectory.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import static org.springframework.data.elasticsearch.annotations.FieldType.Nested;
import org.springframework.stereotype.Component;

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
@Document(indexName = "mobiledirectory9")
public class ElasticSearchDirectory {
    @Field(type = FieldType.Text)
    @Id
    private String id;
    
    @Field(type = FieldType.Text)
    @JsonProperty("brand")
    public String brand;
    
    @Field(type = FieldType.Text)
    @JsonProperty("phone")
    private String phone;
    
    @Field(type = FieldType.Text)
    @JsonProperty("picture")
    private String picture;
    
    @Field(type = Nested, includeInParent = true)
    @JsonProperty("release")
    private Release release;
    
    @Field(type = FieldType.Text)
    @JsonProperty("sim")
    private String sim;
    
    @Field(type = FieldType.Text)
    @JsonProperty("resolution")
    private String resolution;
    
    @Field(type = Nested, includeInParent = true)
    @JsonProperty("hardware")
    private Hardware hardware;

    public Hardware getHardware(){
        return new Hardware();
    }

    public Release getRelease(){
    	return new Release();
    }
    
    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }
    
    @Override
    public String toString() {
        return "ElasticSearchDirectory{" + "id='" + id + '\'' + ", brand='" + brand + '\'' + ", phone=" + phone + ", picture=" + picture + ", release=" + release + ", sim=" + sim + ", resolution=" + resolution + ", hardware=" + hardware + '}';
    }

}
