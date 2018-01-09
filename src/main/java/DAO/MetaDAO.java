package DAO;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MetaDAO {
    private SessionFactory sessionFactory;

    public MetaDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }


}
