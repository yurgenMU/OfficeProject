package service;

import DAO.UserDAO;
import model.AbstractEntity;
import model.Project;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements EntityService {
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
        User userInfo = (User) entity;
        User proxyUser = userDAO.getEntity(userInfo.getId());
        if (userInfo.getFirstName() != null)
            proxyUser.setFirstName(userInfo.getFirstName());
        if (userInfo.getLastName() != null)
            proxyUser.setLastName(userInfo.getLastName());
        if (userInfo.getRoom() != null)
            proxyUser.setRoom(userInfo.getRoom());
        if (userInfo.getLogin() != null)
            proxyUser.setLogin(userInfo.getLogin());
        if (!userInfo.getPassword().equals(proxyUser.getPassword())) {
            if (userInfo.getPassword() != null)
                proxyUser.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        }
        if (userInfo.getDates() != null)
            if (userInfo.getDates().size() != 0) {
                proxyUser.setDates(userInfo.getDates());
            }

        if (userInfo.getProjects() != null)
            proxyUser.setProjects(userInfo.getProjects());
        userDAO.editEntity(proxyUser);
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return userDAO.getAllEntities();
    }

    @Override
    @Transactional
    public void remove(int id) {
        User user = get(id);
        user.setRoom(null);
        user.setProjects(null);
        user.setDates(null);
        userDAO.editEntity(user);
        userDAO.removeEntity(id);
    }


    @Override
    public User getByName(String username) {
        return userDAO.getEntityByName(username);
    }


    public void removeRoom(int userId){
        User user = get(userId);
        user.setRoom(null);
        userDAO.editEntity(user);
    }


    public boolean isActual(int id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return id == getByName(userDetails.getUsername()).getId();
    }
}
