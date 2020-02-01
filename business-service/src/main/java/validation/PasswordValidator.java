package validation;

import model.Password;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Password.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Password password = (Password) o;

        String oldPass = password.getOldPassword();
        String confirmPass = password.getConfirmPassword();
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "password.emptyconfirm");
        if (!"".equals(confirmPass)) {
            if (!oldPass.equals(confirmPass)) {
                errors.rejectValue("confirmPassword", "password.incorrect");
            }
            if (confirmPass.length() < 5 || confirmPass.length() > 15) {
                errors.rejectValue("confirmPassword", "password.length");
            }
        }

        String newPass = password.getNewPassword();
        String repeatPass = password.getRepeatPassword();
        ValidationUtils.rejectIfEmpty(errors, "newPassword", "password.emptynew");
        ValidationUtils.rejectIfEmpty(errors, "repeatPassword", "password.emptyrepeat");
        if (!"".equals(newPass)) {
            if (newPass.length() < 5 || newPass.length() > 15) {
                errors.rejectValue("newPassword", "password.length");
            }
            if (!newPass.equals(repeatPass)) {
                ValidationUtils.rejectIfEmpty(errors, "newPassword", "password.duplicate");
            }
        }
    }
}
