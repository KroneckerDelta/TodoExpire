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

    private Calendar deadline = null;
    private String dateformat = null;

    public TodoWithNameAndDateIdentifier(final Calendar deadline, final String dateformat) {
        this.deadline = deadline;
        this.dateformat = dateformat;
    }

    public String getOldTodo(final String normal) {

        final StringTokenizer st = new StringTokenizer(normal, " ");

        while (st.hasMoreElements()) {
            final String nextToken = st.nextToken();
            final Calendar calendar = getCalendar(nextToken);
            if (calendar != null && this.deadline.after(calendar)) {
                return normal;
            }
        }
        return null;
    }

    private Calendar getCalendar(final String maybeDate) {

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
            // Monat beginnt mit 0, daher wird "falsch" geparst.
            gregorianCalendar.add(Calendar.MONTH, -1);
            return gregorianCalendar;
        } catch (final ParseException e) {

            return null;
        }
    }
}
