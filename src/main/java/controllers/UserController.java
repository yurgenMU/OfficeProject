package controllers;

import model.Room;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.SecurityService;
import service.UserService;
import validator.UpdateUserValidator;
import validator.CreateUserValidator;

import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CreateUserValidator createUserValidator;

    @Autowired
    private UpdateUserValidator updateUserValidator;

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
        createUserValidator.validate(userForm, bindingResult);
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
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        String name = modelUser.getUsername();
        User user = userService.getByName(name);
        model.addAttribute("user", user);
        model.addAttribute("room", user.getRoom());
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
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        model.addAttribute("users", usersData);
        if (roles.contains("ROLE_ADMIN")) {
            return "usersList";
        }
        return "immutableUsersList";

    }

    @RequestMapping(value = "OfficeProject/users/add", method = RequestMethod.POST)
    private String addNew(@ModelAttribute("user") User user, Model model) {
        user.setRole("ROLE_USER");
        userService.add(user);
        return "redirect:/";

    }

    @RequestMapping(value = "OfficeProject/users/edit", method = RequestMethod.POST)
    private String updateExisting(@ModelAttribute("user") User userInfo, BindingResult bindingResult,
                                  @ModelAttribute("room") Room room, Model model,@RequestParam("userId") int userId) {
        userInfo.setId(userId);
        updateUserValidator.validate(userInfo, bindingResult);

        if (bindingResult.hasErrors()) {
            userInfo.setPassword(null);
            userInfo.setConfirmPassword(null);
            return "editUser";
        }
        if (userService.isActual(userId)) {
            userService.edit(userInfo);
            return "redirect:/";
        }
        return "error";

    }


    @RequestMapping(value = "users/add", method = RequestMethod.GET)
    private String getAddPage() {
        return "user";
    }

    @RequestMapping(value = "OfficeProject/users/edit", method = RequestMethod.GET)
    private String getEditPage(Model model, @RequestParam("userId") int userId) {
        User userInfo = userService.get(userId);
        userInfo.setPassword(null);
        userInfo.setConfirmPassword(null);
        if (userService.isActual(userInfo.getId())) {
            model.addAttribute("user", userInfo);
            model.addAttribute("room", userInfo.getRoom());
            return "editUser";
        }
        return "error";
    }

    @RequestMapping(value = "OfficeProject/users/remove", method = RequestMethod.GET)
    private String delete(@RequestParam("userId") int userId) {
        userService.remove(userId);
        return "redirect:/";
    }


    @RequestMapping(value = "OfficeProject/projects",method = RequestMethod.GET)
    private String getProjectsByUser(@RequestParam("userId") int userId, Model model){
        User user = userService.get(userId);
        model.addAttribute("user", user);
        model.addAttribute("myProjects", user.getProjects());
        return "userProject";
    }





}

