package formatter;

import org.springframework.format.Formatter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateSqlFormatter implements Formatter<Date> {

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Date parse(String s, Locale locale) throws ParseException {
        java.util.Date date = format.parse(s);
        return new Date(date.getTime());
    }

    @Override
    public String print(Date date, Locale locale) {
        return format.format(date);
    }
}
