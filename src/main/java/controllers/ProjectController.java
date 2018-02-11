package controllers;

import model.Project;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.ProjectService;
import service.UserService;
import validator.ProjectValidator;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

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


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "OfficeProject/projects/all", method = RequestMethod.GET)
    public String listProjects(Model model) {
        List<Project> myProjects = projectService.getAll();
        model.addAttribute("myProjects", myProjects);
        return "listProject";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "OfficeProject/projects/add", method = RequestMethod.GET)
    public String getAddPage(Model model) {
        projectService.createAddPageModel(model);
        return "newProject";
    }


    @RequestMapping(value = "OfficeProject/projects/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addNew(
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
        return "redirect: /OfficeProject/OfficeProject/projects/all";

    }

    @RequestMapping(value = "OfficeProject/projects", params = {"userId"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String getProjectsByUser(Model model, @RequestParam("userId") int id) {
        projectService.createByUserModel(id, model);
        return "userProject";

    }

    @RequestMapping(value = "OfficeProject/projects/edit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateExisting(@ModelAttribute("mproject") Project mproject) {
        projectService.add(mproject);
        return "projects";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "OfficeProject/projects/edit", method = RequestMethod.GET)
    public String getEditPage(Model model, @RequestParam("projectId") int id) {
        projectService.createEditPageModel(id, model);
        return "editProject";
    }

    @RequestMapping(value = "OfficeProject/projects/removeFrom", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUsersFromProject(@RequestParam("projectId") int id,
                                          Model model,
                                          @RequestParam(value = "auserId", required = false) ArrayList<Integer> selectedUsers
    ) {
        if (selectedUsers != null) {
            projectService.deleteUsersOperation(id, selectedUsers);
        }
        getEditPage(model, id);
        return "redirect: /OfficeProject/OfficeProject/projects/edit?projectId=" + id;
    }


    @RequestMapping(value = "OfficeProject/projects/addInto", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUsersIntoProject(@RequestParam("projectId") int id,
                                       Model model,
                                       @RequestParam(value = "nuserId", required = false) ArrayList<Integer> selectedUsers
    ) {
        if (selectedUsers != null) {
            projectService.addToExisting(id, selectedUsers);
        }
        getEditPage(model, id);
        return "redirect: /OfficeProject/OfficeProject/projects/edit?projectId=" + id;
    }


    @RequestMapping(value = "OfficeProject/projects/changeName", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changeProjectName(@RequestParam("projectId") int id,
                                     @ModelAttribute("mproject") Project project,
                                     BindingResult bindingResult,
                                     Model model
    ) {
        projectValidator.validate(project, bindingResult);
        if (bindingResult.hasErrors()) {
            projectService.createEditPageModel(id, model);
            return "redirect: /OfficeProject/OfficeProject/projects/edit?projectId=" + id;
        }
        projectService.changeName(id, project.getName());
        getEditPage(model, id);
        return "redirect: /OfficeProject/OfficeProject/projects/edit?projectId=" + id;

    }


    @RequestMapping(value = "OfficeProject/usersByProject/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUsers(Model model, @PathVariable("id") int id) {
        projectService.createByProjectModel(id, model);
        return "userProject";
    }


    @RequestMapping(value = "OfficeProject/projects/remove", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProject(@RequestParam("projectId") int Id) {
        projectService.remove(Id);
        return "redirect:/";
    }


}
