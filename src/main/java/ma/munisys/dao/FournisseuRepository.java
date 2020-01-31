package ma.munisys.dao;


import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.munisys.entities.Fournisseur;



public interface FournisseuRepository extends JpaRepository<Fournisseur, String> {
	
	
	@Query("SELECT e FROM Fournisseur e  order by e.name asc")
	public Collection<Fournisseur> getAllFournisseurs();
	
	
	


}
