package fr.diginamic.controllers;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

import fr.diginamic.dto.CityDto;
import fr.diginamic.models.City;
import fr.diginamic.services.CityService;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getCities() {
        List<City> cities = cityService.extractCities();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable int id) {
        CityDto cityDto = cityService.getCityDtoById(id);
        return cityDto != null ? ResponseEntity.ok(cityDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/name/{nom}")
    public ResponseEntity<City> getCityByName(@PathVariable String nom) {
        City city = cityService.extractCity(nom);
        return city != null ? ResponseEntity.ok(city) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City nouvelleCity) {
        City savedCity = cityService.createCity(nouvelleCity);
        return ResponseEntity.ok(savedCity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<City>> updateCity(@PathVariable int id, @RequestBody City cityModifiee) {
        List<City> cities = cityService.updateCity(id, cityModifiee);
        return ResponseEntity.ok(cities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<City>> deleteCity(@PathVariable int id) {
        List<City> cities = cityService.deleteCity(id);
        return ResponseEntity.ok(cities);
    }

    @PostMapping("/init")
    public ResponseEntity<String> initializeData() {
        cityService.initData();
        return ResponseEntity.ok("Data initialized successfully");
    }
    
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportCitiesCsv() {
        List<CityDto> cities = cityService.getAllCityDtos();
        StringBuilder csvBuilder = new StringBuilder("City Name,Population,Department Code,Department Name\n");

        for (CityDto city : cities) {
            csvBuilder.append(city.getName()).append(",")
                      .append(city.getPopulation()).append(",")
                      .append(city.getDepartementCode()).append(",")
                      .append(city.getDepartementName()).append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                                                        .filename("cities.csv")
                                                        .build());
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(csvBytes);
    }
    
    @GetMapping("/export/pdf") 
    public ResponseEntity<byte[]> exportCitiesPdf() throws java.io.IOException {
        List<CityDto> cities = cityService.getAllCityDtos();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
 
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Paragraph header = new Paragraph("City Report")
                .setFont(bold)
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(header);

        float[] columnWidths = {4, 2, 2, 3};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        table.addHeaderCell(new Paragraph("City Name").setFont(bold));
        table.addHeaderCell(new Paragraph("Population").setFont(bold));
        table.addHeaderCell(new Paragraph("Department Code").setFont(bold));
        table.addHeaderCell(new Paragraph("Department Name").setFont(bold));

        for (CityDto city : cities) {
            table.addCell(new Paragraph(city.getName()).setFont(font));
            table.addCell(new Paragraph(String.valueOf(city.getPopulation())).setFont(font));
            table.addCell(new Paragraph(city.getDepartementCode()).setFont(font));
            table.addCell(new Paragraph(city.getDepartementName()).setFont(font));
        }

        document.add(table);
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                                                        .filename("cities.pdf")
                                                        .build());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return ResponseEntity.ok()
                             .headers(headers)
                             .body(byteArrayOutputStream.toByteArray());
    }
    
}
