package com.saylorsolutions.stm.core;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Task implements Serializable, Comparable<Task> {
	public static final Logger log = Logger.getLogger("com.saylorsolutions.stm.core.Task");
	public static final String FIELD_SEPARATOR = " | ";
	public static final String DATE_FORMAT = "dd-MMM-yyyy";
	public static final String DEFAULT_NAME = "<Unnamed>";
	public static final String DEFAULT_DESC = "";
	public static final String NO_DUE_DATE = "<No Date Assigned>";
	private static final long serialVersionUID = 3689071554940979246L;
	private String name, desc, listName;
	private boolean priority, pressing;
	private Date dueDate;
	
	public Task() {}
	
	public Task(String name, String desc, boolean priority, boolean pressing, Date dueDate) {
		super();
		this.name = name;
		this.desc = desc;
		this.priority = priority;
		this.pressing = pressing;
		this.dueDate = dueDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name == null || name.equals("")) this.name = DEFAULT_NAME;
		else this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		if(desc == null || desc.equals("")) this.desc = DEFAULT_DESC;
		else this.desc = desc;
	}

	public boolean isPriority() {
		return priority;
	}

	public void setPriority(boolean priority) {
		this.priority = priority;
	}

	public boolean isPressing() {
		return pressing;
	}

	public void setPressing(boolean pressing) {
		this.pressing = pressing;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Task) {
			Task t = (Task)obj;
			if(t.getName().equals(getName()) &&
				t.getDesc().equals(getDesc()) &&
				t.isPriority() == isPriority() &&
				t.isPressing() == isPressing()) {
				if(t.getDueDate() != null && getDueDate() != null) {
					return t.getDueDate().equals(getDueDate());
				} else {
					if(t.getDueDate() == null) return getDueDate() == null;
					return t.getDueDate() == null;
				}
			}
			return false;
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		if(getDueDate() == null) {
			sb.append(NO_DUE_DATE + FIELD_SEPARATOR);
		} else {
			sb.append(sdf.format(getDueDate()) + FIELD_SEPARATOR);
		}
		sb.append((isPriority() ? "P" : "_") + FIELD_SEPARATOR);
		sb.append((isPressing() ? "p" : "_") + FIELD_SEPARATOR);
		sb.append(getName() + FIELD_SEPARATOR);
		sb.append(getDesc() + System.lineSeparator());
		return sb.toString();
	}
	
	public static Task fromString(String task) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Task t = new Task();
		StringTokenizer st = new StringTokenizer(task, "|");
		int fieldPointer = 0;
		for(fieldPointer = 0; st.hasMoreTokens(); fieldPointer++) {
			String token = st.nextToken().trim();
			switch(fieldPointer) {
			case 0: // Date
				try {
					t.setDueDate(sdf.parse(token));
				} catch (ParseException px) {
					log.log(Level.SEVERE, "Invalid date format", px);
					t.setDueDate(null);
				}
				break;
			case 1: // Priority
				t.setPriority(token.equals("P"));
				break;
			case 2: // Pressing
				t.setPressing(token.equals("p"));
				break;
			case 3: // Name
				t.setName(token);
				break;
			case 4: // Description
				t.setDesc(token);
				break;
			default:
				log.log(Level.WARNING, "Extra token found: " + token);
			}
		}
		return t;
	}

	@Override
	public int compareTo(Task o) {
		// Put items at the top of the list that are past due
		if(getDueDate() != null && getDueDate().compareTo(new Date()) < 0) {
			if(o.getDueDate() != null) return o.getDueDate().compareTo(getDueDate());
			return 1;
		}
		if(o.getDueDate() != null && o.getDueDate().compareTo(new Date()) < 0) {
			if(getDueDate() != null) return o.getDueDate().compareTo(getDueDate());
			return -1;
		}
		
		if(o.isPriority() != isPriority()) {
			if(isPriority()) return 1;
			return -1;
		} else if(o.isPressing() != isPressing()) {
			if(isPressing()) return 1;
			return -1;
		}
		
		if(getDueDate() != null && o.getDueDate() != null) {
			
			return o.getDueDate().compareTo(getDueDate());
		} else if(getDueDate() == null && o.getDueDate() == null) ; // Do nothing
		else {
			if(o.getDueDate() == null) return 1;
			else return -1;
		}
		
		if(!o.getName().equals(getName())) {
			return getName().compareToIgnoreCase(o.getName()) * -1;
		} else if(!o.getDesc().equals(getDesc())) {
			return getDesc().compareToIgnoreCase(o.getDesc()) * -1;
		}
		return 0;
	}
}
