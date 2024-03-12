package org.example.repository.impl;

import cfg.HibernateConfig;
import org.example.pojo.Skill;
import org.example.repository.SkillRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateSkillRepositoryImpl implements SkillRepository {

    private final String GET_ALL_SKILLS = "FROM Skill";

    private Transaction transaction;

    @Override
    public Skill create(Skill skill) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(skill);
            transaction.commit();
            return skill;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

        @Override
    public Skill get(Long id) {
            try (Session session = HibernateConfig.getSessionFactory().openSession()){
                transaction = session.beginTransaction();
                Skill skill =  session.get(Skill.class, id);
            transaction.commit();
            return skill;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public List<Skill> getAll() {
        List<Skill> skills;
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createQuery(GET_ALL_SKILLS);
            skills = query.getResultList();
            transaction.commit();
            return skills;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public Skill update(Skill skill) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.update(skill);
            transaction.commit();
            return skill;
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()){
            Skill skill = session.get(Skill.class, id);
            transaction = session.beginTransaction();
            session.delete(skill);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
}
