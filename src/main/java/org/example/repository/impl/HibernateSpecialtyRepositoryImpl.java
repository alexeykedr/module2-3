package org.example.repository.impl;

import cfg.HibernateConfig;
import org.example.pojo.Specialty;
import org.example.repository.SpecialtyRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateSpecialtyRepositoryImpl implements SpecialtyRepository {

    private final String GET_ALL_SPECIALTIES = "FROM Specialty";

    private Transaction transaction;

    @Override
    public Specialty create(Specialty specialty) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(specialty);
            transaction.commit();
            return specialty;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public Specialty get(Long id) {
        Specialty specialty;
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            specialty = session.get(Specialty.class, id);
            transaction.commit();
            return specialty;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public List<Specialty> getAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery(GET_ALL_SPECIALTIES);
            List<Specialty> specials = query.getResultList();
            transaction.commit();
            return specials;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public Specialty update(Specialty specialty) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.update(specialty);
            transaction.commit();
            return specialty;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Specialty specialty = session.get(Specialty.class, id);
            session.delete(specialty);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
}
