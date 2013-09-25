package data.persistentEntities;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import data.NamedEntity;

import utilities.CollectionUtil;
import utilities.StringUtil;

public class Class implements NamedEntity, Serializable{
	private static final long serialVersionUID = 8593555577464830048L;
	
	private String name;
	private double ch;
	private int ccSemester, ecSemester;
	private String course;
	private int ch2;
	private String code;
	private Color color;
	private int id;
	private ExcelMetadata excelMetadata;
	private Class alias;
	private AliasSharedData aliasSharedData;
	
	public Class(){
		aliasSharedData = new AliasSharedData();
		ccSemester = ecSemester = -1;
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
		aliasSharedData.slots.add(slot);
		Collections.sort(aliasSharedData.slots);
	}
	
	public Collection<SlotRange> getSlots(){
		return Collections.unmodifiableList(aliasSharedData.slots);
	}
	
	public List<Professor> getProfessors(){
		return Collections.unmodifiableList(aliasSharedData.professors);
	}
	
	public void addProfessor(Professor prof){
		aliasSharedData.professors.add(prof);
		sortProfessors();
	}
	
	public String getName() {
		return name;
	}

	public void setProfessors(Iterable<Professor> profs) {
		aliasSharedData.professors.clear();
		for(Professor p : profs) aliasSharedData.professors.add(p);
		
	}
	
	private void sortProfessors(){
		Collections.sort(aliasSharedData.professors, new Comparator<Professor>() {
			public int compare(Professor p1, Professor p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
	}
	
	public void setSlots(Iterable<SlotRange> newSlots) {
		aliasSharedData.slots.clear();
		for(SlotRange s : newSlots) aliasSharedData.slots.add(s);
		Collections.sort(aliasSharedData.slots);
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
	
	public List<Classroom> getRoomsOrderedBySlot(){
		List<SlotRange> slots = new LinkedList<SlotRange>(aliasSharedData.slots);
		LinkedList<Classroom> rooms = new LinkedList<Classroom>();
		Collections.sort(slots);
		
		for(SlotRange slot : slots){
			if(slot.getClassroom() != null)
				rooms.add(slot.getClassroom());
		}
		
		if(CollectionUtil.distinct(rooms).size() == 1){
			while(rooms.size() > 1)
				rooms.removeLast();
		}
		
		return rooms;
	}
	
	public List<Classroom> getAllRooms(){
		List<Classroom> rooms = new LinkedList<Classroom>();
		for(SlotRange r : getSlots()) if(r.getClassroom() != null) rooms.add(r.getClassroom());
		return rooms;
	}
	
	public void setExcelMetadata(ExcelMetadata meta){
		this.excelMetadata = meta;
	}
	
	public ExcelMetadata getExcelMetadata(){
		return excelMetadata;
	}
	
	public void setAlias(Class other){
		this.alias = other;
		if(other.getAlias() != this){
			other.setAlias(this);
			
			if(getSlots().isEmpty()) 
				aliasSharedData.slots = other.aliasSharedData.slots;
			if(getProfessors().isEmpty()) 
				aliasSharedData.professors = other.aliasSharedData.professors;
			
			other.aliasSharedData = aliasSharedData;
		}
	}
	
	public Class getAlias(){
		return alias;
	}
	
	public String getFormattedAliasName(){
		if(alias != null) 
			return String.format("[%s]", alias.completeName());
		return "";
	}

	public boolean canBeAliasOf(Class another) {
		return aliasSharedData.isCompatibleWith(another.aliasSharedData);
	}
}

class AliasSharedData implements Serializable{
	private static final long serialVersionUID = -7071841203665546844L;
	
	public List<Professor> professors;
	public List<SlotRange> slots;
	
	public AliasSharedData(){
		professors = new LinkedList<Professor>();
		slots = new LinkedList<SlotRange>();
	}

	public boolean isCompatibleWith(AliasSharedData another) {
		return areTheSlotsCompatible(another) && areTheProfessorsCompatible(another);
	}
	
	private boolean areTheSlotsCompatible(AliasSharedData another){
		boolean compatible = true;
		
		if(!slots.isEmpty() && !another.slots.isEmpty()){
			List<SlotRange> other = another.slots;
			if(slots.size() != other.size()) compatible = false;
			else{
				for(int i = 0; i < slots.size(); ++i){
					SlotRange r1 = slots.get(i), r2 = other.get(i);
					if(!r1.equals(r2) || r1.getClassroom() != r2.getClassroom()) compatible = false;
				}
			}
		}
		
		return compatible;
	}
	
	private boolean areTheProfessorsCompatible(AliasSharedData another){
		return CollectionUtil.equalsWithoutOrder(professors, another.professors);
	}
}

