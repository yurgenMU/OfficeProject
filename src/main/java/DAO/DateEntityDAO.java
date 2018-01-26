package DAO;

import model.AbstractEntity;
import model.DateEntity;
import model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DateEntityDAO implements EntityDAO {
    private SessionFactory sessionFactory;

    public DateEntityDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public <T extends AbstractEntity> void addEntity(T entity) {
        DateEntity room = (DateEntity) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(room);
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
    public DateEntity getEntity(int Id) {
        Session session = sessionFactory.openSession();
        return session.get(DateEntity.class, Id);
    }

    @Override
    public <T extends AbstractEntity> void editEntity(T entity) {
        DateEntity dateEntity = (DateEntity) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(dateEntity);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null)
                transaction.rollback();
            he.printStackTrace();
        } finally {
            if (session != null){
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
            DateEntity dateEntity = session.get(DateEntity.class, Id);
            session.delete(dateEntity);
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
    public List<DateEntity> getAllEntities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<DateEntity> dateEntitiess = session.createQuery("FROM DateEntity ").list();
        transaction.commit();
        session.close();
        return dateEntitiess;
    }

}
