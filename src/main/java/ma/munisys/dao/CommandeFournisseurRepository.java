package ma.munisys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ma.munisys.entities.CommandeFournisseur;
import ma.munisys.entities.Contrat;
import ma.munisys.entities.Service;


public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur,String> {
	
	

}
