package org.example.resume.entity;

import lombok.Data;
import java.util.List;

@Data
public class Resume {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phone;
    private String email;
    private String address;
    private byte[] photo;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Skill> skills;

    @Data
    public static class Education {
        private String institution;
        private String fromDate;
        private String toDate;
    }

    @Data
    public static class Experience {
        private String company;
        private String role;
        private String fromDate;
        private String toDate;
    }
}