package validator;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UpdateUserValidator implements Validator {

    @Autowired
    private UserService userService;


    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        User proxyUser = userService.getByName(user.getLogin());
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "Required");
        if (user.getLogin().length() < 8 || user.getLogin().length() > 32) {
            errors.rejectValue("login", "Size.user.login");
        }
        if (user.getFirstName().trim().length() == 0){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Required");
        }
        if (user.getLastName().trim().length() == 0){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Required");
        }
        if ((proxyUser != null) &&
                (proxyUser.getId() != user.getId())) {
            errors.rejectValue("login", "Duplicate.user.login");
        }

        if((user.getPassword().equals("")) && (user.getConfirmPassword().equals(""))){
            user.setPassword(proxyUser.getPassword());
        } else{
            if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
                errors.rejectValue("password", "Size.user.password");
            }
            if (!user.getConfirmPassword().equals(user.getPassword())) {
                errors.rejectValue("confirmPassword", "Different.user.password");
            }
        }
        if(!validateEmail(user.getEmail())){
            errors.rejectValue("email", "Email.invalid");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }


    public boolean validateEmail(String emailStr) {
        Pattern validEmailAddressRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailAddressRegex .matcher(emailStr);
        return matcher.find();
    }
}

