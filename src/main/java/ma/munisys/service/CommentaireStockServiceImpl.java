package ma.munisys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.munisys.dao.CommentaireStockRepository;
import ma.munisys.entities.CommentaireStock;

@Service
@Transactional 
public class CommentaireStockServiceImpl implements CommentaireStockService {

	
	@Autowired 
	CommentaireStockRepository commentaireStockRepo;
	
	@Override
	public void deleteCommentaire(Long c) {
		
		 commentaireStockRepo.deleteById2(c);;
	}
	
	@Override
	public CommentaireStock saveCommentProjet(CommentaireStock c) {
		return commentaireStockRepo.save(c);
	}

	
	
}
