package DAO;

import model.AbstractEntity;
import model.Project;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserDAO implements EntityDAO {
    private SessionFactory sessionFactory;

    public UserDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    @Override
    public <T extends AbstractEntity> void addEntity(T entity) {
        User userInfo = (User) entity;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(userInfo);
            transaction.commit();
        }

    }

    @Override
    public User getEntity(int Id) {
        Session session = sessionFactory.openSession();
        return session.get(User.class, Id);
    }

    @Override
    public <T extends AbstractEntity> void editEntity(T entity) {
        User userInfo = (User) entity;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User modifiedUserInfo = session.get(User.class, userInfo.getId());
            modifiedUserInfo.setFirstName(userInfo.getFirstName());
            modifiedUserInfo.setLastName(userInfo.getLastName());
            modifiedUserInfo.setLogin(userInfo.getLogin());
            modifiedUserInfo.setRole(userInfo.getRole());
            modifiedUserInfo.setPassword(userInfo.getPassword());
            modifiedUserInfo.setEmail(userInfo.getEmail());
            modifiedUserInfo.setProjects(userInfo.getProjects());
            session.update(modifiedUserInfo);
            transaction.commit();
        }

    }

    @Override
    public void removeEntity(int Id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User removedUserInfo = session.get(User.class, Id);
            session.delete(removedUserInfo);
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllEntities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> usersData = session.createQuery("FROM User").list();
        transaction.commit();
        session.close();
        return usersData;
    }

    public Set<Project> getProjects(int id){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Set<Project> projects = session.get(User.class, id).getProjects();
        transaction.commit();
        session.close();
        return projects;
    }

    public User getUser(String login){
        Session session = sessionFactory.openSession();
        return session.get(User.class, login);
    }
}
