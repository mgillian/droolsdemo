package net.unicon.ccc.controller;

import java.util.ArrayList;
import java.util.List;

import net.unicon.ccc.engine.RulesEngine;
import net.unicon.ccc.model.Rule;
import net.unicon.ccc.model.Applicant;
import net.unicon.ccc.service.RulesService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	Logger log = Logger.getLogger(IndexController.class);
	
	@Autowired
	private RulesService ruleService;
	
	@Autowired
	private RulesEngine rulesEngine;
	
	private List<Applicant> applicants = new ArrayList<Applicant>();
	
	private String result = "";
	
    @RequestMapping("/index")
    public ModelAndView index() {
    	ModelAndView modelAndView = new ModelAndView("index");
    	List<Rule> rules = ruleService.findAll();
    	modelAndView.addObject("rules", rules);
    	modelAndView.addObject("applicants", applicants);
    	modelAndView.addObject("result", result);
		return modelAndView;
	}
    
    @RequestMapping("/addRule")
    public ModelAndView save(Rule model) {
    	ruleService.saveRule(model);
    	ModelAndView modelAndView = new ModelAndView("redirect:/index");
    	return modelAndView;
    }
    
    @RequestMapping("/deleteRule/{id}")
    public ModelAndView delete(@PathVariable("id") long id) {
    	ruleService.deleteRule(id);
    	ModelAndView modelAndView = new ModelAndView("redirect:/index");
    	return modelAndView;
    }
    
    @RequestMapping(value="/addApplicant", method = RequestMethod.POST)
    public ModelAndView addApplicant(@ModelAttribute("applicant") Applicant applicant, BindingResult result) {
    	if (result.hasErrors()) {
    		for (Object obj: result.getAllErrors()) {
    			log.info("error: [" + obj.toString() + "]");
    		}
    	}
    	applicants.add(applicant);
    	Iterable<Rule> rules = this.ruleService.findAll();
    	rulesEngine.runRules(rules, applicant);
    	ModelAndView modelAndView = new ModelAndView("redirect:/index");
    	return modelAndView;    	
    }
    
    @RequestMapping("/deleteApplicant/{applicantName}")
    public ModelAndView deleteApplicant(@PathVariable("applicantName") String applicantName) {
    	Applicant deletedApplicant = null;
    	for (Applicant applicant: applicants) {
    		if (applicant.getName().equals(applicantName)) {
    			deletedApplicant = applicant;
    		}
    	}
    	if (deletedApplicant != null) {
    		applicants.remove(deletedApplicant);
    	}
    	ModelAndView modelAndView = new ModelAndView("redirect:/index");
    	return modelAndView;    	
    }
    
    @RequestMapping("/load") 
    public ModelAndView run() {
    	List<Rule> rules = rulesEngine.loadRules();
    	for (Rule rule : rules) {
        	ruleService.saveRule(rule);
    	}
    	ModelAndView modelAndView = new ModelAndView("redirect:/index");
    	return modelAndView;
    }
    
    @RequestMapping("/reset")
    public ModelAndView reset() {
    	applicants.clear();
		ModelAndView modelAndView = new ModelAndView("redirect:/index");
		return modelAndView;
    }
}
