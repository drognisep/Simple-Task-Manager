/**
 * 
 */
package com.saylorsolutions.stm.util.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.saylorsolutions.stm.core.Task;
import com.saylorsolutions.stm.core.TaskList;
import com.saylorsolutions.stm.util.TaskUtil;

/**
 * @author Doug Saylor
 *
 */
public class TaskUtilTest {
	TaskList tl = new TaskList("Testing");
	Task t1 = null, t2 = null, t3 = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		t1 = new Task("Task1", "Task 1", true, false, new Date());
		t2 = new Task("Task2", "Task 2", false, true, null);
		t3 = new Task("Task3", "Task 3", false, false, new Date());
		tl.addAll(Arrays.asList(new Task[] { t1, t2, t3}));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		tl.clear();
	}
	
	@Test
	public final void testInit() {
		assertEquals("Unexpected size of TaskList", 3, tl.size());
		assertEquals(t1, tl.get(0));
		assertEquals(t2, tl.get(1));
		assertEquals(t3, tl.get(2));
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.util.TaskUtil#printList(com.saylorsolutions.stm.core.TaskList)}.
	 */
	@Test
	public final void testPrintList() {
		TaskUtil.printList(tl);
	}

}
