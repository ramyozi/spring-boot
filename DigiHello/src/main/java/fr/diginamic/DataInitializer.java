package fr.diginamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.diginamic.models.UserAccount;
import fr.diginamic.repositories.UserAccountRepository;
import fr.diginamic.services.CityService;
import fr.diginamic.services.DepartementService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CityService cityService;

    @Autowired
    private DepartementService departementService;
    
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void run(String... args) throws Exception {
    	UserAccount a = new UserAccount(
    				"Luffy",
    				"Luffy",
    				"ADMIN"
    			);
    	
    	UserAccount b = new UserAccount(
				"Zorro",
				"Zorro",
				"USER"
			);
    	
    	UserAccount c = new UserAccount(
				"test",
				"test",
				"USER"
			);
    	
    	userAccountRepository.save(a);
    	userAccountRepository.save(b);
    	userAccountRepository.save(c);
    	
    }
   
}
