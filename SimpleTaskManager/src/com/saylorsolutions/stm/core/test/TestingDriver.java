/**
 * 
 */
package com.saylorsolutions.stm.core.test;

import java.util.Arrays;
import java.util.Date;

import com.saylorsolutions.stm.core.Task;
import com.saylorsolutions.stm.core.TaskList;
import com.saylorsolutions.stm.util.TaskUtil;

/**
 * A driver class to demo current functionality.
 * @author Doug Saylor
 *
 */
public class TestingDriver {

	public static void main(String[] args) {
		TaskList tl1 = new TaskList("Todo today");
		TaskList tl2 = new TaskList("Art stuff");
		
		tl1.addAll(Arrays.asList(new Task[] {
				new Task("Pick apples", "Must find orchard", true, true, new Date()),
				new Task("Bring apples back", "After picking", true, false, new Date())
		}));
		tl1.sort();
		
		tl2.addAll(Arrays.asList(new Task[] {
				new Task("Get paper", "For drawing", false, false, null),
				new Task("Draw picture", "A nice one", false, false, new Date()),
				new Task("Give it away", "Share your art", false, false, null),
				new Task("Rest", "Time to relax!", false, false, null)
		}));
		tl2.get(2).setPriority(true);
		tl2.sort();
		
		TaskList tl3 = new TaskList("List C",
				new Task()
		);
		
		TaskUtil.printList(tl1);
		TaskUtil.printList(tl2);
		TaskUtil.printList(tl3);
	}

}
