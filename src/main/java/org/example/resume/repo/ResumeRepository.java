package org.example.resume.repo;

import org.example.resume.entity.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResumeRepository extends MongoRepository<Resume, String> {
}