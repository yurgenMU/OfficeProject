package service;

import model.AbstractEntity;

import java.util.List;

public interface EntityService {

//    public<T extends EntityDAO> void setDAO(T entityDAO);

    public void add(AbstractEntity entity);

    public AbstractEntity get(int id);

    List<? extends AbstractEntity> getAll();

    void remove(int id);
}
