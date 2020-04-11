package ma.munisys.web;

import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.munisys.EtatLvSvcApplication;
import ma.munisys.dao.ProduitRepository;
import ma.munisys.dto.Analyse;
import ma.munisys.entities.Commentaire;
import ma.munisys.entities.CommentaireProduit;
import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.Produit;
import ma.munisys.entities.Projet;
import ma.munisys.entities.Reunion;
import ma.munisys.service.EtatProjetService;
import ma.munisys.service.ProduitService;
import ma.munisys.service.ReunionService;

@RestController
@CrossOrigin(origins = "*")
public class ProduitController {
	
	private static final Logger LOGGER = LogManager.getLogger(ProduitController.class);

	@Autowired
	private ProduitService produitService;
	
	@Autowired
	private EtatProjetService etatProjetService;
	
	@Autowired
	private ProduitRepository produitRepository;

	@RequestMapping(value="/getAllStockByFiltre",method=RequestMethod.GET)
	public Collection<Produit> getAllStockByFiltre( @RequestParam("nature") String nature,@RequestParam("sousNature") String sousNature,@RequestParam("domaine") String domaine,@RequestParam("sousDomaine") String sousDomaine,@RequestParam("numLot")String numLot,@RequestParam("client")String client,@RequestParam("nomMagasin")String nomMagasin) {
		return produitService.getProduitByPredicate(nature, sousNature, domaine, sousDomaine, numLot, client, nomMagasin);
	}
	
	@RequestMapping(value="/getDistinctLot",method=RequestMethod.GET)
	public Collection<String> getAllLot() {
		return produitRepository.getDistinctLot();
	}
	
	
	
	@RequestMapping(value = "/produits", method = RequestMethod.PUT)
	public Produit updateProduits(@RequestBody Produit produit) {

		
		if(produit.getCommentaires()!=null)
		for (CommentaireProduit c : produit.getCommentaires()) {
			c.setProduit(produit);
		}

		 return produitService.saveProduit(produit.getId(), produit);
		
		
		
	}



	@RequestMapping(value="/getAllStock",method=RequestMethod.GET)
	public Collection<Produit> getDistinctClient() {
		return produitService.getAllProduitsInStock();
	}

	

	@RequestMapping(value = "/refreshProduit", method = RequestMethod.GET)
	public String refreshProduits() {
		etatProjetService.loadProjetsFromSap();
		produitService.loadProduitFromSap();
		return "{'statut':'ok'}";
	}


	


	
	
	

}
