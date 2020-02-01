package validation;

import model.Seller;
import service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.sql.Date;

@Component
public class SellerValidator implements Validator {

    @Autowired
    private SellerService sellerService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Seller.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Seller seller = (Seller) target;

        ValidationUtils.rejectIfEmpty(errors, "account.username", "username.empty");
        ValidationUtils.rejectIfEmpty(errors, "account.gender", "gender.empty");
        ValidationUtils.rejectIfEmpty(errors, "account.phone", "phone.empty");
        ValidationUtils.rejectIfEmpty(errors, "account.password", "password.empty");
        ValidationUtils.rejectIfEmpty(errors, "account.confirmPassword", "password.confirm");
        ValidationUtils.rejectIfEmpty(errors, "account.email", "email.empty");
        ValidationUtils.rejectIfEmpty(errors, "account.name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "account.dob", "dob.empty");
        ValidationUtils.rejectIfEmpty(errors,"storeName","storeName.empty");
        ValidationUtils.rejectIfEmpty(errors,"certificateCode","certificateCode.empty");
        String username = seller.getAccount().getUsername();

        if (!"".equals(username)) {

            if (username.length() < 8 || username.length() > 20) {
                errors.rejectValue("account.username", "username.length");
            }

            if (!username.matches("^[a-zA-Z0-9]*$")) {
                errors.rejectValue("account.username", "username.match");
            }

            if (sellerService.findSellerByUsername(username) != null) {
                errors.rejectValue("account.username", "username.duplicate");
            }

        }

        String password = seller.getAccount().getPassword();
        String confirmPassword = seller.getAccount().getConfirmPassword();

        if (!"".equals(password) && password.length() < 5 || password.length() > 15) {
            errors.rejectValue("account.password", "password.length");
        }

        if (!"".equals(confirmPassword) && !password.equals(confirmPassword)) {
            errors.rejectValue("account.password", "password.duplicate");
        }

        String email = seller.getAccount().getEmail();

        if (!"".equals(email)) {

            if (!email.matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
                errors.rejectValue("account.email", "email.invalid");
            }

            if (sellerService.findSellerByEmail(email) != null) {
                errors.rejectValue("account.email", "email.duplicate");
            }

        }

        String name = seller.getAccount().getName();

        if (!"".equals(name) && name.length() > 20) {
            errors.rejectValue("account.name", "name.length");
        }

        Date dob = seller.getAccount().getDob();

        if (dob != null && dob.getTime() > System.currentTimeMillis()) {
            errors.rejectValue("account.dob", "dob.invalid");
        }

        String phone = seller.getAccount().getPhone();

        if (!"".equals(phone)) {

            if (phone.length()>11 || phone.length()<10){
                errors.rejectValue("account.phone", "phone.length");
            }

            if (!phone.startsWith("0")){
                errors.rejectValue("account.phone", "phone.startsWith");
            }

            if (!phone.matches("(^$|[0-9]*$)")){
                errors.rejectValue("account.phone", "phone.matches");
            }

        }

        if (seller.getAccount().getProvince() == null) {
            errors.rejectValue("account.province", "province.empty");
        }

        if (seller.getAccount().getDistrict() == null) {
            errors.rejectValue("account.district", "district.empty");
        }

        if (seller.getAccount().getVillage() == null) {
            errors.rejectValue("account.village", "village.empty");
        }

        String storeName = seller.getStoreName();

        if (!"".equals(storeName)) {

            if (storeName.length() < 8 || storeName.length() > 20) {
                errors.rejectValue("storeName", "storeName.length");
            }

            if (!username.matches("^[a-zA-Z0-9]*$")) {
                errors.rejectValue("storeName", "storeName.match");
            }

            if (sellerService.findSellerByUsername(username) != null) {
                errors.rejectValue("storeName", "storeName.duplicate");
            }
        }
        Long certificate = seller.getCertificateCode();

        if (sellerService.findSellerByCertificateCode(certificate) != null){
            errors.rejectValue("certificateCode","certificateCode.duplicate");
        }
    }
}
