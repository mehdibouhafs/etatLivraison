package ma.munisys.dao;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.Echeance;
import ma.munisys.entities.Facture;

public interface EcheanceRepository extends JpaRepository<Echeance,Long>,JpaSpecificationExecutor<Echeance> {
	
	
	@Query("select sum(f.montantRestFacture) from Echeance f where f.contrat.numContrat =:x and year(f.du)<:y and year(f.au)<:y")
	public Double sumAmountRestAFacture(@Param("x") Long numContrat,@Param("y")int anneFacturation);

	
	@Query("select e from Echeance e  where e.contrat.numContrat=:x and YEAR(e.du)>2018 and YEAR(e.du)<2021 order by e.du DESC")
	public Collection<Echeance> getEcheance(@Param("x")Long numContrat);

}
