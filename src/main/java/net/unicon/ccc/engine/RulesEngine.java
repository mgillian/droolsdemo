package net.unicon.ccc.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.unicon.ccc.model.Rule;
import net.unicon.ccc.model.Applicant;

import org.apache.log4j.Logger;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

@Service
public class RulesEngine {
	Logger log = Logger.getLogger(RulesEngine.class);

	private static final String NEW_LINE = "\n";
	private static final String RULE_HEADER = "package com.sample";
	private static final String IMPORT_APPLICANT_HEADER = "import net.unicon.ccc.model.Applicant;";
	private static final String IMPORT_HEADER = IMPORT_APPLICANT_HEADER + NEW_LINE;
	
	
	public List<Rule> loadRules() {
		List<Rule> rules = new ArrayList<Rule>();
		String filePath = "./rules/rules.json";
		
		try {
			//read json file data to String
	        byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
	        
	        //create ObjectMapper instance
	        ObjectMapper objectMapper = new ObjectMapper();
	         
	        //convert json string to object
	        Rule[] ruleArray = objectMapper.readValue(jsonData, Rule[].class);
	        rules = Lists.newArrayList(ruleArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		for (Rule rule: rules) {
			log.info("rule: [" + rule.getName() + ", " + rule.getAction() + ", " + rule.getComparison() + "]");
		}
		return rules;
	}
	
	public String buildRuleString(Iterable<Rule> rules) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(RULE_HEADER).append(NEW_LINE);
		buffer.append(IMPORT_HEADER).append(NEW_LINE);
		for (Rule rule: rules) {
			buffer.append("rule \"" + rule.getName() + "\"" + NEW_LINE);
			buffer.append("when ").append(NEW_LINE);
			buffer.append(rule.getComparison()).append(NEW_LINE);
			buffer.append("then ").append(NEW_LINE);
			buffer.append(rule.getAction()).append(NEW_LINE);
			buffer.append("end").append(NEW_LINE);
			buffer.append("").append(NEW_LINE);
		}
		
		log.info("rule: [" + buffer + "]");
		return buffer.toString();
	}
	
	public String runRules(Iterable<Rule> rules, Applicant applicant) {
		
		String ruleString = this.buildRuleString(rules);

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add( ResourceFactory.newByteArrayResource( ruleString.getBytes() ),
                      ResourceType.DRL );

        if ( kbuilder.hasErrors() ) {
            System.err.println( kbuilder.getErrors() );
        }

		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages( kbuilder.getKnowledgePackages() );

        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        
       	ksession.insert(applicant);        
        ksession.fireAllRules();		
		return "";
	}
}
