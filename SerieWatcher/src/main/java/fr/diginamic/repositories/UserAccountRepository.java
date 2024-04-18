package fr.diginamic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.diginamic.models.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{
    @Query("SELECT u FROM UserAccount u WHERE u.username = :username")
    public UserAccount findByUsername(@Param("username") String username);
}