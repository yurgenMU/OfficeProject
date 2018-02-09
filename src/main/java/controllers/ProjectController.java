package controllers;//package controllers;

import model.Project;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.ProjectService;
import service.UserService;
import validator.ProjectValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;

    @Autowired
    private ProjectValidator projectValidator;

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

    @RequestMapping(value = "OfficeProject/projects/all", method = RequestMethod.GET)
    private String listProjects(Model model) {
        List<Project> myProjects = projectService.getAll();
        model.addAttribute("myProjects", myProjects);
        return "listProject";
    }

    @RequestMapping(value = "OfficeProject/projects/add", method = RequestMethod.GET)
    private String getAddPage(Model model) {
        projectService.createAddPageModel(model);
        return "newProject";
    }


    @RequestMapping(value = "OfficeProject/projects/add", method = RequestMethod.POST)
    private String addNew(
            @ModelAttribute("mproject") Project project,
            BindingResult bindingResult,
            @RequestParam(value = "userId", required = false) ArrayList<Integer> selectedUsers,
            Model model) {
        projectValidator.validate(project, bindingResult);
        if (bindingResult.hasErrors()) {
            List<User> users = userService.getAll();
            model.addAttribute("mproject", new Project());
            model.addAttribute("users", users);
            return "newProject";
        }
        if (selectedUsers != null) {
            projectService.setupNewProjectParams(project, selectedUsers);
        } else {
            projectService.add(project);
        }
        return "redirect: /OfficeProject/projects/all";

    }

    @RequestMapping(value = "OfficeProject/projects", params = {"userId"}, method = RequestMethod.GET)
    private String getProjectsByUser(Model model, @RequestParam("userId") int id) {
        projectService.createByUserModel(id, model);
        return "userProject";

    }

    @RequestMapping(value = "OfficeProject/projects/edit", method = RequestMethod.POST)
    private String updateExisting(@ModelAttribute("mproject") Project mproject) {
        projectService.add(mproject);
        return "projects";
    }


    @RequestMapping(value = "OfficeProject/projects/edit", method = RequestMethod.GET)
    private String getEditPage(Model model, @RequestParam("projectId") int id) {
        projectService.createEditPageModel(id, model);
        return "editProject";
    }

    @RequestMapping(value = "OfficeProject/projects/removeFrom", method = RequestMethod.POST)
    private String deleteUsersFromProject(@RequestParam("projectId") int id,
                                          Model model,
                                          @RequestParam(value = "auserId", required = false) ArrayList<Integer> selectedUsers
    ) {
        if (selectedUsers != null) {
            projectService.deleteUsersOperation(id, selectedUsers);
        }
        getEditPage(model, id);
        return "redirect: /OfficeProject/projects/edit?projectId=" + id;
    }


    @RequestMapping(value = "OfficeProject/projects/addInto", method = RequestMethod.POST)
    private String addUsersIntoProject(@RequestParam("projectId") int id,
                                       Model model,
                                       @RequestParam(value = "nuserId", required = false) ArrayList<Integer> selectedUsers
    ) {
        if (selectedUsers != null) {
            projectService.addToExisting(id, selectedUsers);
        }
        getEditPage(model, id);
        return "redirect: /OfficeProject/projects/edit?projectId=" + id;
    }


    @RequestMapping(value = "OfficeProject/projects/changeName", method = RequestMethod.POST)
    private String changeProjectName(@RequestParam("projectId") int id,
                                     @ModelAttribute("mproject") Project project,
                                     BindingResult bindingResult,
                                     Model model
    ) {
        projectValidator.validate(project, bindingResult);
        if (bindingResult.hasErrors()) {
            projectService.createEditPageModel(id, model);
            return "redirect: /OfficeProject/projects/edit?projectId=" + id;
        }
        projectService.changeName(id, project.getName());
        getEditPage(model, id);
        return "redirect: /OfficeProject/projects/edit?projectId=" + id;

    }


    @RequestMapping(value = "OfficeProject/usersByProject/{id}", method = RequestMethod.GET)
    private String getUsers(Model model, @PathVariable("id") int id) {
        projectService.createByProjectModel(id, model);
        return "userProject";
    }


    @RequestMapping(value = "OfficeProject/projects/remove", method = RequestMethod.GET)
    private String deleteProject(@RequestParam("projectId") int Id) {
        projectService.remove(Id);
        return "redirect:/";
    }


}
