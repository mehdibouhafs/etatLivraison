package ma.munisys.dao;


import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.FactureEcheance;

public interface FactureEcheanceRepository extends JpaRepository<FactureEcheance,String>{
	
	@Query("select fe from FactureEcheance fe where fe.facture.numFacture =:x")
	public Collection<FactureEcheance> foundFactureEcheance(@Param("x") Long numFacture);
	
	@Query("select e from FactureEcheance e where e.echeance.id=:x order by e.facture.dateEnregistrement DESC")
	public Collection<FactureEcheance> getFactureEcheance(@Param("x")Long idEcheance);

}
