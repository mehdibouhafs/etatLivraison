package ma.munisys.service;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;

import ma.munisys.dto.ContratSearch;
import ma.munisys.entities.CommentaireContrat;
import ma.munisys.entities.Contrat;


public interface ContratService {
	
	public Contrat saveContrat(Contrat contrat);
	
	public  void loadContratFromSap() ;
	
	public  void loadContratPieceSap() ;

	public CommentaireContrat addCommentaire(Long numContrat,CommentaireContrat commentaire);
	
	public void deleteCommentaire(Long idCommentaire);

	public Collection<Contrat> getContratByPredicate(ContratSearch contratSearch);
	
	public Collection<Contrat> getAllContrats();
	
	public Page<Contrat> getAllContrats(int page, int size);
	
	public Contrat getContrat(Long numContrat);
	
	
	public Page<Contrat> getContratByPredicate2(ContratSearch contratSearch,int page,int size,String sortBy,String sortType);
	
	public Collection<Contrat> getAllContratsWithPredicate(ContratSearch contratSearch);
	

}
