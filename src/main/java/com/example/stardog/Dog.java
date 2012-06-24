package com.example.stardog;

import java.net.URI;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Simple POJO of our domain
 *
 */
public class Dog {

	@NotNull
	@Size(min=1, max=25)
	private String name;
	
	private URI wikiPage;
	
	private URI picture;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URI getWikiPage() {
		return wikiPage;
	}

	public void setWikiPage(URI wikiPage) {
		this.wikiPage = wikiPage;
	}

	public URI getPicture() {
		return picture;
	}

	public void setPicture(URI picture) {
		this.picture = picture;
	}
	
}
