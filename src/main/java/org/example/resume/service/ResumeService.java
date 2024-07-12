package org.example.resume.service;

import lombok.RequiredArgsConstructor;
import org.example.resume.entity.Resume;
import org.example.resume.repo.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public Resume saveResume(Resume resume) {
        return resumeRepository.save(resume);
    }

    public Resume getResume(String id) {
        return resumeRepository.findById(id).orElse(null);
    }
}