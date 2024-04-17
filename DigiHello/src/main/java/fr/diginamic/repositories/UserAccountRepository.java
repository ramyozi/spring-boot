package fr.diginamic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.models.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{
	public UserAccount findByUsername(String username);
}