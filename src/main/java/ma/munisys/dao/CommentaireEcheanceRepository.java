package ma.munisys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.munisys.entities.Authorisation;
import ma.munisys.entities.Commentaire;
import ma.munisys.entities.CommentaireEcheance;

public interface CommentaireEcheanceRepository extends JpaRepository<CommentaireEcheance, Long> {
	
	

}
