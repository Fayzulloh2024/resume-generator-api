package org.example.resume.service;

import lombok.RequiredArgsConstructor;
import org.example.resume.entity.Skill;
import org.example.resume.repo.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill createSkill(String name) {
        return skillRepository.findByName(name)
                .orElseGet(() -> skillRepository.save(Skill.builder().name(name).build()));
    }
}