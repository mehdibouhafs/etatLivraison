package ma.munisys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.AppProfile;
import ma.munisys.entities.Authorisation;

public interface ProfileRepository extends JpaRepository<AppProfile, Long> {
	
	public AppProfile findByPrflName(String profileName);
	
	@Query("select p from AppProfile p join p.appUsers up on up.username = :x")
	public List<AppProfile> findProfilesByUsers(@Param("x")String username);
	
	@Query("select p.authorities from AppProfile p where p.prflName= :x")
	public List<Authorisation> findAuthorityByPrflName(@Param("x") String profileName);
}
