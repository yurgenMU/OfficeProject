package controllers;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.UserService;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;


    @Autowired
    @Qualifier(value = "userService")
    private void setUserService(UserService userService) {
        this.userService = userService;
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

