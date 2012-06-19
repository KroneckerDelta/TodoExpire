package de.mic.checkstyle.rule;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import de.mic.checkstyle.rule.TodoWithNameAndDateIdentifier;

/**
 * Diese Klasse testet die Identifizierung des Datums
 * @author EXMICHA
 *
 */
public class TestCalendarIdentifier {

    private String dateformat = "dd.MM.yyyy";
	
    /**
     * Testen der Grenzfälle, Jahresanfang und Jahresende
     */
    
    /**
     * Jahresende
     */
    @Test
	public void shouldGetSylvester()
	{
		final String testDay = "31";
		final String testMonth = "12";
		final String testYear = "2012";
		
		
		checkDateIdentifier(testDay, testMonth, testYear);
	}


	/**
	 * Jahresmitte
	 */
	@Test
	public void shouldGetFirstMay()
	{
		
		final String testDay = "01";
		final String testMonth = "05";
		final String testYear = "2005";
		
		
		checkDateIdentifier(testDay, testMonth, testYear);
				}
	/**
	 * Jahresanfang
	 */
	@Test
	public void shouldGetNewYear()
	{
		final String testDay = "01";
		final String testMonth = "01";
		final String testYear = "1999";
		
		checkDateIdentifier(testDay, testMonth, testYear);
		
	}
	
	private void checkDateIdentifier(final String testDay,
			final String testMonth, final String testYear) {
		final String testDate = new String(testDay+"."+testMonth+"."+testYear);
		
		TodoWithNameAndDateIdentifier identifier = new TodoWithNameAndDateIdentifier(null, dateformat);
		Calendar calendar = identifier.getCalendar(testDate);
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		
		assertEquals(Integer.valueOf(testDay).intValue(), day);
		assertEquals(Integer.valueOf(testMonth).intValue()-1,month); // Monat beginnt bei 0!
		assertEquals(Integer.valueOf(testYear).intValue(), year);
	}
}
