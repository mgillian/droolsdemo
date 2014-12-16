package net.unicon.ccc.model;

public class Teacher {
	private String name;
	private String grade;
	private String specialNeed;
	
	private int count = 0;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSpecialNeed() {
		return specialNeed;
	}

	public void setSpecialNeed(String specialNeed) {
		this.specialNeed = specialNeed;
	}
}
