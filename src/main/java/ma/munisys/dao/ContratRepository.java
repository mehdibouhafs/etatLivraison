package ma.munisys.dao;

import java.util.Collection;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ma.munisys.entities.Contrat;


@RepositoryRestResource(collectionResourceRel = "contrats", path = "contrats")
public interface ContratRepository extends JpaRepository<Contrat,Long>,JpaSpecificationExecutor<Contrat> {
	
	
	
	@Query("select p from Contrat p where p.numContrat=1")
	public Collection<Contrat> getContratsByDate();
	
	@Query("select c from Contrat c where c.codeProjet =:x")
	public Collection<Contrat> getContratByCodeProjet(@Param("x") String codeProjet);
	

}
