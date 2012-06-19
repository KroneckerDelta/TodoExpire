package de.mic.checkstyle.rule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * <pre>
 * Diese Klasse überprüft
 * a) einen String auf ein vorhandenes Datum.
 * um b) dann zu prüfen, ob das Datum im angegeben Format vorliegt und außerhalb
 * der deadline ist.
 * 
 * </pre>
 */
public class TodoWithNameAndDateIdentifier {

	public enum CheckResult {
		NO_DATE, DATE_OK, DATE_TOO_OLD
	}

	private Calendar deadline = null;
	private String dateformat = null;

	public TodoWithNameAndDateIdentifier(final Calendar deadline,
			final String dateformat) {
		this.deadline = deadline;
		this.dateformat = dateformat;
	}

	public CheckResult getOldTodo(final String normal) {

		final StringTokenizer st = new StringTokenizer(normal, " ");

		while (st.hasMoreElements()) {
			final String nextToken = st.nextToken();
			final Calendar calendar = getCalendar(nextToken);
			if (calendar != null) {
				if (this.deadline.after(calendar)) {
					return CheckResult.DATE_TOO_OLD;
				} else {
					return CheckResult.DATE_OK;
				}
			}
		}
		return CheckResult.NO_DATE;
	}

	protected Calendar getCalendar(final String maybeDate) {

		if (maybeDate == null) {
			return null;
		}
		final DateFormat df = new SimpleDateFormat(this.dateformat);
		df.setLenient(true);
		Date parse;
		try {
			parse = df.parse(maybeDate);
			final GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(parse);
			return gregorianCalendar;
		} catch (final ParseException e) {

			return null;
		}
	}

	@Override
	public String toString() {
		return "Deadline: " + this.deadline.getTime() + " Format: " +this.dateformat;
	}
}
