package org.example.controller;

import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.example.repository.jdbcimpl.JdbcSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyController {
    SpecialtyRepository specialtyRepository = new JdbcSpecialtyRepositoryImpl();

    public void create(Specialty specialty) {
        specialtyRepository.create(specialty);
    }

    public Specialty get(Long id) {
        return specialtyRepository.get(id);
    }

    public List<Specialty> getAll() {
        return specialtyRepository.getAll();
    }

    public void update(Specialty specialty) {
        specialtyRepository.update(specialty);

    }

    public void delete(Long id) {
        specialtyRepository.delete(id);
    }
}
