package service;

import DAO.RoomDAO;
import model.AbstractEntity;
import model.Room;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RoomService implements EntityService{
    private RoomDAO roomDAO;

    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    @Override
    @Transactional
    public void add(AbstractEntity entity) {
        roomDAO.addEntity(entity);
    }

    @Override
    @Transactional
    public Room get(int id) {
        return roomDAO.getEntity(id);
    }

    @Override
    @Transactional
    public void edit(AbstractEntity entity) {
        roomDAO.editEntity(entity);
    }

    @Override
    @Transactional
    public List<Room> getAll() {
        return roomDAO.getAllEntities();
    }

    @Override
    @Transactional
    public void remove(int id) {
        roomDAO.removeEntity(id);
    }


}
