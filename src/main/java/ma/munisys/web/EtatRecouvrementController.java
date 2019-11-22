package ma.munisys.web;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ma.munisys.EtatLvSvcApplication;
import ma.munisys.entities.Commentaire;
import ma.munisys.entities.EtatRecouvrement;
import ma.munisys.entities.Document;
import ma.munisys.service.EtatRecouvrementService;


@RestController
@CrossOrigin(origins="*")
public class EtatRecouvrementController {
	
	@Autowired
	private EtatRecouvrementService etatRecouvrementService;
	
	@RequestMapping(value="/getDocumentsByCommercialOrChefProjet",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByCommercialOrChefProjet(@RequestParam(name="idEtatRecouvrement",defaultValue="1")Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false") Boolean cloturer,
			 @RequestParam(name="commercialOrChefProjet") String commercialOrChefProjet) {
		return etatRecouvrementService.getDocumentsByCommercialOrChefProjet(idEtatFacture, cloturer,
				commercialOrChefProjet);
	}

	@RequestMapping(value="/getDocumentsByChargeRecouvrement",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByChargeRecouvrement(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false")  Boolean cloturer,@RequestParam(name="chargeRecouvrement")  String chargeRecouvrement) {
		return etatRecouvrementService.getDocumentsByChargeRecouvrement(idEtatFacture, cloturer, chargeRecouvrement);
	}
		
	@RequestMapping(value="/getDocumentsByCodeCommercial",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByCommercial(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false")  Boolean cloturer,@RequestParam(name="codeCommercial")  String codeCommercial) {
		return etatRecouvrementService.getDocumentsByCommercial(idEtatFacture, cloturer, codeCommercial);
	}
	
	@RequestMapping(value="/getDocumentsByChefProjet",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByChefProjet(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false")  Boolean cloturer,@RequestParam(name="chefProjet")  String chefProjet) {
		return etatRecouvrementService.getDocumentsByChefProjet(idEtatFacture, cloturer,chefProjet);
	}

	@RequestMapping(value="/getDocumentsByCodeProjet",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByCodeProjet(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false")  Boolean cloturer,@RequestParam(name="codeProjet")  String codeProjet) {
		return etatRecouvrementService.getDocumentsByCodeProjet(idEtatFacture, cloturer,codeProjet);
	}
	
	@RequestMapping(value="/getDocumentsByCodeClient",method=RequestMethod.GET)
	public Collection<Document> getDocumentsByClient(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatFacture,@RequestParam(name="cloturer",defaultValue="false")  Boolean cloturer,@RequestParam(name="codeClient")  String codeClient) {
		return etatRecouvrementService.getDocumentsByClient(idEtatFacture, cloturer, codeClient);
	}

	@RequestMapping(value="/getEtatRecouvrement",method=RequestMethod.GET)
	public EtatRecouvrement getEtatProjet() {
		return etatRecouvrementService.getEtatRecouvrement();
	}
	
	@RequestMapping(value="/getPageDocuments",method=RequestMethod.GET)
	public Page<Document> getDocuments(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatRecouvrement,@RequestParam(name="cloturer",defaultValue="false") Boolean cloturer, @RequestParam(name="page",defaultValue="0")int page,@RequestParam(name="size",defaultValue="5")int size) {
		return  etatRecouvrementService.getDocumentsFromEtatRecouvrement(idEtatRecouvrement,cloturer,page, size);
	}
	
	@RequestMapping(value="/getDocuments",method=RequestMethod.GET)
	public Collection<Document> getDocumentsWithoutPagination(@RequestParam(name="idEtatRecouvrement",defaultValue="1") Long idEtatRecouvrement,@RequestParam(name="cloturer",defaultValue="false") Boolean cloturer) {
		return  etatRecouvrementService.getDocumentsFromEtatRecouvrement(idEtatRecouvrement,cloturer);
	}
	
	@RequestMapping(value="/documents",method=RequestMethod.PUT)
	public Document updateDocuments(@RequestBody Document document) {
		
		EtatRecouvrement etatRecouvrement = new EtatRecouvrement();
		etatRecouvrement.setId(1L);
		document.setEtatRecouvrement(etatRecouvrement);
		
		for(Commentaire c : document.getCommentaires()) {
			c.setDocument(document);
		}
		
		return etatRecouvrementService.updateDocument(document.getNumPiece(), document);
	}
	
	
	@RequestMapping(value="/refreshDocuments",method=RequestMethod.GET)
	public String refreshProjects() {
		EtatLvSvcApplication.loadDocumentsFromSap();
		return  "ok";
	}
	


	
	
	
}
