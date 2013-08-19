package basic;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;
import java.util.Vector;

import utilities.StringUtil;

public class Class implements NamedEntity, Serializable{
	private static final long serialVersionUID = -4317770498263255354L;
	
	private String name;
	private Vector<Professor> professors;
	private double ch;
	private int ccSemester, ecSemester;
	private String course;
	private Vector<SlotRange> slots;
	private int ch2;
	private String code;
	private Color color;
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

	public int getCcSemester() {
		return ccSemester;
	}

	public void setCcSemester(String ccSemester) {
		if(StringUtil.isNullOrEmpty(ccSemester)) setCcSemester(-1);
		else setCcSemester(Integer.parseInt(ccSemester));
	}
	
	public void setCcSemester(int ccSemester) {
		this.ccSemester = ccSemester;
	}

	public int getEcSemester() {
		return ecSemester;
	}
	
	public void setEcSemester(String ecSemester) {
		if(StringUtil.isNullOrEmpty(ecSemester)) setEcSemester(-1);
		else setEcSemester(Integer.parseInt(ecSemester));
	}
	
	public void setEcSemester(int ecSemester) {
		this.ecSemester = ecSemester;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		if(StringUtil.isNullOrEmpty(course))
			course = null;
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
		if(StringUtil.isNullOrEmpty(code))
			code = null;
		this.code = code;
	}

	public void setName(String name){
		if(StringUtil.isNullOrEmpty(name)) 
			name = null;
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

	public void setColor(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}
	
	public String getHtmlColor(){
		return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
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
	
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
	
	public Vector<Classroom> getAllRooms(){
		Vector<Classroom> rooms = new Vector<Classroom>();
		for(SlotRange r : getSlots()) if(r.getClassroom() != null) rooms.add(r.getClassroom());
		return rooms;
	}
}
