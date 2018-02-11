package service;

import DAO.DateEntityDAO;
import model.AbstractEntity;
import model.DateEntity;

import model.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DateService implements EntityService {
    private DateEntityDAO dateEntityDAO;
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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
    public AbstractEntity getByName(String name) {
        return null;
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
    public DateEntity findByDate(java.sql.Date date){
        return dateEntityDAO.findByDate(date);
    }


    public void setNewDate(int userId, Date date){
        User user = userService.get(userId);
        Set<DateEntity> dates = user.getDates();
        DateEntity dateEntity = new DateEntity();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        DateEntity existing = findByDate(sqlDate);
        if (existing == null) {
            dateEntity.setDate(sqlDate);
        } else
            dateEntity = existing;
        dates.add(dateEntity);
        user.setDates(dates);
        userService.edit(user);
    }


    public Set<User> getUsersByDate(Date date){
        Set<User> users = new HashSet<>();
        DateEntity dateEntity = new DateEntity();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        DateEntity existing = findByDate(sqlDate);
        if (existing == null) {
            return null;
        } else{
            dateEntity = existing;
            users = dateEntity.getUsers();
        }
        return users;
    }

    public String dateToString(java.sql.Date date) {
        LocalDate localDate = new Date(date.getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getDayOfMonth() + " " + localDate.getMonth() +" " + localDate.getYear();
    }
}
