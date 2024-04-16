package fr.diginamic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.diginamic.models.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {
    List<Show> findByTitleContaining(String title);
}

