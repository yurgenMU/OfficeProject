package service;

import DAO.EntityDAO;
import DAO.ProjectDAO;
import model.AbstractEntity;
import model.Project;

import javax.transaction.Transactional;
import java.util.List;

public class ProjectService implements EntityService{
    private ProjectDAO projectDAO;


    public void setProjectDAO(ProjectDAO entityDAO) {
        this.projectDAO = entityDAO;
    }

    @Override
    public void add(AbstractEntity entity) {
        projectDAO.addEntity(entity);
    }

    @Override
    public AbstractEntity get(int id) {
        return projectDAO.getEntity(id);
    }

    @Override
    public List<Project> getAll() {
        return projectDAO.getAllEntities();
    }

    @Override
    public void remove(int id) {
        projectDAO.removeEntity(id);
    }
}
