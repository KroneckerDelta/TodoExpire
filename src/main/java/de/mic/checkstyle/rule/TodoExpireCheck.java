package de.mic.checkstyle.rule;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck;

/**
 * Diese Klasse repräsentiert ein eigenes Checkstyle zum Finden von veralteten Todos.
 * Die Klasse sucht im Quellcode nach dem Regex 'TODO' und überprüft die dahinterliegenden
 * Zeichen auf ein Datum. Das Datumsformat kann über angegeben werden, wenn kein Format
 * angegeben ist, wird auf 'dd.mm.yyyy' geprüft.
 * 
 * Ab wann ein Datum veraltet ist wird über 'days' gesteuert. Wenn 'days' nicht konfiguiert ist
 * werden Datumwerte mit mehr als 180 zurück als alt identifiziet.
 * 
 * 
 * <pre>
 * &lt;module name="de.mic.checkstyle.rule.TodoExpireCheck"&gt;
 * &lt;property name="days" value="180"/&gt;
 * &lt;property name="dateformat" value="dd.mm.yyyy"/&gt;
 * &lt;/module&gt;
 * </pre>
 * 
 */
public class TodoExpireCheck extends TodoCommentCheck {

    private int days = 180;
    private String dateformat = "dd.mm.yyyy";

    public TodoExpireCheck() {
        super();
        // Regex to find
        setFormat("TODO");
    }

    @Override
    public void beginTree(final DetailAST aRootAST) {
        final FileContents contents = getFileContents();
        checkCppComments(contents);
        checkBadComments(contents);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[0];
    }

    /**
     * überprüft im Code die Kommentare die im C++ Style vorliegen.
     * 
     * @param aContents
     */
    private void checkCppComments(final FileContents aContents) {
        final TodoWithNameAndDateIdentifier todoIdentifier = initCalendar();
        final ImmutableMap<Integer, TextBlock> comments = aContents.getCppComments();
        final ImmutableSet<Entry<Integer, TextBlock>> entrySet = comments.entrySet();
        for (final Entry<Integer, TextBlock> entry : entrySet) {
            final String cmt = entry.getValue().getText()[0];
            checkOldComment(todoIdentifier, cmt, entry.getKey().intValue());
        }
    }

    private void checkOldComment(final TodoWithNameAndDateIdentifier todoIdentifier, final String cmt,
            final int lineNummer) {
        if (getRegexp().matcher(cmt).find()) {
            String oldTodo = todoIdentifier.getOldTodo(cmt);
            if (oldTodo != null) {
                // Sternchen für Javadoc Kommentare entfernen, schönere Ausgabe!
                oldTodo = oldTodo.replace('*', ' ');
                log(lineNummer, "found old" + oldTodo, new Object[] {getFormat()});
            }
        }
    }

    /**
     * überprüft im Code die Kommentar, die als Javadoc vorliegen
     * 
     * @param aContents
     */
    private void checkBadComments(final FileContents aContents) {

        final TodoWithNameAndDateIdentifier todoIdentifier = initCalendar();
        final ImmutableMap<Integer, List<TextBlock>> cComments = aContents.getCComments();
        Entry<Integer, List<TextBlock>> entry;
        for (final UnmodifiableIterator<Entry<Integer, List<TextBlock>>> iterator = cComments.entrySet().iterator(); iterator
                .hasNext();) {
            entry = iterator.next();

            for (final TextBlock line : entry.getValue()) {
                final String[] cmt = line.getText();
                for (int i = 0; i < cmt.length; ++i) {
                    final int lineNumber = entry.getKey().intValue() + i;
                    checkOldComment(todoIdentifier, cmt[i], lineNumber);
                }
            }
        }
    }

    /**
     * Erzeugt einen Kommentaridentifier mit dem einem Kalendar mit Deadline
     * und dem zu prüfendem Datumsformat
     * 
     * @return
     */
    private TodoWithNameAndDateIdentifier initCalendar() {
        final Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, -this.days);
        return new TodoWithNameAndDateIdentifier(calendar, this.dateformat);
    }

    public void setDays(final int days) {
        this.days = days;
    }

    public int getDays() {
        return this.days;
    }

    public String getDateformat() {
        return this.dateformat;
    }

    public void setDateformat(final String dateformat) {
        this.dateformat = dateformat;
    }
}
