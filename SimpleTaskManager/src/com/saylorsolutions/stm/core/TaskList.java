package com.saylorsolutions.stm.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * Specialized extension of ArrayList for Task objects. Note: this class
 * disallows duplicates based on task name.
 * 
 * @author Doug Saylor
 * @see ArrayList
 * @see Task
 */
public class TaskList extends ArrayList<Task> {
	private static final long serialVersionUID = 4653534838311744846L;
	public static final String DEFAULT_LIST_NAME = "<Orphaned Tasks>";
	private String listName;

	public TaskList(String listName, Task... tasks) {
		this.listName = (listName == null || listName.equals("") ? 
				DEFAULT_LIST_NAME : listName);
		if (tasks.length > 0)
			addAll(Arrays.asList(tasks));
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
		for(Task t : this) {
			t.setListName(listName);
		}
	}

	@Override
	public boolean add(Task t) {
		if(t == null) return false;
		if (contains(t.getName()))
			return false;
		t.setListName(getListName());
		return super.add(t);
	}

	@Override
	public void add(int index, Task element) {
		if(element == null) return;
		Task t = element;
		if (contains(element.getName())) {
			t = get(element.getName());
			if (!element.equals(t))
				return;
			remove(t);
		}
		t.setListName(getListName());
		super.add(index, t);
	}

	@Override
	public boolean addAll(Collection<? extends Task> c) {
		for (Task t : c) {
			if (t == null || contains(t.getName()))
				return false;
		}
		for (Task t : c) {
			t.setListName(getListName());
		}
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Task> c) {
		for (Task t : c) {
			if (t == null || contains(t.getName()))
				return false;
		}
		for (Task t : c) {
			t.setListName(getListName());
		}
		return super.addAll(index, c);
	}

	/**
	 * Sort the list descending using Task.compareTo(Task).
	 * 
	 * @see Task
	 */
	public void sort() {
		super.sort(new Comparator<Task>() {
			@Override
			public int compare(Task o1, Task o2) {
				return o2.compareTo(o1); // Reversed on purpose, want to sort
											// descending by default
			}
		});
	}

	@Override
	public Object[] toArray() {
		Object[] ary = new Object[size()];
		for (int i = 0; i < ary.length; i++) {
			ary[i] = get(i);
		}
		return ary;
	}

	/**
	 * Checks if an object in the collection has a name that matches the 
	 * argument.
	 * @param name Unique name to search for
	 * @return Whether or not the name already exists
	 */
	public boolean contains(String name) {
		for (Task t : this) {
			if (t.getName().equals(name))
				return true;
		}
		return false;
	}

	/**
	 * Gets a Task object in the collection that has a name that 
	 * matches the argument.
	 * @param name Name to search for
	 * @return The object with a matching name, null otherwise
	 */
	public Task get(String name) {
		for (Task t : this) {
			if (t.getName().equals(name))
				return t;
		}
		return null;
	}
}
