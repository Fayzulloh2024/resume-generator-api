package org.example.resume.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.resume.entity.Resume;
import org.example.resume.entity.Skill;
import org.example.resume.repo.SkillRepository;
import org.example.resume.service.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeController {

    private final SkillRepository skillRepository;

    @PostMapping
    public ResponseEntity<?> createResume(@RequestBody Resume resume, HttpServletResponse response) throws Exception {
        byte[] pdfBytes = PdfService.generatePdf(resume);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=resume.pdf");
        response.getOutputStream().write(pdfBytes);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillRepository.findAll());
    }

    @GetMapping("/skills/search")
    public ResponseEntity<List<Skill>> searchSkills(@RequestParam String query) {
        List<Skill> skills = skillRepository.findByNameContainingIgnoreCase(query);
        return ResponseEntity.ok(skills);
    }

    @PostMapping("/skills")
    public ResponseEntity<Skill> addSkill(@RequestBody Skill skill) {
        skillRepository.save(skill);
        return ResponseEntity.ok(skill);
    }
}