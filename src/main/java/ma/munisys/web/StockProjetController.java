package ma.munisys.web;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.munisys.entities.AppUser;
import ma.munisys.entities.Commentaire;
import ma.munisys.entities.CommentaireStock;
import ma.munisys.entities.Projet;
import ma.munisys.entities.StockProjet;
import ma.munisys.service.CommentaireStockService;
import ma.munisys.service.StockProjetService;


@RestController
@CrossOrigin(origins = "*")
public class StockProjetController {

	
	
	@Autowired
	private StockProjetService stockProjetService;
	
	@Autowired
	private CommentaireStockService commentaireStockService;
	
	
	@RequestMapping(value="/getAllStockProjetByFiltre",method=RequestMethod.GET)
	public Collection<StockProjet> getStockProjetByFiltre( @RequestParam("numLot") String numLot,@RequestParam("client") String client,@RequestParam("annee")String annee,@RequestParam("magasin")String magasin,@RequestParam("com")String com){
	
		System.out.println("NIVEAU 1" + magasin);
		return stockProjetService.getStockProjetByFiltre(numLot, client, annee, magasin,com);
	}
	
	@RequestMapping(value = "/getStockParProjet", method = RequestMethod.GET)
	public Collection<StockProjet>  getStockParProjet(){
	
		return stockProjetService.getStockParProjet();
	}
	
	@RequestMapping(value = "/getMontantByNature", method = RequestMethod.GET)
	public List<String> getMontantByNature(@RequestParam("numLot") String numLot,@RequestParam("magasin") String magasin){
		
		return stockProjetService.getMontantByNature(numLot,magasin);
	}
	
	@RequestMapping(value = "/commentaireDelete", method = RequestMethod.POST)
	public void  deleteCommentaire(@RequestBody Long c,@RequestParam("id") Long id){
		System.out.println("COM TO DELETE "+id);
		 commentaireStockService.deleteCommentaire(id);
			System.out.println("DELETED SUCCESFULLY "+id);

	}
	
	@RequestMapping(value = "/saveCommentProjet", method = RequestMethod.POST)
	public CommentaireStock saveCommentProjet(@RequestBody CommentaireStock c,@RequestParam("projet") String projet,@RequestParam("id") Long id,@RequestParam("user") String user) {
		
			Projet p = new Projet();
			p.setCodeProjet(projet);
			c.setProjet_code_projet(p);
			System.out.println("COMMENTAIRE "+id+" user "+user);
			
			AppUser u = new AppUser();
			u.setUsername(user);
			c.setUser_username(u);
			
			StockProjet s = new StockProjet(id);
			c.setStock(s);
			
	
			
			
			return commentaireStockService.saveCommentProjet(c);
		}
	
	
}
