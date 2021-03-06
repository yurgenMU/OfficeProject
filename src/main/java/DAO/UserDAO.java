package DAO;

import model.AbstractEntity;
import model.DateEntity;
import model.Project;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
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
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(userInfo);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            he.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }

    }

    @Override
    public User getEntity(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public <T extends AbstractEntity> void editEntity(T entity) {
        User userInfo = (User) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(userInfo);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null)
                transaction.rollback();
            he.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void removeEntity(int Id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User removedUser = session.get(User.class, Id);
            session.delete(removedUser);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null)
                transaction.rollback();
            he.printStackTrace();
        } finally {
            if (session != null)
                session.close();
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

    public Set<Project> getProjects(int id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Set<Project> projects = session.get(User.class, id).getProjects();
        transaction.commit();
        session.close();
        return projects;
    }

    public Set<DateEntity> getDates(int id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Set<DateEntity> dates = session.get(User.class, id).getDates();
        transaction.commit();
        session.close();
        return dates;
    }

    @Override
    public User getEntityByName(String userLogin) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User where login =:login");
        query.setParameter("login", userLogin);
        return (User) query.uniqueResult();
    }


    public Set<User> notActualUsers(int projectId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select distinct u.id, u.firstName, u.lastName " +
                "from Project p join p.users u where p.id != :id");
        query.setParameter("id", projectId);
        List<Object[]> proxyList = query.list();
        List<User> ans = new ArrayList<>();
        proxyList.stream().forEach(x -> {
            User u = new User();
            u.setId((Integer) x[0]);
            u.setFirstName((String) x[1]);
            u.setLastName((String) x[2]);
            ans.add(u);
        });
        session.close();
        return new HashSet<>(ans);
    }
}
