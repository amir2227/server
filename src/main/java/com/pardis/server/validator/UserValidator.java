package com.pardis.server.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pardis.server.domain.User;

@Component
public class UserValidator implements Validator {
 
    // common-validator library.
    private EmailValidator emailValidator = EmailValidator.getInstance();
 
    @Autowired
    private UserDAO UserDAO;
 
    // The classes are supported by this validator.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == User.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        User UserForm = (User) target;
 
        // Check the fields of UserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.appUserForm.username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.appUserForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.appUserForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
 
        if (!this.emailValidator.isValid(UserForm.getEmail())) {
            // Invalid email.
            errors.rejectValue("email", "Pattern.UserForm.email");
        } else if (UserForm.getUserId() == null) {
            User dbUser = UserDAO.findUserByEmail(UserForm.getEmail());
            if (dbUser != null) {
                // Email has been used by another account.
                errors.rejectValue("email", "Duplicate.UserForm.email");
            }
        }
 
        if (!errors.hasFieldErrors("username")) {
            User dbUser = UserDAO.findUserByUserName(UserForm.getUsername());
            if (dbUser != null) {
                // Username is not available.
                errors.rejectValue("username", "Duplicate.UserForm.username");
            }
        }
 
    }
 
}