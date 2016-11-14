/**
 * 
 */
package com.saylorsolutions.stm.core.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.saylorsolutions.stm.core.Task;
import com.saylorsolutions.stm.core.TaskList;

/**
 * @author Doug Saylor
 *
 */
public class TaskListTest {

	private SimpleDateFormat sdf = new SimpleDateFormat(Task.DATE_FORMAT);
	private Task t1;
	private Task t2;
	private Task t3;
	private TaskList tl;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		t1 = new Task("Task1", "", true, false, sdf.parse("05-OCT-2014"));
		t2 = new Task("Task2", "", true, false, sdf.parse("05-JAN-1989"));
		t3 = new Task("Task3", "", false, true, sdf.parse("16-DEC-1987"));
		tl = new TaskList("TestList", t1, t2, t3);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.core.TaskList#TaskList(java.lang.String, com.saylorsolutions.stm.core.Task[])}.
	 */
	@Test
	public final void testTaskList() {
		assertEquals(3, tl.size());
		TaskList tl = new TaskList("Other");
		assertTrue(tl.isEmpty());
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.core.TaskList#getListName()}.
	 */
	@Test
	public final void testGetListName() {
		assertEquals("TestList", tl.getListName());
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.core.TaskList#setListName(java.lang.String)}.
	 */
	@Test
	public final void testSetListName() {
		String name = tl.getListName();
		for(Task t : tl) {
			assertEquals(name, t.getListName());
		}
		name = "New Name";
		tl.setListName(name);
		for(Task t : tl) {
			assertEquals(name, t.getListName());
		}
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.core.TaskList#add(com.saylorsolutions.stm.core.Task)}.
	 */
	@Test
	public final void testAddTask() {
		Task t = null;
		try {
			t = new Task("TaskA", "", false, true, sdf.parse("01-JAN-2016"));
		} catch (ParseException e) {
			fail("Invalid date format");
		}
		
		try {
			tl.add(null);
		} catch (NullPointerException npx) {
			fail("Unhandled null");
		}
		
		tl.add(t);
		assertEquals(4, tl.size());
		assertEquals(t, tl.get(3));
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.core.TaskList#add(int, com.saylorsolutions.stm.core.Task)}.
	 */
	@Test
	public final void testAddIntTask() {
		Task t = null;
		try {
			t = new Task("TaskA", "", false, true, sdf.parse("01-JAN-2016"));
		} catch (ParseException e) {
			fail("Invalid date format");
		}
		
		try {
			tl.add(2, null);
		} catch (NullPointerException npx) {
			fail("Unhandled null");
		}
		
		tl.add(2, t);
		assertEquals(4, tl.size());
		assertEquals(t, tl.get(2));
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.core.TaskList#addAll(java.util.Collection)}.
	 */
	@Test
	public final void testAddAllCollectionOfQextendsTask() {
		List<Task> list = null;
		Task newTask1 = null, newTask2 = null;
		try {
			newTask1 = new Task("TaskA", "", true, true, sdf.parse("01-JAN-2017"));
			newTask2 = new Task("TaskB", "2nd", false, true, sdf.parse("02-JAN-2017"));
			list = Arrays.asList(new Task[] {
					newTask1,
					newTask2
			});
		} catch (ParseException px) {
			fail("Invalid date format");
		}
		
		tl.addAll(list);
		assertEquals(5, tl.size());
		assertEquals(newTask1, tl.get(3));
		assertEquals(newTask2, tl.get(4));
		
		tl.addAll(Arrays.asList(new Task[] {
				null, null, null
		}));
		assertEquals(5, tl.size());
		assertEquals(newTask1, tl.get(3));
		assertEquals(newTask2, tl.get(4));
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.core.TaskList#addAll(int, java.util.Collection)}.
	 */
	@Test
	public final void testAddAllIntCollectionOfQextendsTask() {
		List<Task> list = null;
		Task newTask1 = null, newTask2 = null;
		try {
			newTask1 = new Task("TaskA", "", true, true, sdf.parse("01-JAN-2017"));
			newTask2 = new Task("TaskB", "2nd", false, true, sdf.parse("02-JAN-2017"));
			list = Arrays.asList( new Task[] {
					newTask1,
					newTask2
			});
		} catch (ParseException px) {
			fail("Invalid date format");
		}
		
		tl.addAll(1, list);
		assertEquals(5, tl.size());
		assertEquals(newTask1, tl.get(1));
		assertEquals(newTask2, tl.get(2));
		
		tl.addAll(Arrays.asList(new Task[] {
				null, null, null
		}));
		assertEquals(5, tl.size());
		assertEquals(newTask1, tl.get(1));
		assertEquals(newTask2, tl.get(2));
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.core.TaskList#sort()}.
	 */
	@Test
	public final void testSort() {
		tl.sort();
		// Due date should trump everything in comparison if it is before now
		assertEquals(t3, tl.get(0));
		assertEquals(t2, tl.get(1));
		assertEquals(t1, tl.get(2));
		
		t1.setDueDate(null);
		t2.setDueDate(null);
		t3.setDueDate(null);
		tl.sort();
		assertEquals(t1, tl.get(0));
		assertEquals(t2, tl.get(1));
		assertEquals(t3, tl.get(2));
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.core.TaskList#toArray()}.
	 */
	@Test
	public final void testToArray() {
		Object[] ary = tl.toArray();
		assertEquals(t1, ary[0]);
		assertEquals(t2, ary[1]);
		assertEquals(t3, ary[2]);
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.core.TaskList#contains(java.lang.String)}.
	 */
	@Test
	public final void testContainsString() {
		assertTrue(tl.contains("Task1"));
		assertTrue(tl.contains("Task2"));
		assertTrue(tl.contains("Task3"));
		assertFalse(tl.contains("alsdkfja;lskdjfa;lskdj"));
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.core.TaskList#get(java.lang.String)}.
	 */
	@Test
	public final void testGetString() {
		assertEquals(t1, tl.get("Task1"));
		assertEquals(t3, tl.get("Task3"));
		assertNull(tl.get("aslkdfja;sldkjf"));
	}

}
