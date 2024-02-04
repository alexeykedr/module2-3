package org.example.repository.impl;

import cfg.HibernateConfig;
import org.example.pojo.Skill;
import org.example.repository.SkillRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import java.util.ArrayList;
import java.util.List;

public class HibernateSkillRepositoryImpl implements SkillRepository {

    private final String GET_ALL_SKILLS = "select * from skills";

    private Session session;
    private Transaction transaction;

    @Override
    public Skill create(Skill skill) {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.getTransaction();
        try {
            session.save(skill);
            transaction.commit();
            return skill;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return null;
        }
    }

        @Override
    public Skill get(Long id) {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.getTransaction();
        try {
           Skill skill =  session.get(Skill.class, id);
            transaction.commit();
            return skill;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return null;
        }
    }

    @Override
    public List<Skill> getAll() {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.getTransaction();
        List<Skill> skills = new ArrayList<>();
        try {
            Query query = session.createNativeQuery(GET_ALL_SKILLS)
                    .addScalar("id", LongType.INSTANCE)
                    .addScalar("name", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(Skill.class));
            skills = query.getResultList();
            transaction.commit();
            return skills;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
            return null;
        }
    }

    @Override
    public Skill update(Skill skill) {
        session = HibernateConfig.getSessionFactory().openSession();
        transaction = session.getTransaction();
        try {
            session.update(skill);
            transaction.commit();
            return skill;
        }catch (Exception e){
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
        Skill skill = session.get(Skill.class, id);
        try {
            session.delete(skill);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
        }
    }
}
