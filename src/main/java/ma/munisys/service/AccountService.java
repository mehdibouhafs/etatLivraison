<<<<<<< HEAD
package ma.munisys.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.AppProfile;
import ma.munisys.entities.AppUser;
import ma.munisys.entities.Authorisation;
import ma.munisys.entities.Service;

public interface AccountService {
	
	public AppUser saveUser(AppUser appUser);
	
	public AppProfile saveProfile(AppProfile profile);
	
	public void addProfileToUser(String username,String profileName);
	
	public AppUser findUserByUsername(String username);
	
	public Authorisation saveAuthorisation(String authorisationName);
	
	public void addAuthorisationToProfile(String authorisation,String profileName);
	
	public void addAuthorisationToUser(String authorisation,String username);
	
	public List<AppProfile> findProfilesByUsers(String username);
	
	public List<Authorisation> findAuthorityByPrflName(String profileName);
	
	public List<Authorisation> findUserAuthority(String username);

	public Service getService(String username);
	
	public String getSigle(String username);
	
	public String getFullName(String username);

}
=======
package ma.munisys.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.AppProfile;
import ma.munisys.entities.AppUser;
import ma.munisys.entities.Authorisation;
import ma.munisys.entities.Service;

public interface AccountService {
	
	public AppUser saveUser(AppUser appUser);
	
	public AppProfile saveProfile(AppProfile profile);
	
	public void addProfileToUser(String username,String profileName);
	
	public AppUser findUserByUsername(String username);
	
	public Authorisation saveAuthorisation(String authorisationName);
	
	public void addAuthorisationToProfile(String authorisation,String profileName);
	
	public void addAuthorisationToUser(String authorisation,String username);
	
	public List<AppProfile> findProfilesByUsers(String username);
	
	public List<Authorisation> findAuthorityByPrflName(String profileName);
	
	public List<Authorisation> findUserAuthority(String username);

	public Service getService(String username);
	
	public String getSigle(String username);
	
	public String getFullName(String username);

}
>>>>>>> munisysRepo/main
