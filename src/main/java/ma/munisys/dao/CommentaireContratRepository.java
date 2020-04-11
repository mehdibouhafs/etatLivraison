package ma.munisys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.munisys.entities.Authorisation;
import ma.munisys.entities.Commentaire;
import ma.munisys.entities.CommentaireContrat;

public interface CommentaireContratRepository extends JpaRepository<CommentaireContrat, Long> {
	
	

}
