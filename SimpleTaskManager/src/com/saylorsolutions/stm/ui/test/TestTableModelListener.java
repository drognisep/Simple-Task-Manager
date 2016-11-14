package com.saylorsolutions.stm.ui.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class TestTableModelListener implements TableModelListener {
	private Logger logger = Logger.getLogger("TestTableModelListener");
	private boolean eventReceived = false;
	private TableModelEvent lastEvent = null;

	@Override
	public void tableChanged(TableModelEvent newEvent) {
		String message = "Type: " + newEvent.getType() +
				", row: " + newEvent.getFirstRow() +
				", column: " + newEvent.getColumn();
		eventReceived = true;
		this.lastEvent = newEvent;
		logger.log(Level.INFO, message);
	}
	
	public boolean wasEventReceived() {
		boolean r = eventReceived;
		eventReceived = false;
		return r;
	}

	public TableModelEvent getLastEvent() {
		return lastEvent;
	}
}