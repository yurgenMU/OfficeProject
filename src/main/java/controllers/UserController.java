package controllers;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.SecurityService;
import service.UserService;
import validator.UserValidator;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    @Qualifier(value = "userService")
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("user") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.add(userForm);

        securityService.autoLogin(userForm.getLogin(), userForm.getConfirmPassword());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    private String listUsers(Model model) {
        List<User> usersData = userService.getAll();
        model.addAttribute("users", usersData);
        return "listUser";
    }

    @RequestMapping(value = "users/add", method = RequestMethod.POST)
    private String addNew(@ModelAttribute("user") User user, Model model) {
        user.setRole("ROLE_USER");
        userService.add(user);
        return "redirect:/";

    }

    @RequestMapping(value = "users/edit/{id}", method = RequestMethod.POST)
    private String updateExisting(@ModelAttribute("user") User userInfo) {
        userService.add(userInfo);
        return "redirect:/";
    }

    @RequestMapping(value = "users/add", method = RequestMethod.GET)
    private String getAddPage() {
        return "user";
    }

    @RequestMapping(value = "users/edit/{id}", method = RequestMethod.GET)
    private String getEditPage(Model model, @PathVariable("id") int userId) {
        User userInfo = (User) userService.get(userId);
        model.addAttribute("user", userInfo);
        return "update";
    }

    @RequestMapping(value = "users/remove/{id}", method = RequestMethod.GET)
    private String delete(@PathVariable("id") int userId){
        userService.remove(userId);
        return "redirect:/";
    }

}

