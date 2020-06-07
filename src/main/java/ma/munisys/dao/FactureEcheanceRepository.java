package ma.munisys.dao;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.FactureEcheance;

public interface FactureEcheanceRepository extends JpaRepository<FactureEcheance,String>{
	
	@Query("select fe from FactureEcheance fe where fe.facture.numFacture =:x")
	public Collection<FactureEcheance> foundFactureEcheance(@Param("x") Long numFacture);
	
	@Query("select e from FactureEcheance e where e.echeance.id=:x order by e.facture.dateEnregistrement DESC")
	public Collection<FactureEcheance> getFactureEcheance(@Param("x")Long idEcheance);
	
	@Query("select f from FactureEcheance f where f.contrat.numContrat =:x and f.cloture = false order by f.facture.dateEnregistrement ASC")
	public Page<FactureEcheance> getFactureEcheance(@Param("x") Long numContrat,Pageable pageable);

	
	@Query("select fe from FactureEcheance fe where fe.facture.numFacture =:y and fe.contrat.numContrat=:x and fe.cloture = false ")
	public Collection<FactureEcheance> getFactureEcheance(@Param("x") Long numContrate,@Param("y")Long numFacture);
	
	
	
}
