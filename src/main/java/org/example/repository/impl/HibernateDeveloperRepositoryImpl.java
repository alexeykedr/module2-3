package org.example.repository.impl;

import cfg.HibernateConfig;
import org.example.pojo.Developer;
import org.example.pojo.Status;
import org.example.repository.DeveloperRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class HibernateDeveloperRepositoryImpl implements DeveloperRepository {

    private Transaction transaction;

    private final String GET_ALL_DEVELOPERS = "FROM Developer d LEFT JOIN FETCH d.skills";

    @Override
    public Developer create(Developer developer) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
                session.save(developer);
                transaction.commit();
                return developer;
        } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
                return null;
            }
        }

    @Override
    public Developer get(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            try {
                transaction = session.beginTransaction();
                Developer developer = session.get(Developer.class, id);
                transaction.commit();
                return developer;
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
                return null;
            }
        }
    }

    @Override
    public List<Developer> getAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery(GET_ALL_DEVELOPERS);
            List<Developer> developers = query.getResultList();
            transaction.commit();
            return developers;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public Developer update(Developer developer) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.update(developer);
            transaction.commit();
            return developer;
        }catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Developer developer = session.get(Developer.class,id);
            developer.setStatus(Status.DELETED);
            session.update(developer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
}
