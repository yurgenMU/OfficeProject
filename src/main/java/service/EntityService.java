package service;

import model.AbstractEntity;

import java.util.List;

public interface EntityService {

    void add(AbstractEntity entity);

    AbstractEntity get(int id);

    AbstractEntity getByName(String name);

    void edit(AbstractEntity entity);

    List<? extends AbstractEntity> getAll();

    void remove(int id);

}
