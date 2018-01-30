package controllers;//package controllers;

import model.Project;
import model.Room;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.ProjectService;
import service.UserService;

import java.util.List;
import java.util.Set;

@Controller
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;

    @Autowired
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    @Qualifier(value = "projectService")
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "OfficeProject/allProjects", method = RequestMethod.GET)
    private String listProjects(Model model) {
        List<Project> myProjects = projectService.getAll();

        model.addAttribute("myProjects", myProjects);
        return "listProject";
    }

    @RequestMapping(value = "OfficeProject/projects/add", method = RequestMethod.POST)
    private String addNew(@ModelAttribute("mproject") Project mproject,
                          Model model,
                          @RequestParam("selected") List<Integer> selectedUsers) {
        projectService.add(mproject);
//        String[] selectedStudentIds = model.request.getParameterValues("selected");
        return "projects";

    }

    @RequestMapping(value = "OfficeProject/projects",params = {"userId"},  method = RequestMethod.GET)
    private String getProjectsByUser(Model model, @RequestParam("userId") int id) {
        User user = userService.get(id);
        Set<Project> projects = user.getProjects();
        model.addAttribute("user", user);
        model.addAttribute("myProjects", projects);
        return "userProject";

    }

    @RequestMapping(value = "OfficeProject/projects/edit/{id}", method = RequestMethod.POST)
    private String updateExisting(@ModelAttribute("mproject") Project mproject) {
        projectService.add(mproject);
        return "projects";
    }

    @RequestMapping(value = "OfficeProject/projects/add", method = RequestMethod.GET)
    private String getAddPage(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute(users);
        return "newProject";
    }

    @RequestMapping(value = "OfficeProject/projects/edit/{id}", method = RequestMethod.GET)
    private String getEditPage(Model model, @PathVariable("id") int id) {
        Project project = (Project) projectService.get(id);
        model.addAttribute("project", project);
        return "update";
    }

    @RequestMapping(value = "OfficeProject/projects/remove/{id}", method = RequestMethod.GET)
    private String delete(@PathVariable("id") int Id) {
        projectService.remove(Id);
        return "redirect:/";
    }

    @RequestMapping(value = "OfficeProject/usersByProject/{id}", method = RequestMethod.GET)
    private String getUsers(Model model, @PathVariable("id") int id) {
        Project mproject = (Project) projectService.get(id);
        Set<User> users = mproject.getUsers();
        model.addAttribute("mproject", mproject);
        model.addAttribute("users", users);
        return "userProject";
    }

//    @RequestMapping(value = "usersByProject/{id}", method = RequestMethod.POST)
//    private String updateUsers(Model model, @PathVariable("id") int id) {
//        Project mproject = (Project) projectService.get(id);
//        Set<User> users = mproject.getUsers();
//        model.addAttribute("mproject", mproject);
//        model.addAttribute("users", users);
//        return "userProject";
//    }

}
