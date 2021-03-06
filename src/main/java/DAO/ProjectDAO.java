package DAO;

import model.AbstractEntity;
import model.Project;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class ProjectDAO implements EntityDAO {
    private SessionFactory sessionFactory;

    public ProjectDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    @Override
    public <T extends AbstractEntity> void addEntity(T entity) {
        Project project = (Project) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(project);
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
    public Project getEntityByName(String projectName) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Project where name =:name");
        query.setParameter("name", projectName);
        Project ans = (Project) query.uniqueResult();
        session.close();
        return ans;
    }

    @Override
    public Project getEntity(int Id) {
        Session session = sessionFactory.openSession();
        Project project = session.get(Project.class, Id);
        session.close();
        return project;
    }

    @Override
    public <T extends AbstractEntity> void editEntity(T entity) {
        Project project = (Project) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(project);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
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
            Project removedProject = session.get(Project.class, Id);
            session.delete(removedProject);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            he.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Project> getAllEntities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Project> projects = session.createQuery("FROM Project").list();
        transaction.commit();
        session.close();
        return projects;
    }

//    public List<Project> notActualProjects(int userId) {
//        Session session = sessionFactory.openSession();
//        Query query = session.createQuery("select p.id, p.name from User u join u.projects p where u.id != :id");
//        query.setParameter("id", userId);
//        List<Object[]> proxyList = query.list();
//        List<Project> ans = new ArrayList<>();
//        proxyList.stream().forEach(x -> {
//            Project p = new Project();
//            p.setId((Integer) x[0]);
//            p.setName((String) x[1]);
//            ans.add(p);
//        });
//        session.close();
//        return ans;
//    }
//
//    public void removeUsers(List<Integer> userIdList) {
//        Session session = sessionFactory.openSession();
//        Query query = session.createQuery("delete from User u join u.projects p where u.id != :id");
//
//    }


}
