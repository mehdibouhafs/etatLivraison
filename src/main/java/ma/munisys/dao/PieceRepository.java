package ma.munisys.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ma.munisys.entities.Piece;

public interface PieceRepository extends JpaRepository<Piece,String>,JpaSpecificationExecutor<Piece> {
	
	


}
