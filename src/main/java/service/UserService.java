package service;

import DAO.UserDAO;
import model.AbstractEntity;
import model.Project;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements EntityService{
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    @Transactional
    public void add(AbstractEntity entity) {
        User user = (User) entity;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userDAO.addEntity(entity);
    }

    @Override
    @Transactional
    public User get(int id) {
        return userDAO.getEntity(id);
    }

    @Override
    @Transactional
    public void edit(AbstractEntity entity) {
        userDAO.editEntity(entity);
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

    public User findByLogin(String username) {
        return userDAO.findByLogin(username);
    }


//    public Set<Project> getProjects(int id){
//        return  userDAO.getProjects(id);
//    }
}
