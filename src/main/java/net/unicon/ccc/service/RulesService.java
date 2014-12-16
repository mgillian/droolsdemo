package net.unicon.ccc.service;

import java.util.List;

import net.unicon.ccc.model.Rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RulesService {
	
	private final RuleRepository ruleRepository;
	
	@Autowired
	public RulesService(RuleRepository ruleRepository) {
		this.ruleRepository = ruleRepository;
	}
	
	public List<Rule> findAll() {
		return (List<Rule>) this.ruleRepository.findAll();
	}
	
	public int saveRule(Rule rule) {
		this.ruleRepository.save(rule);
		return 0;
	}
	
	public void deleteRule(long id) {
		this.ruleRepository.delete(id);
	}
}
