package org.example.resume.repo;

import org.example.resume.entity.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends MongoRepository<Skill, String> {
    Optional<Skill> findByName(String name);

    List<Skill> findByNameContainingIgnoreCase(String name);

}