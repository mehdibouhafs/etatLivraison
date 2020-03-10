package ma.munisys.service;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import ma.munisys.dto.ContratSearch;
import ma.munisys.entities.CommentaireContrat;
import ma.munisys.entities.Contrat;


public interface ContratService {
	
	public Contrat saveContrat(Contrat contrat);
	
	public  CompletableFuture<String> loadContratFromSap() ;
	
	public  CompletableFuture<String> loadContratPieceSap() ;

	public Contrat addCommentaires(Long numContrat,List<CommentaireContrat> commentaires);

	
	
	public Collection<Contrat> getContratByPredicate(ContratSearch contratSearch);
	
	public Collection<Contrat> getAllContrats();
	
	
	

}
