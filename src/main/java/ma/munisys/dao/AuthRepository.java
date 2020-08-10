<<<<<<< HEAD
package ma.munisys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.munisys.entities.Authorisation;

public interface AuthRepository extends JpaRepository<Authorisation, Long> {
	
	public Authorisation findByAuthName(String authName);

}
=======
package ma.munisys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.munisys.entities.Authorisation;

public interface AuthRepository extends JpaRepository<Authorisation, Long> {
	
	public Authorisation findByAuthName(String authName);

}
>>>>>>> munisysRepo/main
