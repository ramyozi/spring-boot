package fr.diginamic.services;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.diginamic.models.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class DepartementService {

	@PersistenceContext
	private EntityManager em;

	public List<Departement> findAllDepartements() {
		return em.createQuery("SELECT d FROM Departement d",
				Departement.class).getResultList();
	}

	public Departement updateDepartement(Long id,
			Departement newDepartementData) {
		Departement departement = em.find(Departement.class, id);
		if (departement != null) {
			departement.setNom(newDepartementData.getNom());
			departement.setCode(newDepartementData.getCode());
			em.merge(departement);
		}
		return departement;
	}

	public void deleteDepartement(Long id) {
	    Departement departement = em.find(Departement.class, id);
	    if (departement != null) {
	        em.remove(departement);
	    }
	}

	/**
	 * Crée un département et le persiste dans la base de données.
	 * 
	 * @param departement Le département à persister.
	 * @return Le département persisté.
	 */
	@Transactional
	public Departement saveDepartement(Departement departement) {
		em.persist(departement);
		return departement;
	}

	/**
	 * Recherche un département par son code.
	 * 
	 * @param code Le code du département.
	 * @return Le département trouvé ou null.
	 */
	public Departement findDepartementByCode(String code) {
		return em
				.createQuery(
						"SELECT d FROM Departement d WHERE d.code = :code",
						Departement.class)
				.setParameter("code", code).getResultList().stream()
				.findFirst().orElse(null);
	}
}
