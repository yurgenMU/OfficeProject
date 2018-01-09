package controllers;//package controllers;

import model.Project;
import model.Room;
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
        List<Project> projects = projectService.getAll();
        model.addAttribute("projects", projects);
        return "listProject";
    }

    @RequestMapping(value = "projects/add", method = RequestMethod.POST)
    private String addNew(@ModelAttribute("room") Room room, Model model) {
        projectService.add(room);
        return "redirect:/";

    }

    @RequestMapping(value = "projects/edit/{id}", method = RequestMethod.POST)
    private String updateExisting(@ModelAttribute("project") Room room) {
        projectService.add(room);
        return "redirect:/";
    }

    @RequestMapping(value = "projects/add", method = RequestMethod.GET)
    private String getAddPage() {
        return "project";
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

}
