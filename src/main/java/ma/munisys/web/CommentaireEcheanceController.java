package ma.munisys.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.munisys.dao.CommentaireEcheanceRepository;
import ma.munisys.entities.CommentaireEcheance;

@RestController
@CrossOrigin(origins = "*")
public class CommentaireEcheanceController {

	@Autowired
	private CommentaireEcheanceRepository commentaireEcheanceRepository;
	
	public CommentaireEcheanceController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/getAllCommentaireEcheance",method=RequestMethod.GET)
	public Collection<CommentaireEcheance> getAllCommentaireEcheance() {
	
		return commentaireEcheanceRepository.findAll();
	}

	public CommentaireEcheanceRepository getCommentaireEcheanceRepository() {
		return commentaireEcheanceRepository;
	}

	public void setCommentaireEcheanceRepository(CommentaireEcheanceRepository commentaireEcheanceRepository) {
		this.commentaireEcheanceRepository = commentaireEcheanceRepository;
	}
	
	
	
}
