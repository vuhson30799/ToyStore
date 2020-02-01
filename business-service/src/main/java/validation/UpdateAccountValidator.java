package validation;

import model.Account;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.sql.Date;

@Component
public class UpdateAccountValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Account.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Account account = (Account) o;

        String name = account.getName();
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        if (!"".equals(name) && name.length() > 20) {
            errors.rejectValue("name", "name.length");
        }

        Date dob = account.getDob();
        ValidationUtils.rejectIfEmpty(errors, "dob", "dob.empty");
        if (dob != null && dob.getTime() > System.currentTimeMillis()) {
            errors.rejectValue("dob", "dob.invalid");
        }

        String gender = account.getGender();
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.empty");


        String address = account.getAddress();
        ValidationUtils.rejectIfEmpty(errors, "address", "address.empty");

        String phone = account.getPhone();
        ValidationUtils.rejectIfEmpty(errors, "phone", "phone.empty");
        if (!"".equals(phone)) {
            if (phone.length()>11 || phone.length()<10){
                errors.rejectValue("phone", "phone.length");
            }
            if (!phone.startsWith("0")){
                errors.rejectValue("phone", "phone.startsWith");
            }
            if (!phone.matches("(^$|[0-9]*$)")){
                errors.rejectValue("phone", "phone.matches");
            }
        }
    }
}
