package service;

import DAO.EntityDAO;
import DAO.RoomDAO;
import model.AbstractEntity;
import model.Room;

import java.util.List;

public class RoomService implements EntityService{
    private RoomDAO roomDAO;

    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    @Override
    public void add(AbstractEntity entity) {
        roomDAO.addEntity(entity);
    }

    @Override
    public AbstractEntity get(int id) {
        return roomDAO.getEntity(id);
    }

    @Override
    public List<Room> getAll() {
        return roomDAO.getAllEntities();
    }

    @Override
    public void remove(int id) {
        roomDAO.removeEntity(id);
    }


}
