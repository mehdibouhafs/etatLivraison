package ma.munisys.dao;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.Echeance;

public interface EcheanceRepository extends JpaRepository<Echeance,Long>,JpaSpecificationExecutor<Echeance> {
	
	@Query("select sum(f.montantRestFacture) from Echeance f where f.contrat.numContrat =:x and year(f.du)<:y and year(f.au)<:y and f.cloture = false")
	public Double sumAmountRestAFacture(@Param("x") Long numContrat,@Param("y")int anneFacturation);

	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false order by f.du DESC")
	public Collection<Echeance> getAllEcheancesFromContrat(@Param("x") Long numContrat);

	
	

}
