package ma.munisys.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.BalanceAgee;


public interface BalanceAgeeRepository extends JpaRepository<BalanceAgee, Long> {
	
	@Query(value="SELECT p.id_balance,p.client,ISNULL(p.tois_mois,0) as tois_mois,ISNULL(p.six_mois,0) as six_mois,ISNULL(p.douze_mois,0) as douze_mois,ISNULL(p.sup_douze_mois,0) AS sup_douze_mois,ISNULL(p.total,0) as total from balance_agee p",nativeQuery=true)
	public Collection<BalanceAgee> getBalance();

	@Query(value="SELECT p.id_balance,p.client,ISNULL(p.tois_mois,0) as tois_mois,ISNULL(p.six_mois,0) as six_mois,ISNULL(p.douze_mois,0) as douze_mois,ISNULL(p.sup_douze_mois,0) AS sup_douze_mois,ISNULL(p.total,0) as total from balance_agee p where p.client= :client",nativeQuery=true)
	public Collection<BalanceAgee> getBalanceByClient(@Param("client") String client);

}
