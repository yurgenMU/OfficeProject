package controllers;

import model.Room;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Set;

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

        return "redirect:/OfficeProject";
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

    @RequestMapping(value = {"/", "/OfficeProject"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils
                .authorityListToSet(authentication.getAuthorities());
        org.springframework.security.core.userdetails.User modelUser =
                (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        String name = modelUser.getUsername();
        User user = userService.findByLogin(name);
        model.addAttribute("user", user);
        if (roles.contains("ROLE_ADMIN")) {
            return "welcomeAdmin";
        }
        return "welcomeUser";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }


    @RequestMapping(value = "OfficeProject/showAll", method = RequestMethod.GET)
    private String listUsers(Model model) {
        List<User> usersData = userService.getAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils
                .authorityListToSet(authentication.getAuthorities());
        org.springframework.security.core.userdetails.User modelUser =
                (org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        model.addAttribute("users", usersData);
        if (roles.contains("ROLE_ADMIN")) {
            return "listUser";
        }
        return "immutableUsersList";

    }

    @RequestMapping(value = "OfficeProject/users/add", method = RequestMethod.POST)
    private String addNew(@ModelAttribute("user") User user, Model model) {
        user.setRole("ROLE_USER");
        userService.add(user);
        return "redirect:/";

    }

    @RequestMapping(value = "OfficeProject/users/edit/{id}", method = RequestMethod.POST)
    private String updateExisting(@ModelAttribute("user") User userInfo, @ModelAttribute("room") Room room) {
        User proxyUser = userService.get(userInfo.getId());
        if(userInfo.getFirstName() != null)
            proxyUser.setFirstName(userInfo.getFirstName());
        if(userInfo.getLastName() != null)
            proxyUser.setLastName(userInfo.getLastName());
        if(userInfo.getRoom() != null)
            proxyUser.setRoom(room);
        userService.edit(proxyUser);
        return "redirect:/";
    }

    @RequestMapping(value = "users/add", method = RequestMethod.GET)
    private String getAddPage() {
        return "user";
    }

    @RequestMapping(value = "OfficeProject/users/edit/{id}", method = RequestMethod.GET)
    private String getEditPage(Model model, @PathVariable("id") int userId) {
        User userInfo = userService.get(userId);
        model.addAttribute("user", userInfo);
        model.addAttribute("room", userInfo.getRoom());
        return "editUser";
    }

    @RequestMapping(value = "OfficeProject/users/remove/{id}", method = RequestMethod.GET)
    private String delete(@PathVariable("id") int userId){
        userService.remove(userId);
        return "redirect:/";
    }


}

