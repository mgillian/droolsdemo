package net.unicon.ccc.model;

public class Student {
	
	private String name;
//	private String grade;
//	private String teacherName;
//	private String specialNeed;
	private boolean veteran;
	private boolean married;
	private boolean parent;
	private boolean disabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getGrade() {
//		return grade;
//	}
//
//	public void setGrade(String grade) {
//		this.grade = grade;
//	}
//
//	public String getTeacherName() {
//		return teacherName;
//	}
//
//	public void setTeacherName(String teacherName) {
//		this.teacherName = teacherName;
//	}
//
//	public String getSpecialNeed() {
//		return specialNeed;
//	}
//
//	public void setSpecialNeed(String specialNeed) {
//		this.specialNeed = specialNeed;
//	}

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

}
