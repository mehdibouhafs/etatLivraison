package ma.munisys.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.Piece;

public interface PieceRepository extends JpaRepository<Piece,String>,JpaSpecificationExecutor<Piece> {
	
	
	@Query("select p from Piece p where p.contrat.numContrat =:x ")
	public Page<Piece> getPieces(@Param("x") Long numContrat,Pageable pageable);

}
