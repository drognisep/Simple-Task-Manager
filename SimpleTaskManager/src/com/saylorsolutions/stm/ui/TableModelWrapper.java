package com.saylorsolutions.stm.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.saylorsolutions.stm.core.Task;
import com.saylorsolutions.stm.core.TaskList;

public class TableModelWrapper implements javax.swing.table.TableModel {
	private TaskList tl;
	private ArrayList<TableModelListener> listeners = new ArrayList<>();
	private TaskRowMapping trm;
	
	private ColumnMapping[] colMap = new ColumnMapping[] {
			new ColumnMapping("Name", String.class),
			new ColumnMapping("Description", String.class),
			new ColumnMapping("Priority", Boolean.class, String.class),
			new ColumnMapping("Pressing", Boolean.class, String.class),
			new ColumnMapping("Due Date", String.class)
	};
	
	public TableModelWrapper(TaskList list) {
		tl = list;
		trm = new TaskRowMapping();
	}

	@Override
	public int getRowCount() {
		return tl.size();
	}

	@Override
	public int getColumnCount() {
		return colMap.length;
	}

	@Override
	public String getColumnName(int cIdx) {
		return colMap[cIdx].getColName();
	}

	@Override
	public Class<?> getColumnClass(int cIdx) {
		return colMap[cIdx].getColClass()[0];
	}

	@Override
	public boolean isCellEditable(int rIdx, int cIdx) {
		return cIdx != 0;
	}

	@Override
	public Object getValueAt(int rIdx, int cIdx) {
		if(rIdx >= tl.size() || rIdx < 0) throw new IndexOutOfBoundsException();
		if(cIdx >= getColumnCount() || cIdx < 0) throw new IndexOutOfBoundsException();
		Task t = tl.get(rIdx);
		if(t == null) return null;
		
		return trm.setSubject(t).get(cIdx);
	}

	@Override
	public void setValueAt(Object aValue, int rIdx, int cIdx) {
		if(rIdx >= tl.size()) throw new IndexOutOfBoundsException();
		if(cIdx >= getColumnCount()) throw new IndexOutOfBoundsException();
//		Task t = tl.get(rIdx);
//		if(t == null) return;
		
		if(trm.setSubject(tl.get(rIdx)).set(cIdx, aValue)) {
			TableModelEvent e = new TableModelEvent(this, rIdx, rIdx, cIdx, TableModelEvent.UPDATE);
			notifyListeners(e);
		}
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}
	
	private void notifyListeners(TableModelEvent e) {
		for(TableModelListener l : listeners) {
			l.tableChanged(e);
		}
	}
	
	private class TaskRowMapping {
		private Task t;
		private ColumnMapping[] mapping;
		private Logger log = Logger.getLogger("TaskRowMapping");

		public TaskRowMapping() {
			this(null);
		}
		
		public TaskRowMapping(Task t) {
			super();
			this.t = t;
			this.mapping = colMap;
		}
		
		public TaskRowMapping setSubject(Task t) {
			this.t= t;
			return this;
		}
		
		public Object get(int idx) {
			SimpleDateFormat sdf = new SimpleDateFormat(Task.DATE_FORMAT);
			
			switch(idx) {
			case 0: // Name
				return t.getName();
			case 1: // Desc
				return t.getDesc();
			case 2: // Priority
				return t.isPriority();
			case 3: // Pressing
				return t.isPressing();
			case 4: // Due date
				Date d = t.getDueDate();
				return (d == null ? Task.NO_DUE_DATE : sdf.format(t.getDueDate()));
			default:
				throw new IllegalStateException("Unhandled columnIndex out of bounds");
			}
		}
		
		public boolean set(int idx, Object val) {
			if(val != null && !mapping[idx].matchesColumnClass(val.getClass())) {
				return false;
			}
			
			switch(idx) {
			case 0: // Name
				if(val == null) return false;
				t.setName((String)val);
				return true;
			case 1: // Description
				t.setDesc((String)val);
				return true;
			case 2: // Priority
				if(val instanceof Boolean) {
					t.setPriority((Boolean)val);
				} else if(val instanceof String) {
					t.setPriority(Boolean.parseBoolean((String)val));
				} else return false;
				return true;
			case 3: // Pressing
				if(val instanceof Boolean) {
					t.setPressing((Boolean)val);
				} else if(val instanceof String) {
					t.setPressing(Boolean.parseBoolean((String)val));
				} else return false;
				return true;
			case 4: // Due date
				SimpleDateFormat sdf = new SimpleDateFormat(Task.DATE_FORMAT);
				if(val instanceof String) {
					try {
						t.setDueDate(sdf.parse((String)val));
					} catch (ParseException e) {
						log.log(Level.SEVERE, "Invalid date format", e);
						return false;
					}
				} else if(val == null) {
					t.setDueDate(null);
					return true;
				} else return false;
				return true;
			default:
				log.log(Level.SEVERE, "Unrecognized index: " + idx);
				return false;
			}
		}
	}
}

class ColumnMapping {
	private Class<?>[] classes;
	private String name;
	public ColumnMapping(String name, Class<?>... classes) {
		super();
		this.classes = classes;
		this.name = name;
	}
	
	public ColumnMapping(String name) {
		this(name, String.class);
	}
	public Class<?>[] getColClass() {
		return classes;
	}
	public void setColClass(Class<?>... classes) {
		this.classes = classes;
	}
	public String getColName() {
		return name;
	}
	public void setColName(String name) {
		this.name = name;
	}
	public boolean matchesColumnClass(Class<?> cls) {
		for(Class<?> c : classes) {
			if(c.isAssignableFrom(cls)) return true;
		}
		return false;
	}
}
