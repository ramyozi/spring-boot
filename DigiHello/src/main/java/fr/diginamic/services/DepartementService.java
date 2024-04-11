package fr.diginamic.services;

import fr.diginamic.dto.DepartementDto;
import fr.diginamic.models.City;
import fr.diginamic.models.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class DepartementService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Departement saveDepartement(Departement departement) {
        em.persist(departement);
        em.flush();
        return departement;
    }

    public DepartementDto getDepartementDtoByCode(String code) {
        Departement departement = em.createQuery("SELECT d FROM Departement d WHERE d.code = :code", Departement.class)
                                    .setParameter("code", code)
                                    .getResultList().stream().findFirst().orElse(null);
        if (departement != null) {
            DepartementDto dto = new DepartementDto();
            dto.setCode(departement.getCode());
            dto.setName(departement.getNom());
            dto.setPopulation(departement.getVilles().stream().mapToInt(City::getPopulation).sum());
            return dto;
        }
        return null;
    }

    @Transactional
    public Departement updateDepartementByCode(String code, Departement newDepartementData) {
        Departement departement = em.createQuery("SELECT d FROM Departement d WHERE d.code = :code", Departement.class)
                                    .setParameter("code", code)
                                    .getResultList().stream().findFirst().orElse(null);
        if (departement != null) {
            departement.setNom(newDepartementData.getNom());
            departement.setCode(newDepartementData.getCode());
            em.merge(departement);
        }
        return departement;
    }

    @Transactional
    public boolean deleteDepartementByCode(String code) {
        Departement departement = em.createQuery("SELECT d FROM Departement d WHERE d.code = :code", Departement.class)
                                    .setParameter("code", code)
                                    .getResultList().stream().findFirst().orElse(null);
        if (departement != null) {
            em.remove(departement);
            return true;
        }
        return false;
    }

    public List<DepartementDto> getAllDepartementDtos() {
        List<Departement> departements = em.createQuery("SELECT d FROM Departement d", Departement.class).getResultList();
        return departements.stream().map(d -> {
            DepartementDto dto = new DepartementDto();
            dto.setCode(d.getCode());
            dto.setName(d.getNom());
            dto.setPopulation(d.getVilles().stream().mapToInt(City::getPopulation).sum());
            return dto;
        }).collect(Collectors.toList());
    }

}
