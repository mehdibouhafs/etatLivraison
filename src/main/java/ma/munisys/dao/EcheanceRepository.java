package ma.munisys.dao;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.Echeance;

public interface EcheanceRepository extends JpaRepository<Echeance,Long>,JpaSpecificationExecutor<Echeance> {
	
	@Query("select sum(f.montantRestFacture) from Echeance f where f.contrat.numContrat =:x and year(f.du)<:y and year(f.au)<:y and f.cloture = false")
	public Double sumAmountRestAFacture(@Param("x") Long numContrat,@Param("y")int anneFacturation);

	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.du DESC")
	public Collection<Echeance> getAllEcheancesFromContrat(@Param("x") Long numContrat,@Param("y")int anneEcheance);

	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false order by f.du DESC")
	public Collection<Echeance> getAllEcheancesFromContrat(@Param("x") Long numContrat);

	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.du DESC")
	public Page<Echeance> getEcheance(@Param("x") Long numContrat,@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.du DESC")
	public Page<Echeance> getEcheanceNotLinked(@Param("x") Long numContrat,@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (f.factures is not null and f.factures != '[]' and  f.factures!='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.du DESC")
	public Page<Echeance> getEcheanceLinked(@Param("x") Long numContrat,@Param("y")int anneEcheance ,Pageable pageable);
	
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y)")
	public Page<Echeance> getEcheanceWithoutOrder(@Param("x") Long numContrat,@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y)")
	public Page<Echeance> getEcheanceNotLinkedWithoutOrder(@Param("x") Long numContrat,@Param("y")int anneEcheance ,Pageable pageable);
	
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (f.factures is not null and f.factures != '[]' and f.factures!='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y)")
	public Page<Echeance> getEcheanceLinkedWithoutOrder(@Param("x") Long numContrat,@Param("y")int anneEcheance ,Pageable pageable);
	
	
	@Query("select f from Echeance f fetch all properties where f.cloture = false and f.id = :x ")
	public Echeance getEcheanceEager(@Param("x") Long id);
	
	@Query("select f from Echeance f where (f.factures = null or f.factures = '[]') and f.addedByUser = 0 and f.cloture = false ")
	public Page<Echeance> getAllEcheanceAFacturer(Pageable pageable);

	@Query("select count(f) from Echeance f where f.contrat.numContrat =:x and (f.factures = null or f.factures = '[]' or f.factures='')and year(f.au) <=:y and f.addedByUser = 0 and f.cloture = false ")
	public Integer getNbEcheanceAFacturer(@Param("x") Long numContrat,@Param("y")int anneEcheance);
	
	@Query("select f from Echeance f where   f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='') and year(f.au)<=:y order by f.du DESC")
	public Page<Echeance> getEcheancesNotLinked(@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f where  f.cloture = false and (f.factures is null or f.factures = '[]'  or f.factures='' )  and year(f.au)<=:y")
	public Page<Echeance> getEcheancesNotLinkedWithoutOrder(@Param("y")int anneEcheance ,Pageable pageable);
	
	
	@Query("select f from Echeance f where   f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='') and f.au<=:date order by f.du DESC")
	public Page<Echeance> getEcheancesNotLinkedDateParam(@Param("date") Date date,Pageable pageable);
	
	@Query("select f from Echeance f where  f.cloture = false and (f.factures is null or f.factures = '[]'  or f.factures='' )  and f.au<=:date")
	public Page<Echeance> getEcheancesNotLinkedWithoutOrderDateParam(@Param("date") Date date,Pageable pageable);
	
	
	@Query("select f from Echeance f where   f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='') and f.au<=:date order by f.du DESC")
	public Collection<Echeance> getEcheancesNotLinkedDateParam(@Param("date") Date date);
	
	@Query("select f from Echeance f where  f.cloture = false and (f.factures is null or f.factures = '[]'  or f.factures='' )  and year(f.au)<=:y")
	public Collection<Echeance> getEcheancesNotLinked(@Param("y")int anneEcheance);
	
	
	
	
	
}
