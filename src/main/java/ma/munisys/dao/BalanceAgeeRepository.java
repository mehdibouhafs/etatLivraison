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

<<<<<<< HEAD
=======
	
	
	@Query(value="SELECT (SELECT Max(id_balance) from balance_agee) as id_balance,client, charger_recouvrement as chargee_recouv,last_update,Total,[3M],[6M],[A12M],[Sup. 12M]\r\n" + 
			"FROM\r\n" + 
			"(\r\n" + 
			"    SELECT client,[charger_recouvrement],\r\n" + 
			"			montant_ouvert,\r\n" + 
			"           [age],MAX(last_update) OVER (PARTITION BY client) AS last_update ,SUM(ISNULL(montant_ouvert,0)) OVER (PARTITION BY client) AS total\r\n" + 
			"\r\n" + 
			"    FROM [dbo].[documents] where cloture=0 and statut in :status\r\n" + 
			"	\r\n" + 
			") AS SourceTable PIVOT(SUM([montant_ouvert]) FOR [age] IN([3M],\r\n" + 
			"                                                         [6M],\r\n" + 
			"                                                         [A12M],\r\n" + 
			"                                                         [Sup. 12M])) AS PivotTable\r\n" + 
			" ORDER BY TOTAL desc",nativeQuery=true)
	public Collection<String> FindByStatus(@Param("status") String[] status);
	
	@Query(value="select p from BalanceAgee p where p.client in (select distinct o.client from Document o where o.commercial= :am )")
	public Collection<BalanceAgee> FindByAM(@Param("am") String am);
>>>>>>> munisysRepo/main

}
