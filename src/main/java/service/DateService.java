package service;

import DAO.DateEntityDAO;
import model.AbstractEntity;
import model.DateEntity;

import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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
    public DateEntity get(int id) {
        return dateEntityDAO.getEntity(id);
    }

    @Override
    @Transactional
    public void edit(AbstractEntity entity) {
        dateEntityDAO.editEntity(entity);
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

    @Transactional
    public DateEntity findByDate(Date date){
        return dateEntityDAO.findByDate(date);
    }
}
