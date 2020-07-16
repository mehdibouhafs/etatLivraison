package ma.munisys.dao;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ma.munisys.dto.StatisticContrat;
import ma.munisys.entities.Contrat;


@RepositoryRestResource(collectionResourceRel = "contrats", path = "contrats")
public interface ContratRepository extends JpaRepository<Contrat,Long>,JpaSpecificationExecutor<Contrat> {
	
	
	
	@Query("select p from Contrat p where p.numContrat=1")
	public Collection<Contrat> getContratsByDate();
	
	@Query("select c from Contrat c where c.codeProjet =:x")
	public Collection<Contrat> getContratByCodeProjet(@Param("x") String codeProjet);
	
	@Query("select c from Contrat c where c.cloture=0 order by c.numContrat ASC")
	public Page<Contrat> getAllContrats(Pageable pageable);
	
	
	@Query("select c from Contrat c fetch all properties where c.cloture=0")
	public Collection<Contrat> getAllContrats();
	
	
	@Query("select distinct p.numMarche from Contrat p where  p.numMarche IS NOT NULL and p.numMarche!='' and p.cloture = 0 order by p.numMarche ASC")
	public Collection<String> getDistinctNumMarche();
	
	@Query("select distinct p.nomPartenaire from Contrat p where p.nomPartenaire IS NOT NULL and p.nomPartenaire!='' and p.cloture = 0 order by p.nomPartenaire ASC")
	public Collection<String> getDistinctClients();
	
	@Query("select distinct p.bu from Contrat p where p.bu IS NOT NULL and p.bu!='' and p.cloture = 0 order by p.bu ASC")
	public Collection<String> getDistinctBus();
	
	@Query("select distinct p.pilote from Contrat p where p.pilote IS NOT NULL and p.pilote!='' and p.cloture = 0 order by p.pilote ASC")
	public Collection<String> getDistinctPilotes();
	@Query("select new ma.munisys.dto.StatisticContrat(sum(p.montantContrat),sum(p.montantFactureAn),sum(p.montantRestFactureAn),sum(p.montantProvisionFactureInfAnneeEnCours),sum(p.montantProvisionAFactureInfAnneeEnCours)) from Contrat p where p.cloture = 0")
	public StatisticContrat getStatisticContrat();

}
