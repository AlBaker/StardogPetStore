package com.example.stardog;

import java.net.URI;
import java.net.URISyntaxException;

public enum Constants {

	BASE("http://www.example.com/"),
	NAME("http://www.example.com/name"),
	WIKI("http://xmlns.com/foaf/0.1/page"),
	IMAGE("http://xmlns.com/foaf/0.1/depiction");
	
	
	private String value;
	
	Constants(String value) {
		this.value = value;
	}
	
	public String toString() {
		return value;
	}
	
	public URI asURI() {
		URI uri = null;
		try {
			 uri =  new URI(value);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return uri;
	}
	
}
