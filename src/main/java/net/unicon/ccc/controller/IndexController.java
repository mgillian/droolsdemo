package net.unicon.ccc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.unicon.ccc.engine.RulesEngine;
import net.unicon.ccc.model.Rule;
import net.unicon.ccc.model.Student;
import net.unicon.ccc.model.Teacher;
import net.unicon.ccc.service.RulesService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	Logger log = Logger.getLogger(IndexController.class);
	
	@Autowired
	private RulesService ruleService;
	
	@Autowired
	private RulesEngine rulesEngine;
	
	private Map<String, Student> students = new HashMap<String, Student>();
	private Map<String, Teacher> teachers = new HashMap<String, Teacher>();
	
	private String result = "";
	
    @RequestMapping("/index")
    public ModelAndView index() {
    	ModelAndView modelAndView = new ModelAndView("index");
    	List<Rule> rules = ruleService.findAll();
    	modelAndView.addObject("rules", rules);
    	modelAndView.addObject("students", students);
    	modelAndView.addObject("teachers", teachers);
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
    
    @RequestMapping(value="/addStudent", method = RequestMethod.POST)
//    public ModelAndView addStudent(@RequestParam("name") String name, @RequestParam("grade") String grade, @RequestParam("specialNeed") String specialNeed) {
    public ModelAndView addStudent(@ModelAttribute("student") Student student, BindingResult result) {
    	if (result.hasErrors()) {
    		for (Object obj: result.getAllErrors()) {
    			log.info("error: [" + obj.toString() + "]");
    		}
    	}
//    	Student student = new Student();
//    	student.setName(name);
//    	student.setGrade(grade);
//    	student.setSpecialNeed(specialNeed);
    	log.info("student.veteran: [" + student.isVeteran() + "]");
    	students.put(student.getName(), student);
    	ModelAndView modelAndView = new ModelAndView("redirect:/index");
    	return modelAndView;    	
    }
    
    @RequestMapping("/deleteStudent/{studentName}")
    public ModelAndView deleteStudent(@PathVariable("studentName") String studentName) {
    	students.remove(studentName);
    	ModelAndView modelAndView = new ModelAndView("redirect:/index");
    	return modelAndView;    	
    }
    
//    @RequestMapping("/addTeacher")
//    public ModelAndView addTeacher(@RequestParam("name") String name, @RequestParam("grade") String grade, @RequestParam("specialNeed") String specialNeed) {
//    	Teacher teacher = new Teacher();
//    	teacher.setName(name);
//    	teacher.setGrade(grade);
//    	teacher.setSpecialNeed(specialNeed);
//    	teachers.put(teacher.getName(), teacher);
//    	ModelAndView modelAndView = new ModelAndView("redirect:/index");
//    	return modelAndView;    	
//    }
//    
//    @RequestMapping("/deleteTeacher/{teacherName}")
//    public ModelAndView deleteTeacher(@PathVariable("teacherName") String teacherName) {
//    	teachers.remove(teacherName);
//    	ModelAndView modelAndView = new ModelAndView("redirect:/index");
//    	return modelAndView;    	
//    }
    
    @RequestMapping("/run") 
    public ModelAndView run() {
    	Iterable<Rule> rules = this.ruleService.findAll();
    	rulesEngine.runRules(rules, students, teachers);
    	ModelAndView modelAndView = new ModelAndView("redirect:/index");
    	return modelAndView;
    }
    
    @RequestMapping("/reset")
    public ModelAndView reset() {
    	students.clear();
//    	for (Student student: students.values()) {
//    		student.setTeacherName("");
//    	}
//    	for (Teacher teacher: teachers.values()) {
//    		teacher.setCount(0);
//    	}
		ModelAndView modelAndView = new ModelAndView("redirect:/index");
		return modelAndView;
    }
}
