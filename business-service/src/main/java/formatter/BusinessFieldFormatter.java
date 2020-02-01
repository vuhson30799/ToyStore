package formatter;

import model.BusinessField;
import service.BusinessFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class BusinessFieldFormatter implements Formatter<BusinessField> {

    @Autowired
    private BusinessFieldService businessFieldService;
    @Override
    public BusinessField parse(String text, Locale locale) throws ParseException {
        return businessFieldService.findById(Long.parseLong(text));
    }

    @Override
    public String print(BusinessField object, Locale locale) {
        return null;
    }
}
