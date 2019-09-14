package ma.munisys.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import ma.munisys.entities.EtatProjet;



public interface EtatProjetRepository extends JpaRepository<EtatProjet, Long> {
	
	
}
