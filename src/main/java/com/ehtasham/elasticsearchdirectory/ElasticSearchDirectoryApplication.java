package com.ehtasham.elasticsearchdirectory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ehtasham.elasticsearchdirectory.controllers.SearchController;


@SpringBootApplication
@Configuration
@ComponentScan(basePackageClasses = SearchController.class)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class })
public class ElasticSearchDirectoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchDirectoryApplication.class, args);
	}
}