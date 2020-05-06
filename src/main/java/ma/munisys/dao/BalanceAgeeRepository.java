package ma.munisys.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.BalanceAgee;
import ma.munisys.entities.StockProjet;


public interface BalanceAgeeRepository extends JpaRepository<BalanceAgee, Long>,JpaSpecificationExecutor<BalanceAgee> {
	
	@Query(value="SELECT p.id_balance,p.client,p.chargee_recouv,ISNULL(p.tois_mois,0) as tois_mois,ISNULL(p.six_mois,0) as six_mois,ISNULL(p.douze_mois,0) as douze_mois,ISNULL(p.sup_douze_mois,0) AS sup_douze_mois,ISNULL(p.total,0) as total,p.last_update from balance_agee p",nativeQuery=true)
	public Collection<BalanceAgee> getBalance();

	@Query(value="SELECT p.id_balance,p.client,ISNULL(p.tois_mois,0) as tois_mois,ISNULL(p.six_mois,0) as six_mois,ISNULL(p.douze_mois,0) as douze_mois,ISNULL(p.sup_douze_mois,0) AS sup_douze_mois,ISNULL(p.total,0) as total,p.last_update from balance_agee p where p.client= :client or p.chargee_recouv = :cr",nativeQuery=true)
	public Collection<BalanceAgee> getBalanceByFiltre(@Param("client") String client,@Param("cr") String cr);
	


}
