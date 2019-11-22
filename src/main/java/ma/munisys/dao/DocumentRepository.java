package ma.munisys.dao;

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.Document;




public interface DocumentRepository extends JpaRepository<Document, String> {
	
	@Query("select p from Document p where p.etatRecouvrement.id = :x and p.cloture = :y order by p.client")
	public Page<Document> getDocuments(@Param("x") Long idEtatFacture,@Param("y") Boolean cloturer,Pageable pageable);
	
	
	@Query("select p from Document p where p.etatRecouvrement.id = :x and p.cloture = :y order by p.client")
	public Collection<Document> getDocuments(@Param("x") Long idEtatFacture,@Param("y") Boolean cloturer);
	
	

	
	@Query("select p from Document p where p.etatRecouvrement.id = :x and p.cloture = :y and p.chargerRecouvrement =:z order by p.client")
	public Collection<Document> getDocumentsByChargeRecouvrement(@Param("x") Long idEtatFacture,@Param("y") Boolean cloturer,@Param("z")String chargeRecouvrement);
	
	
	@Query("select p from Document p where p.etatRecouvrement.id = :x and p.cloture = :y and p.codeCommercial = :z order by p.client")
	public Collection<Document> getDocumentsByCommercial(@Param("x") Long idEtatFacture,@Param("y") Boolean cloturer,@Param("z")String codeCommercial);
	
	@Query("select p from Document p where p.etatRecouvrement.id = :x and p.cloture = :y and (p.commercial=:z or p.chefProjet =:z) order by p.client")
	public Collection<Document> getDocumentsByCommercialOrChefProjet(@Param("x") Long idEtatFacture,@Param("y") Boolean cloturer,@Param("z")String commercialOrChefProjet);
	
	
	@Query("select p from Document p where p.etatRecouvrement.id = :x and p.cloture = :y and p.chefProjet = :z order by p.client")
	public Collection<Document> getDocumentsByChefProjet(@Param("x") Long idEtatFacture,@Param("y") Boolean cloturer,@Param("z")String chefProjet);
	
	@Query("select p from Document p where p.etatRecouvrement.id = :x and p.cloture = :y and p.codeClient = :z order by p.client")
	public Collection<Document> getDocumentsByClient(@Param("x") Long idEtatFacture,@Param("y") Boolean cloturer,@Param("z")String codeClient);
	
	
	@Query("select p from Document p where p.etatRecouvrement.id = :x and p.cloture = :y and p.codeProjet = :z order by p.client")
	public Collection<Document> getDocumentsByCodeProjet(@Param("x") Long idEtatFacture,@Param("y") Boolean cloturer,@Param("z")String codeProjet);
	
	@Query(value="select p from Document p where p.etatRecouvrement.id = 1")
	public List<Document> findAllDocuments();
	
}
