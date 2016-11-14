/**
 * 
 */
package com.saylorsolutions.stm.ui.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.saylorsolutions.stm.core.Task;
import com.saylorsolutions.stm.core.TaskList;
import com.saylorsolutions.stm.ui.TableModelWrapper;

/**
 * @author Doug Saylor
 *
 */
public class TableModelWrapperTest {
	TaskList tl = null;
	Task t1 = null;
	Task t2 = null;
	Task t3 = null;
	TableModelWrapper tw = null;
	TestTableModelListener tml;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		tl = new TaskList("Testing");
		t1 = new Task("Task1", "Task 1", true, false, null);
		t2 = new Task("Task2", "Task 2", true, true, null);
		t3 = new Task("Task3", "Task 3", false, true, null);
		
		tl.addAll(Arrays.asList(new Task[] {
				t1, t2, t3
		}));
		tw = new TableModelWrapper(tl);
		tml = new TestTableModelListener();
		tw.addTableModelListener(tml);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public final void testInit() {
		assertEquals(3, tl.size());
		assertEquals(t1, tl.get(0));
		assertEquals(t2, tl.get(1));
		assertEquals(t3, tl.get(2));
		assertNull(tml.getLastEvent());
		assertFalse(tml.wasEventReceived());
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.ui.TableModelWrapper#getRowCount()}.
	 */
	@Test
	public final void testGetRowCount() {
		assertEquals(3, tw.getRowCount());
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.ui.TableModelWrapper#getColumnCount()}.
	 */
	@Test
	public final void testGetColumnCount() {
		assertEquals(5, tw.getColumnCount());
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.ui.TableModelWrapper#getColumnName(int)}.
	 */
	@Test
	public final void testGetColumnName() {
		String[] names = new String[] {
			"Name", "Description", "Priority", "Pressing", "Due Date"
		};
		for(int i = 0; i < tw.getColumnCount(); i++) {
			assertEquals("Column " + i + " should be " + names[i], names[i], tw.getColumnName(i));
		}
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.ui.TableModelWrapper#getColumnClass(int)}.
	 */
	@Test
	public final void testGetColumnClass() {
		Class<?>[] classes = new Class<?>[] {
			String.class, String.class, Boolean.class, Boolean.class, String.class
		};
		for(int i = 0; i < tw.getColumnCount(); i++) {
			assertEquals(classes[i], tw.getColumnClass(i));
		}
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.ui.TableModelWrapper#isCellEditable(int, int)}.
	 */
	@Test
	public final void testIsCellEditable() {
		assertEquals(3, tw.getRowCount());
		assertEquals(5, tw.getColumnCount());
		for(int i = 0; i < tw.getRowCount(); i++) {
			for(int j = 0; j < tw.getColumnCount(); j++) {
				assertEquals("All cells should be editable", j != 0, tw.isCellEditable(i, j));
			}
		}
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.ui.TableModelWrapper#getValueAt(int, int)}.
	 */
	@Test
	public final void testGetValueAt() {
		assertEquals("Task1", tw.getValueAt(0, 0));
		assertEquals("Task 1", tw.getValueAt(0, 1));
		assertEquals(true, tw.getValueAt(0, 2));
		assertEquals(false, tw.getValueAt(0, 3));
		assertEquals(Task.NO_DUE_DATE, tw.getValueAt(0, 4));
		
		assertEquals("Task2", tw.getValueAt(1, 0));
		assertEquals("Task 2", tw.getValueAt(1, 1));
		assertEquals(true, tw.getValueAt(1, 2));
		assertEquals(true, tw.getValueAt(1, 3));
		assertEquals(Task.NO_DUE_DATE, tw.getValueAt(1, 4));
		
		assertEquals("Task3", tw.getValueAt(2, 0));
		assertEquals("Task 3", tw.getValueAt(2, 1));
		assertEquals(false, tw.getValueAt(2, 2));
		assertEquals(true, tw.getValueAt(2, 3));
		assertEquals(Task.NO_DUE_DATE, tw.getValueAt(2, 4));
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.ui.TableModelWrapper#setValueAt(java.lang.Object, int, int)}.
	 */
	@Test
	public final void testSetValueAt() {
		SimpleDateFormat sdf = new SimpleDateFormat(Task.DATE_FORMAT);
		String newName = "TaskA";
		String newDesc = "Task A";
		boolean newPrio = false, newPress = true;
		Date newDate = new Date();
		String sNewDate = sdf.format(newDate);
		
		assertEquals("Task1", t1.getName());
		tw.setValueAt(newName, 0, 0);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newName, tw.getValueAt(0, 0));
		assertEquals(newName, t1.getName());
		
		assertEquals("Task 1", t1.getDesc());
		tw.setValueAt(newDesc, 0, 1);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newDesc, tw.getValueAt(0, 1));
		assertEquals(newDesc, t1.getDesc());
		
		assertEquals(true, t1.isPriority());
		tw.setValueAt(newPrio, 0, 2);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newPrio, tw.getValueAt(0, 2));
		assertEquals(newPrio, t1.isPriority());
		
		assertEquals(false, t1.isPressing());
		tw.setValueAt(newPress, 0, 3);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newPress, tw.getValueAt(0, 3));
		assertEquals(newPress, t1.isPressing());
		
		assertNull(t1.getDueDate());
		assertEquals(Task.NO_DUE_DATE, tw.getValueAt(0, 4));
		tw.setValueAt(sNewDate, 0, 4);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(sNewDate, tw.getValueAt(0, 4));
		assertEquals(sNewDate, sdf.format(t1.getDueDate()));
		
		
		assertEquals("Task2", t2.getName());
		tw.setValueAt(newName, 1, 0);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newName, tw.getValueAt(1, 0));
		assertEquals(newName, t2.getName());
		
		assertEquals("Task 2", t2.getDesc());
		tw.setValueAt(newDesc, 1, 1);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newDesc, tw.getValueAt(1, 1));
		assertEquals(newDesc, t2.getDesc());
		
		assertEquals(true, t2.isPriority());
		tw.setValueAt(newPrio, 1, 2);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newPrio, tw.getValueAt(1, 2));
		assertEquals(newPrio, t2.isPriority());
		
		assertEquals(true, t2.isPressing());
		tw.setValueAt(newPress, 1, 3);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newPress, tw.getValueAt(1, 3));
		assertEquals(newPress, t2.isPressing());
		
		assertNull(t2.getDueDate());
		assertEquals(Task.NO_DUE_DATE, tw.getValueAt(1, 4));
		tw.setValueAt(sNewDate, 1, 4);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(sNewDate, tw.getValueAt(1, 4));
		assertEquals(sNewDate, sdf.format(t2.getDueDate()));
		
		
		assertEquals("Task3", t3.getName());
		tw.setValueAt(newName, 2, 0);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newName, tw.getValueAt(2, 0));
		assertEquals(newName, t3.getName());
		
		assertEquals("Task 3", t3.getDesc());
		tw.setValueAt(newDesc, 2, 1);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newDesc, tw.getValueAt(2, 1));
		assertEquals(newDesc, t3.getDesc());
		
		assertEquals(false, t3.isPriority());
		tw.setValueAt(newPrio, 2, 2);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newPrio, tw.getValueAt(2, 2));
		assertEquals(newPrio, t3.isPriority());
		
		assertEquals(true, t3.isPressing());
		tw.setValueAt(newPress, 2, 3);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(newPress, tw.getValueAt(2, 3));
		assertEquals(newPress, t3.isPressing());
		
		assertNull(t3.getDueDate());
		assertEquals(Task.NO_DUE_DATE, tw.getValueAt(2, 4));
		tw.setValueAt(sNewDate, 2, 4);
		assertTrue(tml.wasEventReceived());
		assertFalse(tml.wasEventReceived());
		assertEquals(sNewDate, tw.getValueAt(2, 4));
		assertEquals(sNewDate, sdf.format(t3.getDueDate()));
	}
}
