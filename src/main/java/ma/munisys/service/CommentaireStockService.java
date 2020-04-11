package ma.munisys.service;

import ma.munisys.entities.Commentaire;
import  ma.munisys.entities.CommentaireStock;;
public interface CommentaireStockService {

	
	public void deleteCommentaire(Long c);
	public CommentaireStock saveCommentProjet(CommentaireStock c);

}
