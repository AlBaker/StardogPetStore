package com.example.stardog;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.mortbay.log.Log;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.BindingSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clarkparsia.stardog.StardogException;
import com.clarkparsia.stardog.api.Remover;
import com.clarkparsia.stardog.ext.spring.GetterCallback;
import com.clarkparsia.stardog.ext.spring.RemoverCallback;
import com.clarkparsia.stardog.ext.spring.RowMapper;
import com.clarkparsia.stardog.ext.spring.SnarlTemplate;

/**
 * DogDAO
 * 
 * A classic Data Access Object, showing how to use SnarlTemplate for full control
 * on how to persist a POJO, in this caes a simple class called Dog
 * 
 * Like jdbcTemplate in Spring, SnarlTemplate eliminates boilerplate connection management
 * in favor of just dealing with the query language, SPARQL in this case, directly
 * 
 * This class would be further simplified by using Empire and JPA annotations on the
 * dog class -- perhaps a future addition to this sample
 * 
 * @author Al Baker
 *
 */
@Component
public class DogDAO {

	private static final Logger logger = LoggerFactory.getLogger(DogDAO.class);
	
	@Autowired
	private SnarlTemplate snarlTemplate;
	
	/**
	 * <code>list</code>
	 * Lists all of the dogs in the store in no particular order
	 * @return List<Dog>
	 */
	public List<Dog> list() {
		
		String query = "SELECT ?subject ?name ?wiki ?image WHERE { ?subject <"+Constants.NAME.toString() + "> ?name . " +
				" OPTIONAL { ?subject <" + Constants.WIKI.toString() + "> ?wiki  } . " +
				" OPTIONAL { ?subject <" + Constants.IMAGE.toString() + "> ?image } }";
		
		logger.debug("DAO Query: " + query);
		List<Dog> results = snarlTemplate.query(query, new RowMapper<Dog>() {

			public Dog mapRow(BindingSet bindingSet) {
				Dog d = new Dog();
				d.setName(bindingSet.getValue("name").stringValue());
				try {
					if (bindingSet.getValue("wiki") != null) {
						d.setWikiPage(new URI(bindingSet.getValue("wiki").stringValue()));
					}
					if (bindingSet.getValue("image") != null) {
						d.setPicture(new URI(bindingSet.getValue("image").stringValue()));
					}
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return d;
				
			} });
		return results;
	}
	
	/**
	 * <code>get</code>
	 * @param name of the dog, not a fully qualified URI
	 * @return Dog instance
	 */
	public Dog get(String name) {
		String nameUri = createNewSubject(name);
		String query = "SELECT ?name ?wiki ?image WHERE { <"+nameUri+"> <"+Constants.NAME + "> ?name . " +
				"OPTIONAL { <"+nameUri+"> <" + Constants.WIKI + "> ?wiki }. " +
				"OPTIONAL { <"+nameUri+"> <" + Constants.IMAGE + "> ?image } }";
		
		List<Dog> results = snarlTemplate.query(query, new RowMapper<Dog>() {

			public Dog mapRow(BindingSet bindingSet) {
				Dog d = new Dog();
				d.setName(bindingSet.getValue("name").stringValue());
				try {
					if (bindingSet.getValue("wiki") != null) {
						d.setWikiPage(new URI(bindingSet.getValue("wiki").stringValue()));
					}
					if (bindingSet.getValue("image") != null) {
						d.setWikiPage(new URI(bindingSet.getValue("image").stringValue()));
					}
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return d;
			} });
		
		if (results.size() > 0) {
			return results.remove(0);
		} else {
			return null;
		}
	}
	
	/**
	 * <code>exists</code>
	 * @param dog to check if it exists in Stardog
	 * @return boolean
	 */
	public boolean exists(Dog dog) {
		List<String> results = snarlTemplate.doWithGetter(createNewSubject(dog.getName()),null, new GetterCallback<String>() {
			public String processStatement(Statement statement) {
				return statement.getObject().stringValue();
			} 
		});
		if (results.size() > 0) {
			return true;
		} else { 
			return false;
		}
	}
	
	/**
	 * <code>add</code>
	 * Adds a Dog POJO to stardog
	 * @param dog instance
	 */
	public void add(Dog dog) {
		
		try {
			// TODO / Note, real implementation would have connection call back and
			// wrapped in tx here, perhaps spring mangaed tx
			URI subject = new URI(createNewSubject(dog.getName()));
			snarlTemplate.add(subject, Constants.NAME.asURI(), dog.getName());
			snarlTemplate.add(subject, Constants.WIKI.asURI(), dog.getWikiPage());
			snarlTemplate.add(subject, Constants.IMAGE.asURI(), dog.getPicture());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * <code>remove</code>
	 * @param dog instance to remove
	 */
	public void remove(final Dog dog) {
		snarlTemplate.doWithRemover(new RemoverCallback<Boolean>() {
			public Boolean remove(Remover remover) throws StardogException {
				remover.statements(new URIImpl(createNewSubject(dog.getName())), null, null);
			//	remover.statements(new URIImpl(createNewSubject(dog.getName())), new URIImpl(Constants.WIKI.toString()), null);
			//	remover.statements(new URIImpl(createNewSubject(dog.getName())), new URIImpl(Constants.IMAGE.toString()), null);
				return true;
			} 
		});
	}
	
	/**
	 * <code>update</code>
	 * updates a dog POJO in Stardog
	 * @param dog instance
	 */
	public void update(Dog dog) {
		remove(dog);
		add(dog);
	}
	
	/**
	 * <code>createNewSubject</code>
	 * Creates a URI for the dog based on the name
	 * @param dogName 
	 * @return URI
	 */
	private String createNewSubject(String dogName) {
		return Constants.BASE + "#" + dogName.toLowerCase();
	}

	public SnarlTemplate getSnarlTemplate() {
		return snarlTemplate;
	}

	public void setSnarlTemplate(SnarlTemplate snarlTemplate) {
		this.snarlTemplate = snarlTemplate;
	}
	
}
