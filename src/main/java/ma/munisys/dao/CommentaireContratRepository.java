package ma.munisys.dao;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.CommentaireContrat;

public interface CommentaireContratRepository extends JpaRepository<CommentaireContrat, Long> {
	
	@Query("select f from CommentaireContrat f where f.contrat.numContrat =:x order by f.date DESC")
	public Page<CommentaireContrat> getCommentairesContrat(@Param("x") Long numContrat ,Pageable pageable);
	
	
	@Query("select f from CommentaireContrat f where f.contrat.numContrat =:x order by f.date DESC")
	public Collection<CommentaireContrat> getAllCommentairesByContrat(@Param("x") Long numContrat);
	

}
