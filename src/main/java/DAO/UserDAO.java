package DAO;

import model.AbstractEntity;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserDAO implements EntityDAO {
    private SessionFactory sessionFactory;

    public UserDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    @Override
    public <T extends AbstractEntity> void addEntity(T entity) {
        User user = (User) entity;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
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
        User user = (User) entity;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User modifiedUser = session.get(User.class, user.getId());
            modifiedUser.setFirstName(user.getFirstName());
            modifiedUser.setLastName(user.getLastName());
//            modifiedUser.setLogin(user.getLogin());
//                modifiedUser.setRole(user.getRole());
//                modifiedUser.setPassword(user.getPassword());
//                modifiedUser.setEmail(user.getEmail());
            session.update(modifiedUser);
            transaction.commit();
        }

    }

    @Override
    public void removeEntity(int Id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User removedUser = session.get(User.class, Id);
            session.delete(removedUser);
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllEntities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = session.createQuery("FROM User").list();
        transaction.commit();
        session.close();
        return users;
    }
}
