package fr.diginamic.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import fr.diginamic.dto.DepartementDto;
import fr.diginamic.models.City;
import fr.diginamic.models.Departement;
import fr.diginamic.services.CityService;
import fr.diginamic.services.DepartementService;

@RestController
@RequestMapping("/departements")
public class DepartementController {

	@Autowired
    private DepartementService departementService;
	
    @Autowired
    private CityService cityService;

    @PostMapping
    public ResponseEntity<Departement> createDepartement(@RequestBody Departement departement) {
        Departement created = departementService.saveDepartement(departement);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{code}")
    public ResponseEntity<DepartementDto> getDepartementByCode(@PathVariable String code) {
        DepartementDto departementDto = departementService.getDepartementDtoByCode(code);
        return departementDto != null ? ResponseEntity.ok(departementDto) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{code}/cities/top/{n}")
    public ResponseEntity<List<City>> getTopNCitiesByDepartment(@PathVariable String code, @PathVariable int n) {
        List<City> cities = cityService.getTopNCitiesByDepartment(code, n);
        return cities.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(cities);
    }

    @GetMapping("/{code}/cities/range")
    public ResponseEntity<List<City>> getCitiesByPopulationRange(@PathVariable String code, @RequestParam int min, @RequestParam int max) {
        List<City> cities = cityService.getCitiesByPopulationRangeAndDepartment(code, min, max);
        return cities.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(cities);
    }

    @GetMapping
    public ResponseEntity<List<DepartementDto>> getAllDepartements() {
        List<DepartementDto> departements = departementService.getAllDepartementDtos();
        return ResponseEntity.ok(departements);
    }

    
    @PutMapping("/{code}")
    public ResponseEntity<Departement> updateDepartement(@PathVariable String code, @RequestBody Departement departement) {
        Departement updated = departementService.updateDepartementByCode(code, departement);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteDepartement(@PathVariable String code) {
        boolean deleted = departementService.deleteDepartementByCode(code);
        return deleted ? ResponseEntity.ok("Departement deleted successfully") : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportDepartementsCsv() {
        List<DepartementDto> departements = departementService.getAllDepartementDtos();
        StringBuilder csvBuilder = new StringBuilder("Department Code,Department Name\n");

        for (DepartementDto departement : departements) {
            csvBuilder.append(departement.getCode()).append(",")
                      .append(departement.getName()).append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                                                        .filename("departements.csv")
                                                        .build());
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(csvBytes);
    }
    
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportDepartementsPdf() throws java.io.IOException {
        List<DepartementDto> departements = departementService.getAllDepartementDtos();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Paragraph header = new Paragraph("Department Report")
                .setFont(bold)
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(header);

        float[] columnWidths = {2, 3}; 
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell(new Paragraph("Department Code").setFont(bold));
        table.addHeaderCell(new Paragraph("Department Name").setFont(bold));

        for (DepartementDto departement : departements) {
            table.addCell(new Paragraph(departement.getCode()).setFont(font));
            table.addCell(new Paragraph(departement.getName()).setFont(font));
        }

        document.add(table);
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                                                        .filename("departements.pdf")
                                                        .build());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(byteArrayOutputStream.toByteArray());
    }
}
