package fr.diginamic.services;

import fr.diginamic.models.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CityService {

    @PersistenceContext
    private EntityManager em;

    /** Extrait toutes les villes de la base de données */
    public List<City> extractVilles() {
        return em.createQuery("SELECT c FROM City c", City.class).getResultList();
    }

    /** Extrait une ville unique par son ID */
    public City extractVille(int idVille) {
        return em.find(City.class, idVille);
    }

    /** Extrait une ville unique par son nom */
    public City extractVille(String nom) {
        return em.createQuery("SELECT c FROM City c WHERE c.name = :name", City.class)
                 .setParameter("name", nom)
                 .getSingleResult();
    }

    /** Insère une nouvelle ville dans la base de données et retourne la liste de toutes les villes */
    @Transactional
    public City insertVille(City ville) {
        em.persist(ville);
        em.flush(); 
        return ville;
    }

    /** Modifie une ville existante et retourne la liste de toutes les villes */
    @Transactional
    public List<City> modifierVille(int idVille, City villeModifiee) {
        City ville = em.find(City.class, idVille);
        if (ville != null) {
            ville.setName(villeModifiee.getName());
            ville.setPopulation(villeModifiee.getPopulation());
            em.merge(ville);
        }
        return extractVilles();
    }

    /** Supprime une ville de la base de données et retourne la liste de toutes les villes */
    @Transactional
    public List<City> supprimerVille(int idVille) {
        City ville = em.find(City.class, idVille);
        if (ville != null) {
            em.remove(ville);
        }
        return extractVilles();
    }

}
