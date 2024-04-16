package fr.diginamic.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.dto.ShowDTO;
import fr.diginamic.services.ShowService;

@RestController
@RequestMapping("/shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping
    public ResponseEntity<ShowDTO> createShow(@RequestBody ShowDTO showDTO) {
        ShowDTO createdShow = showService.createShow(showDTO);
        return new ResponseEntity<>(createdShow, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowDTO> getShowById(@PathVariable int id) {
        ShowDTO showDTO = showService.getShowById(id);
        return ResponseEntity.ok(showDTO);
    }

    @GetMapping
    public ResponseEntity<List<ShowDTO>> getAllShows() {
        List<ShowDTO> shows = showService.getAllShows();
        return ResponseEntity.ok(shows);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowDTO> updateShow(@PathVariable int id, @RequestBody ShowDTO showDTO) {
        showDTO.setId(id); // Ensure the ID is set correctly based on the path
        ShowDTO updatedShow = showService.updateShow(showDTO);
        return ResponseEntity.ok(updatedShow);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable int id) {
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }
}
