package com.example.stardog;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Simple Spring MVC controller, wires in the DogDAO class and works
 * completely off of the POJOs, returning them to the view
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private DogDAO dogDAO;
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
	
		List<Dog> dogList = dogDAO.list();
		logger.debug("Found " + dogList.size() + " number of dogs in the store");
		model.addAttribute("dogList", dogList);	
		return "home";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute(new Dog());
		return "createForm";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String saveDog(Dog dog, BindingResult result) {
		if (result.hasErrors()) {
			return "createForm";
		}
		if (dogDAO.exists(dog)) {
			logger.debug("Updating dog (" + dog.getName() + ")");
			dogDAO.update(dog);
		} else {
			logger.debug("Adding new dog (" + dog.getName() + ")");
			dogDAO.add(dog);
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String remove(@PathVariable String id, Model model) {
		Dog dog = dogDAO.get(id);
		dogDAO.remove(dog);
		return "redirect:/";
	}
		
	public DogDAO getDogDAO() {
		return dogDAO;
	}

	public void setDogDAO(DogDAO dogDAO) {
		this.dogDAO = dogDAO;
	}
	
	
}
