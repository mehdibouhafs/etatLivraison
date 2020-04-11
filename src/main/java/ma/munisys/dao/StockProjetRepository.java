package ma.munisys.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import ma.munisys.entities.StockProjet;
import ma.munisys.entities.Commentaire;
import ma.munisys.entities.Produit;

@RepositoryRestResource(collectionResourceRel = "StockProjet", path = "StockProjet")
public interface StockProjetRepository extends JpaRepository<StockProjet,Long>,JpaSpecificationExecutor<StockProjet> {


	
	@Query(value="SELECT p from StockProjet p ORDER BY annee desc")
	public Collection<StockProjet> getStockProjet();
	
	@Query(value="SELECT p from StockProjet p  where p.client = :client OR p.numLot = :num_lot or p.annee= :annee or p.magasin = :magasin ORDER BY annee desc")
	public Collection<StockProjet> getStockProjetByFiltre(@Param("num_lot") String num_lot,@Param("client") String client, @Param("annee") String annee,@Param("magasin") String magasin);

	@Query(value="SELECT p from StockProjet p  where p.client = :client OR p.numLot = :num_lot or p.annee= :annee or (p.magasin = 'Stock commercial' OR p.magasin = 'Rabat - stock commercial') ORDER BY annee desc")
	public Collection<StockProjet> getStockProjetByFiltre2(@Param("num_lot") String num_lot,@Param("client") String client, @Param("annee") String annee);

}
