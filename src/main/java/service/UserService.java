package service;

import DAO.UserDAO;
import model.AbstractEntity;
import model.Project;
import model.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements EntityService{
//    private EntityDAO userDAO;
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    @Transactional
    public void add(AbstractEntity entity) {
        userDAO.addEntity(entity);
    }

    @Override
    @Transactional
    public AbstractEntity get(int id) {
        return userDAO.getEntity(id);
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return userDAO.getAllEntities();
    }

    @Override
    @Transactional
    public void remove(int id) {
        userDAO.removeEntity(id);
    }


    @Transactional
    public Set<Project> getProjects(int id){
        return  userDAO.getProjects(id);
    }
}
