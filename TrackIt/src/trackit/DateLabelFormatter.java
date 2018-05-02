package trackit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * BLL Layer: Handles all aspects of formatting a date.
 *
 * @author Steven
 */
public class DateLabelFormatter
        extends AbstractFormatter {

    private final SimpleDateFormat dateFormatter = Utilities.getDateFormatter();

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}
