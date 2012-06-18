package de.mic.checkstyle.rule;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import de.mic.checkstyle.rule.TodoWithNameAndDateIdentifier.CheckResult;

/**
 * Diese Klasse testet die Auswahl des Checkresults
 * 
 * @author EXMICHA
 * 
 */
public class TestCheckresult {

	// Deadline ist der 17.06.2012 -> jedes andere Datum ist genauso gut oder
	// schlecht..
	private Calendar deadline = new GregorianCalendar(2012, 5, 17);
	private String dateformat = "dd.MM.yyyy";
	final TodoWithNameAndDateIdentifier identifier = new TodoWithNameAndDateIdentifier(
			deadline, dateformat);

	@Test
	public void shouldGetNotDate() {
		String noDate = "2012.15,10";
		assertEquals(CheckResult.NO_DATE, identifier.getOldTodo(noDate));

	}

	@Test
	public void shouldGetDateOK() {
		String dateOK = "05.09.2012";
		assertEquals(CheckResult.DATE_OK, identifier.getOldTodo(dateOK));

	}

	@Test
	public void shouldgetDateTooOld() {
		String oldDate = "01.01.2011";
		assertEquals(CheckResult.DATE_TOO_OLD, identifier.getOldTodo(oldDate));

	}

	@Test
	public void testDeadlineDateLimit() {
		String pre = "16.06.2012";
		String deadline = "17.06.2012";
		String post = "18.06.2012";
		Calendar myDeadline = new GregorianCalendar(2012, 5, 17);
		TodoWithNameAndDateIdentifier myTestIdentifiert = new TodoWithNameAndDateIdentifier(
				myDeadline, dateformat);

		assertEquals(CheckResult.DATE_TOO_OLD,
				myTestIdentifiert.getOldTodo(pre));
		assertEquals(CheckResult.DATE_OK,
				myTestIdentifiert.getOldTodo(deadline));
		assertEquals(CheckResult.DATE_OK, myTestIdentifiert.getOldTodo(post));
	}

}
