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

	@Query(value="SELECT p.id_balance,p.client,p.chargee_recouv,ISNULL(p.tois_mois,0) as tois_mois,p.six_mois,ISNULL(p.douze_mois,0) as douze_mois,ISNULL(p.sup_douze_mois,0) AS sup_douze_mois,ISNULL(p.total,0) as total,p.last_update from balance_agee p where p.client= :client or p.chargee_recouv = :cr",nativeQuery=true)
	public Collection<BalanceAgee> getBalanceByFiltre(@Param("client") String client,@Param("cr") String cr);
	
	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where six_mois IS NOT NULL ",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAge6();
	
	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where tois_mois IS NOT NULL ",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAge3();	

	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where douze_mois IS NOT NULL ",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAge12();	
	
	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where sup_douze_mois IS NOT NULL ",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAgeSup12();	




	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where (client= :client or chargee_recouv = :cr) and six_mois IS NOT NULL",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAge6(@Param("client") String client,@Param("cr") String cr);
	
	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where (client= :client or chargee_recouv = :cr) and tois_mois IS NOT NULL",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAge3(@Param("client") String client,@Param("cr") String cr);	

	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where (client= :client or chargee_recouv = :cr) and douze_mois IS NOT NULL",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAge12(@Param("client") String client,@Param("cr") String cr);	
	
	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where (client= :client or chargee_recouv = :cr) and sup_douze_mois IS NOT NULL ",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAgeSup12(@Param("client") String client,@Param("cr") String cr);	

	
	
	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where client= :client and chargee_recouv = :cr and six_mois IS NOT NULL",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAge66(@Param("client") String client,@Param("cr") String cr);
	
	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where client= :client and chargee_recouv = :cr and tois_mois IS NOT NULL",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAge33(@Param("client") String client,@Param("cr") String cr);	

	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where client= :client and chargee_recouv = :cr and douze_mois IS NOT NULL",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAge122(@Param("client") String client,@Param("cr") String cr);	
	
	@Query(value="SELECT id_balance,client,chargee_recouv,ISNULL(tois_mois,0) as tois_mois,six_mois,ISNULL(douze_mois,0) as douze_mois,ISNULL(sup_douze_mois,0) AS sup_douze_mois,ISNULL(total,0) as total,last_update from balance_agee where client= :client and chargee_recouv = :cr and sup_douze_mois IS NOT NULL ",nativeQuery=true)
	public Collection<BalanceAgee> findAllByAgeSup122(@Param("client") String client,@Param("cr") String cr);	


}
