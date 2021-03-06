package DAO;

import model.AbstractEntity;
import model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDAO implements EntityDAO {
    private SessionFactory sessionFactory;

    public RoomDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    @Override
    public <T extends AbstractEntity> void addEntity(T entity) {
        Room room = (Room) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(room);
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
    public Room getEntityByName(String RoomName) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Room where name =:name");
        query.setParameter("name", RoomName);
        return (Room) query.uniqueResult();
    }

    @Override
    public Room getEntity(int Id) {
        Session session = sessionFactory.openSession();
        Room room = session.get(Room.class, Id);
        session.close();
        return room;
    }

    @Override
    public <T extends AbstractEntity> void editEntity(T entity) {
        Room room = (Room) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(room);
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
    public void removeEntity(int Id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Room removedRoom = session.get(Room.class, Id);
            session.delete(removedRoom);
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
    public List<Room> getAllEntities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Room> rooms = session.createQuery("FROM Room").list();
        transaction.commit();
        session.close();
        return rooms;
    }
}
