package service;

import DAO.DateEntityDAO;
import model.AbstractEntity;
import model.DateEntity;

import javax.transaction.Transactional;
import java.util.List;

public class DateService implements EntityService {
    private DateEntityDAO dateEntityDAO;

    public DateEntityDAO getDateEntityDAO() {
        return dateEntityDAO;
    }

    public void setDateEntityDAO(DateEntityDAO dateEntityDAO) {
        this.dateEntityDAO = dateEntityDAO;
    }

    @Override
    @Transactional
    public void add(AbstractEntity entity) {
        dateEntityDAO.addEntity(entity);
    }

    @Override
    @Transactional
    public AbstractEntity get(int id) {
        return dateEntityDAO.getEntity(id);
    }

    @Override
    @Transactional
    public List<DateEntity> getAll() {
        return dateEntityDAO.getAllEntities();
    }

    @Override
    @Transactional
    public void remove(int id) {
        dateEntityDAO.removeEntity(id);
    }
}
