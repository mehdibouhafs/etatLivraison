package ma.munisys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.CommentaireStock;

public interface CommentaireStockRepository extends JpaRepository<CommentaireStock, Long>  {
	
	@Modifying
	@Query(value="delete from commentaire_stock where id = :id",nativeQuery=true)
	public void deleteById2(@Param("id")  Long id);

}
