package ma.munisys.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ma.munisys.entities.Produit;


@RepositoryRestResource(collectionResourceRel = "produit", path = "produit")
public interface ProduitRepository extends JpaRepository<Produit,String>,JpaSpecificationExecutor<Produit>  {
	
	@Query("select p from Produit p where p.qte >0 order by p.nomMagasin")
	public Collection<Produit> getAllStock();
	
	@Query("select p from Produit p where p.nature = :a or p.sousNature = :b or p.domaine = :c or p.sousDomaine =:d or p.numLot = :e or p.client = :f or p.nomMagasin = :g order by p.nomMagasin")
	public Collection<Produit> getAllStockByFiltre(@Param("a") String nature,@Param("b") String sousNature,@Param("c") String domaine,@Param("d") String sousDomaine,@Param("e")String numLot,@Param("f")String client,@Param("g")String nomMagasin);
			
	
	@Query(value="select distinct p.numLot from Produit p")
	public List<String> getDistinctLot();
	
	

}
