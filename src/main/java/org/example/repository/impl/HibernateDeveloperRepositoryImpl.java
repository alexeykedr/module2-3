package org.example.repository.impl;

import cfg.HibernateConfig;
import org.example.pojo.Developer;
import org.example.pojo.Status;
import org.example.repository.DeveloperRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import javax.persistence.Query;
import java.util.List;


public class HibernateDeveloperRepositoryImpl implements DeveloperRepository {
        private final String GET_ALL_DEVELOPERS =
            "SELECT d.id, d.first_name, d.last_name,sp.id as spec_id, sp.name as spec_name," +
            " s.id as skill_id, s.name as skill_name " +
            " FROM developers d" +
            " LEFT JOIN developer_skills ds ON d.id = ds.developer_id" +
            " LEFT JOIN skills s ON ds.skill_id = s.id" +
            " LEFT JOIN specialties sp ON sp.id = d.specialty_id";

    private Transaction transaction;
    private Session session;

    //TODO: 1. move starting session to try-with-resourses section
    // 2. change id to id_dev, etcgit remote set-url origin https://github.com/alexeykedr/module2-3.git
    // 3.connecting skill across jointable. Вот для примера.
    //https://www.baeldung.com/hibernate-many-to-many

    @Override
    public Developer create(Developer developer) {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            session.save(developer);
            transaction.commit();
            session.close();
            return developer;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return null;
        }
    }

    @Override
    public Developer get(Long id) {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            Developer developer = session.get(Developer.class, id);
            transaction.commit();
            session.close();
            return developer;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return null;
        }
    }

    @Override
    public List<Developer> getAll() {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            Query query = session.createNativeQuery(GET_ALL_DEVELOPERS)
                    .addScalar("id", LongType.INSTANCE)
                    .addScalar("first_name", StringType.INSTANCE)
                    .addScalar("last_name", StringType.INSTANCE)
                    .addScalar("spec_id", LongType.INSTANCE)
                    .addScalar("spec_name", StringType.INSTANCE)
                    .addScalar("skill_id", LongType.INSTANCE)
                    .addScalar("skill_name", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(Developer.class));
            List<Developer> developers = query.getResultList();
            transaction.commit();
            session.close();
            return developers;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return null;
        }
    }

    @Override
    public Developer update(Developer developer) {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            session.update(developer);
            transaction.commit();
            session.close();
            return developer;
        }catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            Developer developer = session.get(Developer.class,id);
            developer.setStatus(Status.DELETED);
            session.update(developer);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
        }
    }
}
