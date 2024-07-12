package org.example.resume.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.example.resume.entity.Resume;
import org.example.resume.entity.Skill;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public static byte[] generatePdf(Resume resume) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4);

        doc.setMargins(20, 20, 20, 20);

        // Header with name and photo
        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{1, 3}));
        headerTable.setWidth(UnitValue.createPercentValue(100));
        headerTable.setMarginBottom(20);

        if (resume.getPhoto() != null) {
            Image photo = new Image(ImageDataFactory.create(resume.getPhoto()));
            photo.setWidth(100).setHeight(100).setAutoScale(true);
            headerTable.addCell(new Cell().add(photo).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        } else {
            headerTable.addCell(new Cell().setBorder(Border.NO_BORDER));
        }


        doc.add(headerTable);

        // Contact Information
        Table contactTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}));
        contactTable.setWidth(UnitValue.createPercentValue(100)).setMarginBottom(20);

        contactTable.addCell(new Cell().add(new Paragraph("Phone:")).setBorder(Border.NO_BORDER));
        contactTable.addCell(new Cell().add(new Paragraph(resume.getPhone())).setBorder(Border.NO_BORDER));

        contactTable.addCell(new Cell().add(new Paragraph("Email:")).setBorder(Border.NO_BORDER));
        contactTable.addCell(new Cell().add(new Paragraph(resume.getEmail())).setBorder(Border.NO_BORDER));

        contactTable.addCell(new Cell().add(new Paragraph("Address:")).setBorder(Border.NO_BORDER));
        contactTable.addCell(new Cell().add(new Paragraph(resume.getAddress())).setBorder(Border.NO_BORDER));

        doc.add(contactTable);


        // Skills Section
        doc.add(new Paragraph("SKILLS").setFontSize(16).setBold().setUnderline().setMarginBottom(10));
        List<String> skills = resume.getSkills().stream().map(Skill::getName).toList();
        for (String skill : skills) {
            doc.add(new Paragraph("• " + skill).setFontSize(12));
        }
        doc.add(new Paragraph("\n"));


        // Education Section
        doc.add(new Paragraph("EDUCATION").setFontSize(16).setBold().setUnderline().setMarginBottom(10));
        for (Resume.Education education : resume.getEducations()) {
            doc.add(new Paragraph(education.getInstitution() + " (" + education.getFromDate() + " - "
                    + (education.getToDate() != null ? education.getToDate() : "Present") + ")").setFontSize(12));
        }
        doc.add(new Paragraph("\n"));

        // Experience Section
        doc.add(new Paragraph("EXPERIENCE").setFontSize(16).setBold().setUnderline().setMarginBottom(10));
        for (Resume.Experience experience : resume.getExperiences()) {
            doc.add(new Paragraph(experience.getCompany() + " - " + experience.getRole() + " (" + experience.getFromDate() + " - "
                    + (experience.getToDate() != null ? experience.getToDate() : "Present") + ")").setFontSize(12));
        }

        doc.close();
        return baos.toByteArray();
    }
}