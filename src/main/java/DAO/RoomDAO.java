package DAO;

import model.AbstractEntity;
import model.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(room);
            transaction.commit();
        }
    }

    @Override
    public Room getEntity(int Id) {
        Session session = sessionFactory.openSession();
        return session.get(Room.class, Id);
    }

    @Override
    public <T extends AbstractEntity> void editEntity(T entity) {
        Room room = (Room) entity;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Room modifiedRoom = session.get(Room.class, room.getId());
            modifiedRoom.setName(room.getName());
            modifiedRoom.setId(room.getId());
            session.update(modifiedRoom);
            transaction.commit();
        }
    }

    @Override
    public void removeEntity(int Id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Room removedRoom = session.get(Room.class, Id);
            session.delete(removedRoom);
            transaction.commit();
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
