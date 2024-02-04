package org.example.repository.impl;

import cfg.HibernateConfig;
import org.example.pojo.Specialty;
import org.example.repository.SpecialtyRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import java.util.List;

public class HibernateSpecialtyRepositoryImpl implements SpecialtyRepository {

    private final String GET_ALL_SPECIALTIES = "select * from specialties";

    private Transaction transaction;
    private Session session;

    @Override
    public Specialty create(Specialty specialty) {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            session.save(specialty);
            transaction.commit();
            return specialty;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return null;
        }
    }

    @Override
    public Specialty get(Long id) {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Specialty specialty = new Specialty();
        try {
            specialty = session.get(Specialty.class, id);
            transaction.commit();
            return specialty;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return null;
        }
    }

    @Override
    public List<Specialty> getAll() {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            Query query = session.createNativeQuery(GET_ALL_SPECIALTIES)
                    .addScalar("id", LongType.INSTANCE)
                    .addScalar("name", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(Specialty.class));
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
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
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
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            Specialty specialty = session.get(Specialty.class, id);
            session.delete(specialty);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
        }
    }
}
