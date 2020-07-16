package ma.munisys.dao;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.ContratModel;

public interface ContratModelRepository extends JpaRepository<ContratModel, Long> {
	
	@Query("select f from ContratModel f where f.contrat.numContrat =:x and f.cloture = false order by f.du DESC")
	public Collection<ContratModel> getAllContratModel(@Param("x") Long numContrat);


	@Query("select f from ContratModel f where f.contrat.numContrat=:x and f.cloture = false order by f.du asc")
	public Page<ContratModel> getAllContratsModel(@Param("x") Long numContrat,Pageable pageable);
}
