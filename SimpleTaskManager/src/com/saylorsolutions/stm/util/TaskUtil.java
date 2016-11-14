package com.saylorsolutions.stm.util;

import com.saylorsolutions.stm.core.Task;
import com.saylorsolutions.stm.core.TaskList;

/**
 * Defines some interim utility methods for debugging purposes.
 * @author Doug Saylor
 *
 */
public class TaskUtil {
	public static void printList(TaskList list) {
		System.out.println(System.lineSeparator() + list.getListName());
		for(Task t : list) {
			System.out.println("\t" + t.toString());
		}
	}
}
