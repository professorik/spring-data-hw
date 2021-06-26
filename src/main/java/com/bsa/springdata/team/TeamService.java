package com.bsa.springdata.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TechnologyRepository technologyRepository;

    public void updateTechnology(int devsNumber, String oldTechnologyName, String newTechnologyName) {
        Optional<Technology> oldTechnology = technologyRepository.findByName(oldTechnologyName);
        Optional<Technology> newTechnology = technologyRepository.findByName(newTechnologyName);
        if (newTechnology.isPresent() && oldTechnology.isPresent()) {
            teamRepository.setTechnology(devsNumber, oldTechnology.get(), newTechnology.get());
        }
    }

    public void normalizeName(String hipsters) {
        teamRepository.normalizeName(hipsters);
    }
}
