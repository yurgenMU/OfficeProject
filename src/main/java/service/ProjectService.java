package service;

import DAO.ProjectDAO;
import DAO.UserDAO;
import model.AbstractEntity;
import model.Project;

import model.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectService implements EntityService {
    private ProjectDAO projectDAO;

    private UserService userService;

    public void setProjectDAO(ProjectDAO entityDAO) {
        this.projectDAO = entityDAO;
    }


    public void setupNewProjectParams(Project project, ArrayList<Integer> selectedUsers) {
        Set<User> users = new HashSet<>();
        selectedUsers.stream().forEach(x -> users.add(userService.get(x)));
        users.stream().forEach(y -> {
            y.addProject(project);
            userService.edit(y);
        });
        project.setUsers(users);
    }

    public void deleteUsersOperation(int id, ArrayList<Integer> selectedUsers) {
        Project project = get(id);
        selectedUsers.stream().forEach(x -> project.removeUser(x));
        Set<User> users = new HashSet<>();
        selectedUsers.stream().forEach(x -> users.add(userService.get(x)));
        users.stream().forEach(y -> {
            y.removeProject(project);
            userService.edit(y);
        });
        edit(project);
    }

    public void changeName(int id, String newName) {
        Project p = get(id);
        p.setName(newName);
        edit(p);
    }

    public void addToExisting(int id, List<Integer> selectedUsers) {
        Project project = get(id);
        Set<User> users = new HashSet<>();
        selectedUsers.stream().forEach(x -> users.add(userService.get(x)));
        users.stream().forEach(y -> {
            y.addProject(project);
            project.addUser(y);
            userService.edit(y);
        });
        edit(project);
    }

    public Model createEditPageModel(int id, Model model) {
        Project project = get(id);
        Set<User> ausers = project.getUsers();
        List<User> nusers = userService.getAll();
        nusers.removeAll(ausers);
        model.addAttribute("mproject", project);
        model.addAttribute("actualUsers", ausers);
        model.addAttribute("nUsers", nusers);
        model.addAttribute("auserId", new ArrayList<Integer>());
        model.addAttribute("nuserId", new ArrayList<Integer>());
        return model;
    }

    /**
     * Creates Model instance with attriibutes required for getting Projects list
     * for actual user
     *
     * @param id    Actual user identifier
     * @param model Model instance that will be returned
     * @return
     */
    public Model createByUserModel(int id, Model model) {
        User user = userService.get(id);
        Set<Project> projects = user.getProjects();
        model.addAttribute("user", user);
        model.addAttribute("myProjects", projects);
        return model;
    }

    public Model createByProjectModel(int id, Model model) {
        Project mproject = get(id);
        Set<User> users = mproject.getUsers();
        model.addAttribute("mproject", mproject);
        model.addAttribute("users", users);
        return model;
    }

    public Model createAddPageModel(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("mproject", new Project());
        model.addAttribute("users", users);
        model.addAttribute("userId", new ArrayList<Integer>());
        return model;
    }


    @Override
    @Transactional
    public void add(AbstractEntity entity) {
        projectDAO.addEntity(entity);
    }

    @Override
    @Transactional
    public Project get(int id) {
        return projectDAO.getEntity(id);
    }

    @Override
    public Project getByName(String name) {
        return projectDAO.getEntityByName(name);
    }

    @Override
    @Transactional
    public void edit(AbstractEntity entity) {
        projectDAO.editEntity(entity);
    }

    @Override
    @Transactional
    public List<Project> getAll() {
        return projectDAO.getAllEntities();
    }

    @Override
    @Transactional
    public void remove(int id) {
        Project project = get(id);
        Set<User> users = project.getUsers();
        users.stream().forEach(x -> {
            x.removeProject(project);
            project.removeUser(x.getId());
            userService.edit(x);
        });
        edit(project);

        projectDAO.removeEntity(id);
    }

    public ProjectDAO getProjectDAO() {
        return projectDAO;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}


