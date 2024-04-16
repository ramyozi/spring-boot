package fr.diginamic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.diginamic.models.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
	
	Genre findByName(String name);
    List<Genre> findByNameContaining(String name);

}