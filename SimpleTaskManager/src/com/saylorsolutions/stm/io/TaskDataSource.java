package com.saylorsolutions.stm.io;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.saylorsolutions.stm.core.Task;
import com.saylorsolutions.stm.core.TaskList;
import java.util.Date;

public abstract class TaskDataSource {
	public abstract boolean connect(String... args);
	public abstract TaskList[] retrieveAllLists();
	public abstract TaskList getListByName(String listName);
	public abstract TaskList getTasksByDueDate(Date d, DateRetrievalMethod method);
	
	public TaskList getTasksByDueDate(Date d) {
		return getTasksByDueDate(d, DateRetrievalMethod.RETRIEVE_MATCH);
	}
	public TaskList getTasksByDueDate(String dateString) {
		return getTasksByDueDate(dateString, DateRetrievalMethod.RETRIEVE_MATCH);
	}
	public TaskList getTasksByDueDate(String dateString, DateRetrievalMethod method) {
		SimpleDateFormat sdf = new SimpleDateFormat(Task.DATE_FORMAT);
		try {
			return getTasksByDueDate(sdf.parse(dateString), method);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
