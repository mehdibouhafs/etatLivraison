package ma.munisys.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.CommandeFournisseur;



public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur,String> ,JpaSpecificationExecutor<CommandeFournisseur>{
	
	@Query("select f from CommandeFournisseur f join f.contrats c where c.numContrat =:x  order by f.dateEnregistrement ASC")
	public Page<CommandeFournisseur> getCommandeFournisseur(@Param("x") Long numContrat,Pageable pageable);

}
