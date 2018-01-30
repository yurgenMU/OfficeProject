package service;

import DAO.ProjectDAO;
import model.AbstractEntity;
import model.Project;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public class ProjectService implements EntityService{
    private ProjectDAO projectDAO;


    public void setProjectDAO(ProjectDAO entityDAO) {
        this.projectDAO = entityDAO;
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
        projectDAO.removeEntity(id);
    }
}
