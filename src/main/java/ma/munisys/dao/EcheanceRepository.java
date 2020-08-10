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
	
	@Query("select f from Echeance f fetch all properties  where f.contrat.numContrat =:x and  f.contratModel.name =:n and f.cloture = false and (year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.du DESC")
	public Page<Echeance> getEcheanceByModeleName(@Param("x") Long numContrat,@Param("n") String nomModele,@Param("y")int anneEcheance ,Pageable pageable);
	
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.du DESC")
	public Page<Echeance> getEcheanceNotLinked(@Param("x") Long numContrat,@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f fetch all properties where f.contrat.numContrat =:x and f.contratModel.name =:n and f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.du DESC")
	public Page<Echeance> getEcheanceNotLinkedByModeleName(@Param("x") Long numContrat,@Param("n") String nomModele,@Param("y")int anneEcheance ,Pageable pageable);
	
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (f.factures is not null and f.factures != '[]' and  f.factures!='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.du DESC")
	public Page<Echeance> getEcheanceLinked(@Param("x") Long numContrat,@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f fetch all properties where f.contrat.numContrat =:x and f.contratModel.name =:n and f.cloture = false and (f.factures is not null and f.factures != '[]' and  f.factures!='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.du DESC")
	public Page<Echeance> getEcheanceLinkedByModeleName(@Param("x") Long numContrat,@Param("n") String nomModele,@Param("y")int anneEcheance ,Pageable pageable);
	
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y)")
	public Page<Echeance> getEcheanceWithoutOrder(@Param("x") Long numContrat,@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f fetch all properties where f.contrat.numContrat =:x and f.contratModel.name =:n and f.cloture = false and (year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y)")
	public Page<Echeance> getEcheanceWithoutOrderByModeleName(@Param("x") Long numContrat,@Param("n") String nomModele,@Param("y")int anneEcheance ,Pageable pageable);
	
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y)")
	public Page<Echeance> getEcheanceNotLinkedWithoutOrder(@Param("x") Long numContrat,@Param("y")int anneEcheance ,Pageable pageable);
	
	
	@Query("select f from Echeance f fetch all properties where f.contrat.numContrat =:x and f.contratModel.name =:n and f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y)")
	public Page<Echeance> getEcheanceNotLinkedWithoutOrderByModeleName(@Param("x") Long numContrat,@Param("n") String nomModele,@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (f.factures is not null and f.factures != '[]' and f.factures!='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y)")
	public Page<Echeance> getEcheanceLinkedWithoutOrder(@Param("x") Long numContrat,@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f fetch all properties where f.contrat.numContrat =:x and f.contratModel.name =:n and f.cloture = false and (f.factures is not null and f.factures != '[]' and f.factures!='')  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y)")
	public Page<Echeance> getEcheanceLinkedWithoutOrderByModeleName(@Param("x") Long numContrat,@Param("n") String nomModele,@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f fetch all properties where f.cloture = false and f.id = :x ")
	public Echeance getEcheanceEager(@Param("x") Long id);
	
	@Query("select f from Echeance f where (f.factures = null or f.factures = '[]') and f.addedByUser = 0 and f.cloture = false ")
	public Page<Echeance> getAllEcheanceAFacturer(Pageable pageable);
	
	@Query("select f from Echeance f fetch all properties where  f.contratModel.name =:n  and (f.factures = null or f.factures = '[]') and f.addedByUser = 0 and f.cloture = false ")
	public Page<Echeance> getAllEcheanceAFacturerByModeleName(@Param("n") String nomModele,Pageable pageable);

	@Query("select count(f) from Echeance f where f.contrat.numContrat =:x and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :today) or (f.OccurenceFacturation!= 0 and year(f.au) <=:y)) and f.addedByUser = 0 and f.cloture = false ")
	public Integer getNbEcheanceAFacturer(@Param("x") Long numContrat,@Param("y")int anneEcheance,@Param("today") Date today);
	
	@Query("select count(f) from Echeance f fetch all properties where f.contrat.numContrat =:x and  f.contratModel.name =:n and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :today) or (f.OccurenceFacturation!= 0 and f.au <=:today)) and f.addedByUser = 0 and f.cloture = false ")
	public Integer getNbEcheanceAFacturerbyModeleName(@Param("x") Long numContrat,@Param("n") String nomModele,@Param("today") Date today);

	
	@Query("select f from Echeance f where  f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='') and year(f.au)<=:y order by f.du DESC")
	public Page<Echeance> getEcheancesNotLinked(@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f fetch all properties where f.contratModel.name =:x and  f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='') and year(f.au)<=:y order by f.du DESC")
	public Page<Echeance> getEcheancesNotLinkedByModele(@Param("x") String nomModele,@Param("y")int anneEcheance ,Pageable pageable);
	
	
	@Query("select f from Echeance f where  f.cloture = false and (f.factures is null or f.factures = '[]'  or f.factures='' )  and year(f.au)<=:y")
	public Page<Echeance> getEcheancesNotLinkedWithoutOrder(@Param("y")int anneEcheance ,Pageable pageable);
	
	@Query("select f from Echeance f  fetch all properties where f.contratModel.name =:x  and f.cloture = false and (f.factures is null or f.factures = '[]'  or f.factures='' )  and year(f.au)<=:y")
	public Page<Echeance> getEcheancesNotLinkedWithoutOrderByModeleName(@Param("x") String nomModele,@Param("y")int anneEcheance ,Pageable pageable);
	
	
	@Query("select f from Echeance f where   f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='') and f.au<=:date order by f.du DESC")
	public Page<Echeance> getEcheancesNotLinkedDateParam(@Param("date") Date date,Pageable pageable);
	
	@Query("select f from Echeance f where  f.cloture = false and (f.factures is null or f.factures = '[]'  or f.factures='' )  and f.au<=:date")
	public Page<Echeance> getEcheancesNotLinkedWithoutOrderDateParam(@Param("date") Date date,Pageable pageable);
	
	
	@Query("select f from Echeance f where   f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='') and f.au<=:date order by f.du DESC")
	public Collection<Echeance> getEcheancesNotLinkedDateParam(@Param("date") Date date);
	
	@Query("select f from Echeance f where  f.cloture = false and (f.factures is null or f.factures = '[]'  or f.factures='' )  and year(f.au)<=:y")
	public Collection<Echeance> getEcheancesNotLinked(@Param("y")int anneEcheance);
	
	
	@Query("select f from Echeance f where f.contratModel.id =:x and f.cloture = false order by f.du DESC")
	public Page<Echeance> getEcheanceByModele(@Param("x") Long numContrat ,Pageable pageable);
	
	
	@Query("select f from Echeance f fetch all properties where f.contratModel.id =:x and f.cloture = false order by f.du DESC")
	public Collection<Echeance> getEcheanceByModele(@Param("x") Long idModele);
	
	
	@Query("select f from Echeance f fetch all properties where f.contratModel.name =:x  and  f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='') and f.au<=:date order by f.du DESC")
	public Page<Echeance> getEcheancesNotLinkedDateParamByModele(@Param("x") String nomModele,@Param("date") Date date,Pageable pageable);
	
	
	@Query("select f from Echeance f fetch all properties where f.contratModel.name =:x and  f.cloture = false and (f.factures is null or f.factures = '[]' or f.factures='') and f.au<=:date order by f.du DESC")
	public Collection<Echeance> getEcheancesNotLinkedDateParamByModele(@Param("x") String nomModele,@Param("date") Date date);
	
	@Query("select f from Echeance f fetch all properties where f.contratModel.name =:x  and f.cloture = false and (f.factures is null or f.factures = '[]'  or f.factures='' )  and f.au<=:date")
	public Page<Echeance> getEcheancesNotLinkedWithoutOrderDateParamByModele(@Param("x") String nomModele,@Param("date") Date date,Pageable pageable);
	
	
	@Query("select f from Echeance f where  f.cloture = false and f.contratModel.id =:y")
	public Collection<Echeance> getEcheancesByModeleId(@Param("y")Long idModele);
	
	@Query("select f from Echeance f fetch all properties where  f.cloture = false and f.contratModel.name =:y")
	public Collection<Echeance> getEcheancesByModeleName(@Param("y")String nomModele);
	
	// delay
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :today) or (f.OccurenceFacturation!= 0 and f.au <=:today)) and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y)")
	public Page<Echeance> getEcheanceNotLinkedWithoutOrderDelay(@Param("x") Long numContrat,@Param("y")int anneEcheance ,@Param("today") Date today,Pageable pageable);
	
	@Query("select f from Echeance f where f.contrat.numContrat =:x and f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :today) or (f.OccurenceFacturation!= 0 and f.au <=:today)) and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.du DESC")
	public Page<Echeance> getEcheanceNotLinkedDelay(@Param("x") Long numContrat,@Param("y")int anneEcheance ,@Param("today") Date today,Pageable pageable);
	
	@Query("select f from Echeance f fetch all properties where f.contrat.numContrat =:x and f.contratModel.name =:n and f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :today) or (f.OccurenceFacturation!= 0 and f.au <=:today))  and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y)")
	public Page<Echeance> getEcheanceNotLinkedWithoutOrderByModeleNameDelay(@Param("x") Long numContrat,@Param("n") String nomModele,@Param("y")int anneEcheance ,@Param("today") Date today,Pageable pageable);
	
	@Query("select f from Echeance f fetch all properties where f.contrat.numContrat =:x and f.contratModel.name =:n and f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :today) or (f.OccurenceFacturation!= 0 and f.au <=:today)) and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.du DESC")
	public Page<Echeance> getEcheanceNotLinkedByModeleNameDelay(@Param("x") Long numContrat,@Param("n") String nomModele,@Param("y")int anneEcheance ,@Param("today") Date today,Pageable pageable);
	
	// delay date
	
	@Query("select f from Echeance f where  f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :date) or (f.OccurenceFacturation!= 0 and f.au <=:date)) and f.au<=:date")
	public Page<Echeance> getEcheancesNotLinkedWithoutOrderDateParamDelay(@Param("date") Date date,Pageable pageable);
	
	@Query("select f from Echeance f where   f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :date) or (f.OccurenceFacturation!= 0 and f.au <=:date))  and f.au<=:date order by f.du DESC")
	public Page<Echeance> getEcheancesNotLinkedDateParamDelay(@Param("date") Date date,Pageable pageable);
	
	@Query("select f from Echeance f fetch all properties where f.contratModel.name =:x  and f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :date) or (f.OccurenceFacturation!= 0 and f.au <=:date))  and f.au<=:date")
	public Page<Echeance> getEcheancesNotLinkedWithoutOrderDateParamByModeleDelay(@Param("x") String nomModele,@Param("date") Date date,Pageable pageable);
	
	
	@Query("select f from Echeance f fetch all properties where f.contratModel.name =:x  and  f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :date) or (f.OccurenceFacturation!= 0 and f.au <=:date)) and f.au<=:date order by f.du DESC")
	public Page<Echeance> getEcheancesNotLinkedDateParamByModeleDelay(@Param("x") String nomModele,@Param("date") Date date,Pageable pageable);
	
	// delay without numContrat
	
	@Query("select f from Echeance f where  f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :today) or (f.OccurenceFacturation!= 0 and f.au <=:today)) and year(f.au)<=:y")
	public Page<Echeance> getEcheancesNotLinkedWithoutOrderDelay(@Param("y")int anneEcheance ,@Param("today") Date today,Pageable pageable);
	
	
	@Query("select f from Echeance f where  f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :today) or (f.OccurenceFacturation!= 0 and f.au <=:today)) and year(f.au)<=:y order by f.du DESC")
	public Page<Echeance> getEcheancesNotLinkedDelay(@Param("y")int anneEcheance ,@Param("today") Date today,Pageable pageable);
	
	@Query("select f from Echeance f  fetch all properties where f.contratModel.name =:x  and f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :today) or (f.OccurenceFacturation!= 0 and f.au <=:today)) and year(f.au)<=:y")
	public Page<Echeance> getEcheancesNotLinkedWithoutOrderByModeleNameDelay(@Param("x") String nomModele,@Param("y")int anneEcheance ,@Param("today") Date today,Pageable pageable);
	
	@Query("select f from Echeance f fetch all properties where f.contratModel.name =:x and  f.cloture = false and (f.factures = null or f.factures = '[]' or f.factures='') and ((f.OccurenceFacturation= 0 and  f.du <= :today) or (f.OccurenceFacturation!= 0 and f.au <=:today)) and year(f.au)<=:y order by f.du DESC")
	public Page<Echeance> getEcheancesNotLinkedByModeleDelay(@Param("x") String nomModele,@Param("y")int anneEcheance ,@Param("today") Date today,Pageable pageable);
	

	@Query("select distinct f.contratModel.name from Echeance f fetch all properties where f.contrat.numContrat =:x and f.cloture = false and(year(f.du) =: y-1 or year(f.du)=:y or year(f.au)=:y-1 or year(f.au)=:y) order by f.contratModel.name DESC")
	public Collection<String> getAllDistinctNameContratModeles(@Param("x") Long numContrat,@Param("y")int anneEcheance);
	
	@Query("select f from Echeance f where f.cloture = false and ((f.OccurenceFacturation= 0 and  f.du <= :date) or (f.OccurenceFacturation!= 0 and f.au <=:date)) and f.au<=:date order by f.du DESC")
	public Collection<Echeance> getEcheancesNotLinkedDateParamDelay(@Param("date") Date date);
	
	@Query("select f from Echeance f where  f.cloture = false and ((f.OccurenceFacturation= 0 and  f.du <= :today) or (f.OccurenceFacturation!= 0 and f.au <=:today)) and year(f.au)<=:y")
	public Collection<Echeance> getEcheancesNotLinkedDelay(@Param("y")int anneEcheance,@Param("today") Date today);
	
}
