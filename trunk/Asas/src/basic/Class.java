package basic;

import java.io.Serializable;
import java.util.Collection;
import java.util.Vector;

public class Class implements NamedEntity, Serializable{
	private String name;
	private Vector<Professor> professors;
	private double ch;
	private Classroom classroom;
	private int ccSemester, ecSemester;
	private String course;
	private Vector<SlotRange> slots;
	private int ch2;
	private String code;
	private String color;
	private int id;
	
	public Class(){
		this.professors = new Vector<Professor>();
		this.slots = new Vector<SlotRange>();
	}
		
	public double getCh() {
		return ch;
	}

	public void setCh(double ch) {
		this.ch = ch;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public int getCcSemester() {
		return ccSemester;
	}

	public void setCcSemester(String ccSemester) {
		if(ccSemester == null || ccSemester.trim().equals("")) setCcSemester(-1);
		else setCcSemester(Integer.parseInt(ccSemester));
	}
	
	public void setCcSemester(int ccSemester) {
		this.ccSemester = ccSemester;
	}

	public int getEcSemester() {
		return ecSemester;
	}
	
	public void setEcSemester(String ecSemester) {
		if(ecSemester == null || ecSemester.trim().equals("")) setEcSemester(-1);
		else setEcSemester(Integer.parseInt(ecSemester));
	}
	
	public void setEcSemester(int ecSemester) {
		this.ecSemester = ecSemester;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public int getCh2() {
		return ch2;
	}

	public void setCh2(int ch2) {
		this.ch2 = ch2;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public void addSlot(SlotRange slot){
		this.slots.add(slot);
	}
	
	public Collection<SlotRange> getSlots(){
		return slots;
	}
	
	public Collection<Professor> getProfessors(){
		return professors;
	}
	
	public void addProfessor(Professor prof){
		this.professors.add(prof);
	}

	public String getName() {
		return name;
	}

	public void setProfessors(Iterable<Professor> profs) {
		professors.clear();
		for(Professor p : profs) professors.add(p);
	}

	public void setSlots(Iterable<SlotRange> newSlots) {
		slots.clear();
		for(SlotRange s : newSlots) slots.add(s);
	}

	public void setColor(String color){
		this.color = color;
	}
	
	public String getHtmlColor() {
		return "#" + color;
	}
	
	public void setId(int i){
		id = i;
	}
	
	public int getId(){
		return id;
	}
	
	public boolean equals(Object ot){
		Class other = (Class) ot;
		return id == other.getId();
	}
	
	public String completeName(){
		String completeName = "";
		if(code != null && !code.equals("")) completeName = code + " - ";
		completeName += name;
		return completeName;
	}
}
