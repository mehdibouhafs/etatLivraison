<<<<<<< HEAD
package ma.munisys.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.Projet;



public interface ProjetRepository extends JpaRepository<Projet, String>,JpaSpecificationExecutor<Projet>  {
	
	@Query("select p from Projet p where  p.cloture = :y order by p.client")
	public Page<Projet> getProjets(@Param("y") Boolean cloturer,Pageable pageable);
	
	
	@Query("select p from Projet p where p.cloture = :y and (p.bu = :z or p.bu = :u) order by p.client")
	public Collection<Projet> getProjetsByBu(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2);
	
	@Query("select p from Projet p where p.cloture = :y and (p.bu = :z or p.bu = :u) and (p.commercial = :g or p.chefProjet = :g) order by p.client")
	public Collection<Projet> getProjetsByBuAndCommercial(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("g")String commercial);
	
	@Query("select p from Projet p where p.cloture = :y and (p.bu = :z or p.bu = :u) and (p.commercial = :g or p.chefProjet = :g) order by p.client")
	public Collection<Projet> getProjetsByBuAndChefProjet(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("g")String chefProjet);

	@Query("select p from Projet p where p.cloture = :y and p.statut = :z  order by p.client")
	public Collection<Projet> getProjetsByStatut(@Param("y") Boolean cloturer,@Param("z")String statut);

	@Query("select p from Projet p where p.cloture = :y and p.statut = :z and (p.commercial = :g or p.chefProjet = :g)  order by p.client")
	public Collection<Projet> getProjetsByStatutAndCommercial(@Param("y") Boolean cloturer,@Param("z")String statut,@Param("g")String commercial);

	@Query("select p from Projet p where p.cloture = :y and p.statut = :z and (p.commercial = :g or p.chefProjet = :g)  order by p.client")
	public Collection<Projet> getProjetsByStatutAndChefProjet(@Param("y") Boolean cloturer,@Param("z")String statut,@Param("g")String chefProjet);

	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is null  order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNull(@Param("y") Boolean cloturer);
	
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is null and p.commercial=:z  order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndCommercial(@Param("y") Boolean cloturer,@Param("z")String commercial);
	
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is not null order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNull(@Param("y") Boolean cloturer);

	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is not null and p.commercial=:z order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndCommercial(@Param("y") Boolean cloturer,@Param("z")String commercial);


	@Query("select p from Projet p where p.cloture = :y and  (p.bu = :z or p.bu = :u) and p.statut =:t order by p.client") 
	public Collection<Projet> getProjetsByBuAndStatut(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("t")String statut);
	
	@Query("select p from Projet p where p.cloture = :y and  (p.bu = :z or p.bu = :u) and p.statut =:t and (p.commercial = :g or p.chefProjet = :g) order by p.client") 
	public Collection<Projet> getProjetsByBuAndStatutAndCommercial(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("t")String statut,@Param("g")String commercial);
	
	
	@Query("select p from Projet p where p.cloture = :y and  (p.bu = :z or p.bu = :u) and p.statut =:t and (p.commercial = :g or p.chefProjet = :g) order by p.client") 
	public Collection<Projet> getProjetsByBuAndStatutAndchefProjet(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("t")String statut,@Param("g")String chefProjet);
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is not null and (p.bu =:x or p.bu =:z) order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndBu(@Param("y") Boolean cloturer,@Param("x")String bu1,@Param("z")String bu2);
	

	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is null and (p.bu =:x or p.bu =:z) order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndBu(@Param("y") Boolean cloturer, @Param("x") String bu1,@Param("z") String bu2);
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is null and p.statut = :x order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndStatut(@Param("y") Boolean cloturer,@Param("x")String statut);
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is not null and p.statut =:t  order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndStatut(@Param("y") Boolean cloturer,@Param("t")String statut);
	
	@Query("select p from Projet p where p.cloture = :y and  (p.bu = :z or p.bu = :u) and p.chefProjet is not null and p.statut =:t order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndBuAndStatut(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("t")String statut);
	
	@Query("select p from Projet p where p.cloture = :y and  (p.bu = :z or p.bu = :u) and p.chefProjet is null and p.statut =:t  order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndBuAndStatut(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("t")String statut);
	
	
	@Query("select p from Projet p  order by p.client")
	public Collection<Projet> getAllProjets();
	
	@Query("select p from Projet p where p.cloture = :y  and (p.commercial =:g or p.chefProjet =:g)  order by p.client")
	public Collection<Projet> getAllProjetsByCommercialOrChefProjet(@Param("y") Boolean cloturer,@Param("g") String commercialOrChefProjet);
	
	
	@Query("select p from Projet p where p.cloture = :y  order by p.client")
	public Collection<Projet> getProjets(@Param("y") Boolean cloturer);
	

	//@Query(value="select p from Projet p where p.etatProjet.id = 1 and p.cloture = :y and p.dateCmd >= :z @Param("z")Date dateCmd")
	@Query(value="select p from Projet p where p.etatProjet.id = 1 and p.cloture = :y and p.dateCmd >= :z ")
	public List<Projet> findAllProjetsByDateSup(@Param("y") Boolean cloturer,@Param("z")Date dateCmd );
	
	@Query(value="select p from Projet p where p.etatProjet.id = 1 and p.cloture = :y and p.dateCmd <= :z ")
	public List<Projet> findAllProjetsByDateInf(@Param("y") Boolean cloturer,@Param("z")Date dateCmd );
	
	@Query(value="select p from Projet p where p.etatProjet.id = 1")
	public List<Projet> findAllProjetsByDate();
	
	
	@Query(value="select distinct p.client from Projet p where p.client !='' and p.client IS NOT NULL")
	public List<String> getDistinctClient();
	
	@Query(value="select distinct p.commercial from Projet p where p.commercial !='' and p.commercial IS NOT NULL")
	public List<String> getDistinctCommercial();
	
	@Query(value="select distinct p.chefProjet from Projet p where p.chefProjet !='' and p.chefProjet IS NOT NULL")
	public List<String> getDistinctChefProjet();
	
	@Modifying
	@Query("update Projet p set p.cloture = :statut,p.cloturedByUser=:cloturedByUser,p.decloturedByUser = :decloturedByUser where p.codeProjet = :codeProjet")
	public int updateStatutProjet(@Param("statut") boolean statut,@Param("cloturedByUser") boolean cloturedByUser,@Param("decloturedByUser") boolean decloturedByUser, @Param("codeProjet") String codeProjet);
	
	
	@Modifying
	@Query("update Projet p set p.cloture = :statut,p.cloturedByUser=:cloturedByUser,p.decloturedByUser = :decloturedByUser,p.restAlivrer=:ral,p.livrerNonFacture=:lnf,p.facturation=:facturation,p.livreFacturePayer=:lfp where p.codeProjet = :codeProjet")
	
	
	public int updateStatutProjetMontant(@Param("statut") boolean statut,@Param("cloturedByUser") boolean cloturedByUser,@Param("decloturedByUser") boolean decloturedByUser,@Param("facturation") Double facturation,@Param("lnf") Double lnf,@Param("lfp") Double lfp,@Param("ral") Double ral, @Param("codeProjet") String codeProjet);
	
	
	/*@Query(value="select p from Projet p where p.etatProjet.id = 1 and p.cloture = :y and p.commercial like :z or p.chefProjet like :z ")
	public List<Projet> findAllProjetsByCommercialOrChefProjet(@Param("y") Boolean cloturer,@Param("z")String filtre )*/
	
	
}
=======
package ma.munisys.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.Projet;



public interface ProjetRepository extends JpaRepository<Projet, String>,JpaSpecificationExecutor<Projet>  {
	
	@Query("select p from Projet p where  p.cloture = :y order by p.client")
	public Page<Projet> getProjets(@Param("y") Boolean cloturer,Pageable pageable);
	
	
	@Query("select p from Projet p where p.cloture = :y and (p.bu = :z or p.bu = :u) order by p.client")
	public Collection<Projet> getProjetsByBu(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2);
	
	@Query("select p from Projet p where p.cloture = :y and (p.bu = :z or p.bu = :u) and (p.commercial = :g or p.chefProjet = :g) order by p.client")
	public Collection<Projet> getProjetsByBuAndCommercial(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("g")String commercial);
	
	@Query("select p from Projet p where p.cloture = :y and (p.bu = :z or p.bu = :u) and (p.commercial = :g or p.chefProjet = :g) order by p.client")
	public Collection<Projet> getProjetsByBuAndChefProjet(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("g")String chefProjet);

	@Query("select p from Projet p where p.cloture = :y and p.statut = :z  order by p.client")
	public Collection<Projet> getProjetsByStatut(@Param("y") Boolean cloturer,@Param("z")String statut);

	@Query("select p from Projet p where p.cloture = :y and p.statut = :z and (p.commercial = :g or p.chefProjet = :g)  order by p.client")
	public Collection<Projet> getProjetsByStatutAndCommercial(@Param("y") Boolean cloturer,@Param("z")String statut,@Param("g")String commercial);

	@Query("select p from Projet p where p.cloture = :y and p.statut = :z and (p.commercial = :g or p.chefProjet = :g)  order by p.client")
	public Collection<Projet> getProjetsByStatutAndChefProjet(@Param("y") Boolean cloturer,@Param("z")String statut,@Param("g")String chefProjet);

	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is null  order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNull(@Param("y") Boolean cloturer);

	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is null and p.type='DEP' order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNullDep(@Param("y") Boolean cloturer);
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is null and p.commercial=:z  order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndCommercial(@Param("y") Boolean cloturer,@Param("z")String commercial);
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is null and p.commercial=:z and p.type='DEP'  order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndCommercialDep(@Param("y") Boolean cloturer,@Param("z")String commercial);
	
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is not null order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNull(@Param("y") Boolean cloturer);
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is not null and p.type='DEP' order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNullDep(@Param("y") Boolean cloturer);

	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is not null and p.commercial=:z order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndCommercial(@Param("y") Boolean cloturer,@Param("z")String commercial);

	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is not null and p.commercial=:z and p.type='DEP' order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndCommercialDep(@Param("y") Boolean cloturer,@Param("z")String commercial);

	@Query("select p from Projet p where p.cloture = :y and  (p.bu = :z or p.bu = :u) and p.statut =:t order by p.client") 
	public Collection<Projet> getProjetsByBuAndStatut(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("t")String statut);
	
	@Query("select p from Projet p where p.cloture = :y and  (p.bu = :z or p.bu = :u) and p.statut =:t and (p.commercial = :g or p.chefProjet = :g) order by p.client") 
	public Collection<Projet> getProjetsByBuAndStatutAndCommercial(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("t")String statut,@Param("g")String commercial);
	
	
	@Query("select p from Projet p where p.cloture = :y and  (p.bu = :z or p.bu = :u) and p.statut =:t and (p.commercial = :g or p.chefProjet = :g) order by p.client") 
	public Collection<Projet> getProjetsByBuAndStatutAndchefProjet(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("t")String statut,@Param("g")String chefProjet);
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is not null and (p.bu =:x or p.bu =:z) order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndBu(@Param("y") Boolean cloturer,@Param("x")String bu1,@Param("z")String bu2);
	

	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is null and (p.bu =:x or p.bu =:z) order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndBu(@Param("y") Boolean cloturer, @Param("x") String bu1,@Param("z") String bu2);
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is null and p.statut = :x order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndStatut(@Param("y") Boolean cloturer,@Param("x")String statut);
	
	@Query("select p from Projet p where p.cloture = :y  and p.chefProjet is not null and p.statut =:t  order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndStatut(@Param("y") Boolean cloturer,@Param("t")String statut);
	
	@Query("select p from Projet p where p.cloture = :y and  (p.bu = :z or p.bu = :u) and p.chefProjet is not null and p.statut =:t order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndBuAndStatut(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("t")String statut);
	
	@Query("select p from Projet p where p.cloture = :y and  (p.bu = :z or p.bu = :u) and p.chefProjet is null and p.statut =:t  order by p.client") 
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndBuAndStatut(@Param("y") Boolean cloturer,@Param("z")String bu1,@Param("u")String bu2,@Param("t")String statut);
	
	
	@Query("select p from Projet p  order by p.client")
	public Collection<Projet> getAllProjets();
	
	@Query("select p from Projet p where p.cloture = :y  and (p.commercial =:g or p.chefProjet =:g)  order by p.client")
	public Collection<Projet> getAllProjetsByCommercialOrChefProjet(@Param("y") Boolean cloturer,@Param("g") String commercialOrChefProjet);
	
	
	@Query("select p from Projet p where p.cloture = :y order by p.client")
	public Collection<Projet> getProjets(@Param("y") Boolean cloturer);
	

	//@Query(value="select p from Projet p where p.etatProjet.id = 1 and p.cloture = :y and p.dateCmd >= :z @Param("z")Date dateCmd")
	@Query(value="select p from Projet p where p.etatProjet.id = 1 and p.cloture = :y and p.dateCmd >= :z ")
	public List<Projet> findAllProjetsByDateSup(@Param("y") Boolean cloturer,@Param("z")Date dateCmd );
	
	@Query(value="select p from Projet p where p.etatProjet.id = 1 and p.cloture = :y and p.dateCmd <= :z ")
	public List<Projet> findAllProjetsByDateInf(@Param("y") Boolean cloturer,@Param("z")Date dateCmd );
	
	@Query(value="select p from Projet p where p.etatProjet.id = 1")
	public List<Projet> findAllProjetsByDate();
	
	
	@Query(value="select distinct p.client from Projet p where p.client !='' and p.client IS NOT NULL")
	public List<String> getDistinctClient();
	
	@Query(value="select distinct p.commercial from Projet p where p.commercial !='' and p.commercial IS NOT NULL")
	public List<String> getDistinctCommercial();
	
	@Query(value="select distinct p.chefProjet from Projet p where p.chefProjet !='' and p.chefProjet IS NOT NULL")
	public List<String> getDistinctChefProjet();
	
	@Modifying
	@Query("update Projet p set p.cloture = :statut,p.cloturedByUser=:cloturedByUser,p.decloturedByUser = :decloturedByUser where p.codeProjet = :codeProjet")
	public int updateStatutProjet(@Param("statut") boolean statut,@Param("cloturedByUser") boolean cloturedByUser,@Param("decloturedByUser") boolean decloturedByUser, @Param("codeProjet") String codeProjet);
	
	
	@Modifying
	@Query("update Projet p set p.cloture = :statut,p.cloturedByUser=:cloturedByUser,p.decloturedByUser = :decloturedByUser,p.restAlivrer=:ral,p.livrerNonFacture=:lnf,p.facturation=:facturation,p.livreFacturePayer=:lfp where p.codeProjet = :codeProjet")
	
	
	public int updateStatutProjetMontant(@Param("statut") boolean statut,@Param("cloturedByUser") boolean cloturedByUser,@Param("decloturedByUser") boolean decloturedByUser,@Param("facturation") Double facturation,@Param("lnf") Double lnf,@Param("lfp") Double lfp,@Param("ral") Double ral, @Param("codeProjet") String codeProjet);
	
	@Query("select p from Projet p where p.type='DEP' order by p.client")
	public Collection<Projet> getProjetsDep();
	
	@Modifying
	@Query(value="update Projet p set p.flag='Green' where p.codeProjet = :project")
	public void updateFlag(@Param("project") String project);
	
	/*@Query(value="select p from Projet p where p.etatProjet.id = 1 and p.cloture = :y and p.commercial like :z or p.chefProjet like :z ")
	public List<Projet> findAllProjetsByCommercialOrChefProjet(@Param("y") Boolean cloturer,@Param("z")String filtre )*/
	
	
}
>>>>>>> munisysRepo/main
