package net.unicon.ccc.engine;

import java.util.Map;

import net.unicon.ccc.model.Rule;
import net.unicon.ccc.model.Student;
import net.unicon.ccc.model.Teacher;

import org.apache.log4j.Logger;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.stereotype.Service;

@Service
public class RulesEngine {
	Logger log = Logger.getLogger(RulesEngine.class);

	private static final String NEW_LINE = "\n";
	private static final String RULE_HEADER = "package com.sample";
	private static final String IMPORT_STUDENT_HEADER = "import net.unicon.ccc.model.Student;";
	private static final String IMPORT_TEACHER_HEADER = "import net.unicon.ccc.model.Teacher;";
	private static final String IMPORT_HEADER = IMPORT_STUDENT_HEADER + NEW_LINE +
			IMPORT_TEACHER_HEADER + NEW_LINE;
	
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
	/* sample rule:
	when
		t : Teacher()
		s : Student(s.getGrade() == t.getGrade() && s.getSpecialNeed() == t.getSpecialNeed())
		
	then
	    t.setCount(t.getCount() + 1);
	    s.setTeacherName(t.getName());
	*/
	
	public String runRules(Iterable<Rule> rules, Map<String, Student> students, Map<String, Teacher> teachers) {
		
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
        fireRules(ksession, students, teachers);
		return "";
	}
	
	private void fireRules(KieSession ksession, Map<String, Student> students, Map<String, Teacher> teachers) {
        for (Student student: students.values()) {
        	ksession.insert(student);
        }
        
        for (Teacher teacher: teachers.values()) {
        	ksession.insert(teacher);
        }
        
        ksession.fireAllRules();		
	}
}
