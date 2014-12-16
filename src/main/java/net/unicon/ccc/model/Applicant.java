package net.unicon.ccc.model;

import java.util.ArrayList;
import java.util.List;

public class Applicant {
	
	private String name;
	private boolean veteran;
	private boolean married;
	private boolean parent;
	private boolean disabled;
	private List<String> tasks = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVeteran() {
		return veteran;
	}

	public void setVeteran(boolean veteran) {
		this.veteran = veteran;
	}

	public boolean isMarried() {
		return married;
	}

	public void setMarried(boolean married) {
		this.married = married;
	}

	public boolean isParent() {
		return parent;
	}

	public void setParent(boolean parent) {
		this.parent = parent;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public List<String> getTasks() {
		return tasks;
	}

	public void setTasks(List<String> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(String task) {
		if (!this.tasks.contains(task)) {
			this.tasks.add(task);
		}
	}
}
