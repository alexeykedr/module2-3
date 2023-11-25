package org.example.controller;

import org.example.model.Skill;
import org.example.repository.SkillRepository;
import org.example.repository.jdbcimpl.JdbcSkillRepositoryImpl;

import java.util.List;

public class SkillController {
    SkillRepository skillRepository = new JdbcSkillRepositoryImpl();

    public void create(Skill skill) {
        skillRepository.create(skill);
    }

    public Skill get(Long id) {
        return skillRepository.get(id);
    }

    public List<Skill> getAll() {
        return skillRepository.getAll();
    }

    public void update(Skill skill) {
        skillRepository.update(skill);
    }

    public void delete(Long id) {
        skillRepository.delete(id);
    }
}

