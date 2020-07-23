package ma.munisys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import ma.munisys.dao.CommentaireRepository;
import ma.munisys.dto.CommentaireSocket;
import ma.munisys.entities.Commentaire;
import ma.munisys.entities.Projet;


@Controller
public class CommentsController {
	
	@Autowired
	private CommentaireRepository commentaireRepository;
	
	@MessageMapping("/addCommentaireProjet")
	  @SendTo("/topic/addCommentProjet")
	  public CommentaireSocket addCommentaire(CommentaireSocket commentaire) throws Exception {
	    //Thread.sleep(1000); // simulated delay
		
		Commentaire commToAdd = new Commentaire();
		commToAdd.setContent(commentaire.getContent());
		commToAdd.setDate(commentaire.getDate());
		commToAdd.setEmployer(commentaire.getEmployer());
		commToAdd.setUser(commentaire.getUser());
		Projet p = new Projet();
		p.setCodeProjet(commentaire.getJoinId());
		commToAdd.setProjet(p);
		Commentaire res = this.commentaireRepository.save(commToAdd);
		commentaire.setId(res.getId());
	    return commentaire;
	  }
	
	@MessageMapping("/deleteCommentaireProjet")
	  @SendTo("/topic/deleteCommentProjet")
	  public CommentaireSocket greeting(CommentaireSocket commentaire) throws Exception {
	    //Thread.sleep(1000); // simulated delay
		Commentaire c = new Commentaire();
		c.setId(commentaire.getId());
		this.commentaireRepository.delete(c);
	    return commentaire;
	  }
	
	

}
