package DAO;

import model.AbstractEntity;
import model.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDAO implements EntityDAO {
    private SessionFactory sessionFactory;

    public ProjectDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    @Override
    public <T extends AbstractEntity> void addEntity(T entity) {
        Project project = (Project) entity;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
        }
    }

    @Override
    public Project getEntity(int Id) {
        Session session = sessionFactory.openSession();
        return session.get(Project.class, Id);
    }

    @Override
    public <T extends AbstractEntity> void editEntity(T entity) {
        Project project = (Project) entity;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Project modifiedProject = session.get(Project.class, project.getId());
            modifiedProject.setName(project.getName());
            modifiedProject.setId(project.getId());
            modifiedProject.setUsers(project.getUsers());
            session.update(modifiedProject);
            transaction.commit();
        }
    }

    @Override
    public void removeEntity(int Id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Project removedProject = session.get(Project.class, Id);
            session.delete(removedProject);
            transaction.commit();
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
}
