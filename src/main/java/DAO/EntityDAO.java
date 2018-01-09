package DAO;

import model.AbstractEntity;

import java.util.List;

public interface EntityDAO {

    <T extends AbstractEntity> void addEntity(T entity);
    <T extends AbstractEntity> T getEntity(int Id);
    <T extends AbstractEntity> void editEntity(T entity);
    void removeEntity(int Id);
    List<? extends AbstractEntity> getAllEntities();
}
