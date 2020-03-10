package ma.munisys.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.Facture;

public interface FactureRepository extends JpaRepository<Facture,Long>,JpaSpecificationExecutor<Facture> {
	
	@Query("select sum(f.montantHT) from Facture f where f.contrat.numContrat =:x and YEAR(f.dateEnregistrement) =:y")
	public Double sumAmountContrat(@Param("x") Long numContrat,@Param("y")int year);


	@Query("select sum(f.montantHT) from Facture f where f.contrat.numContrat =:x and YEAR(f.dateEnregistrement) =:y and year(f.debutPeriode)<:y and year(f.finPeriode)<:y")
	public Double sumAmountContratInfAn(@Param("x") Long numContrat,@Param("y")int anneFacturation);

	
	
}
