/**
 * 
 */
package com.saylorsolutions.stm.io;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.saylorsolutions.stm.core.Task;

/**
 * @author Doug Saylor
 *
 */
public class DateRetrievalMethodTest {
	Date before = null;
	Date after = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(Task.DATE_FORMAT);
		before = sdf.parse("16-DEC-1987");
		after  = sdf.parse("05-OCT-2014");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.saylorsolutions.stm.io.DateRetrievalMethod#matchesCriteria(java.util.Date, java.util.Date)}.
	 */
	@Test
	public final void testMatchesCriteria() {
		DateRetrievalMethod drm = null;
		
		drm = DateRetrievalMethod.RETRIEVE_AFTER;
		assertTrue(drm.matchesCriteria(before, after));
		assertFalse(drm.matchesCriteria(before, before));
		assertFalse(drm.matchesCriteria(after, before));
		
		drm = DateRetrievalMethod.RETRIEVE_AT_OR_AFTER;
		assertTrue(drm.matchesCriteria(before, after));
		assertTrue(drm.matchesCriteria(before, before));
		assertFalse(drm.matchesCriteria(after, before));
		
		drm = DateRetrievalMethod.RETRIEVE_MATCH;
		assertFalse(drm.matchesCriteria(before, after));
		assertTrue(drm.matchesCriteria(before, before));
		assertFalse(drm.matchesCriteria(after, before));
		
		drm = DateRetrievalMethod.RETRIEVE_AT_OR_BEFORE;
		assertFalse(drm.matchesCriteria(before, after));
		assertTrue(drm.matchesCriteria(before, before));
		assertTrue(drm.matchesCriteria(after, before));
		
		drm = DateRetrievalMethod.RETRIEVE_BEFORE;
		assertFalse(drm.matchesCriteria(before, after));
		assertFalse(drm.matchesCriteria(before, before));
		assertTrue(drm.matchesCriteria(after, before));
	}

}
