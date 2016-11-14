package com.saylorsolutions.stm.ui.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.saylorsolutions.stm.core.Task;
import com.saylorsolutions.stm.core.TaskList;
import com.saylorsolutions.stm.ui.TableModelWrapper;

public class TestTableView {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestTableView window = new TestTableView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestTableView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		SimpleDateFormat sdf = new SimpleDateFormat(Task.DATE_FORMAT);
		TaskList tl = new TaskList("Test List");
		Task t1 = new Task("Task1", "Task 1", true, true, null);
		Task t2 = new Task("Task2", "Task 2", false, false, null);
		Task t3;
		try {
			t3 = new Task("Task3", "Task 3", true, false, sdf.parse("05-OCT-2014"));
		} catch (ParseException e) {
			e.printStackTrace();
			t3 = new Task("Task3", "Task 3", true, false, new Date());
		}
		tl.add(t1);
		tl.add(t3);
		tl.add(t2);
		tl.sort();
		TableModelWrapper tmw = new TableModelWrapper(tl);
		tmw.addTableModelListener(new TestTableModelListener());
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(tmw);
		scrollPane.setViewportView(table);
	}

}
