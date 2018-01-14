package controllers;//package controllers;

import model.Project;
import model.Room;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.ProjectService;

import java.util.List;
import java.util.Set;

@Controller
public class ProjectController {

    private ProjectService projectService;


    @Autowired
    @Qualifier(value = "projectService")
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    private String listUsers(Model model) {
        List<Project> myProjects = projectService.getAll();

        model.addAttribute("myProjects", myProjects);
        return "listProject";
    }

    @RequestMapping(value = "projects/add", method = RequestMethod.POST)
    private String addNew(@ModelAttribute("mproject") Project mproject, Model model) {
        projectService.add(mproject);
        return "projects";

    }

    @RequestMapping(value = "projects/edit/{id}", method = RequestMethod.POST)
    private String updateExisting(@ModelAttribute("mproject") Project mproject) {
        projectService.add(mproject);
        return "projects";
    }

    @RequestMapping(value = "projects/add", method = RequestMethod.GET)
    private String getAddPage() {
        return "newProject";
    }

    @RequestMapping(value = "projects/edit/{id}", method = RequestMethod.GET)
    private String getEditPage(Model model, @PathVariable("id") int id) {
        Project project = (Project) projectService.get(id);
        model.addAttribute("project", project);
        return "update";
    }

    @RequestMapping(value = "projects/remove/{id}", method = RequestMethod.GET)
    private String delete(@PathVariable("id") int Id){
        projectService.remove(Id);
        return "redirect:/";
    }

    @RequestMapping(value = "usersByProject/{id}", method = RequestMethod.GET)
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
