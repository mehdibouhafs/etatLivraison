package ma.munisys.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import ma.munisys.entities.Projet;



public interface ProjetRepository extends JpaRepository<Projet, Long> {
	
	
}
