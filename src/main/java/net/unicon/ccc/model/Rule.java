package net.unicon.ccc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Rule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = true)
	private String description;
	
	@Column(nullable = false)
	private String comparison;
	
	@Column(nullable = false)
	private String action;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getComparison() {
		return comparison;
	}
	
	public void setComparison(String comparison) {
		this.comparison = comparison;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
}
