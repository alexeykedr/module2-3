package org.example.controller;

import org.example.pojo.Developer;
import org.example.repository.DeveloperRepository;
import org.example.repository.impl.HibernateDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperController {
    DeveloperRepository developerRepository;

    public DeveloperController(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public DeveloperController() {
        this.developerRepository = new HibernateDeveloperRepositoryImpl();
    }

    public void create(Developer developer) {
        developerRepository.create(developer);

    }

    public Developer get(Long id) {
        return developerRepository.get(id);
    }

    public List<Developer> getAll() {
        return developerRepository.getAll();
    }

    public void update(Developer developer) {
        developerRepository.update(developer);
    }

    public void delete(Long id) {
        developerRepository.delete(id);
    }

}
