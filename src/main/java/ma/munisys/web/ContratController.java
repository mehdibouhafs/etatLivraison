package ma.munisys.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ma.munisys.dao.ContratRepository;
import ma.munisys.dao.EcheanceRepository;
import ma.munisys.dao.FactureEcheanceRepository;
import ma.munisys.dao.FactureRepository;
import ma.munisys.dto.ContratSearch;
import ma.munisys.entities.CommentaireContrat;
import ma.munisys.entities.Contrat;
import ma.munisys.entities.Echeance;
import ma.munisys.entities.FactureEcheance;
import ma.munisys.service.CommandeFournisseurService;
import ma.munisys.service.ContratService;
import ma.munisys.service.EcheanceService;
import ma.munisys.service.EcheanceServiceImpl;
import ma.munisys.service.FactureService;


@RestController
@CrossOrigin(origins = "*")
public class ContratController {
	
	private static final Logger LOGGER = LogManager.getLogger(ContratController.class);
	

	@Autowired
	private ContratService contratService;
	@Autowired
	private FactureService factureService;
	
	@Autowired
	private CommandeFournisseurService commandeFournisseurService;
	
	
	@Autowired
	private ContratRepository contratRepository;
	
	
	@Autowired
	private FactureRepository factureRepository;
	
	@Autowired
	private FactureEcheanceRepository factureEcheanceRepository;
	
	@Autowired
	private EcheanceRepository echeanceRepository;
	
	@Autowired
	private EcheanceService echeanceService;
	
	
	public ContratController() {
		
	}
	
	@RequestMapping(value="/getAllContrat",method=RequestMethod.GET)
	public Collection<Contrat> getAllContrats() {
		
	
		return contratService.getAllContrats();
	}
	
	@RequestMapping(value="/getAllEcheances",method=RequestMethod.GET)
	public Collection<Echeance> getAllEcheances() {

		return echeanceRepository.getEcheance(1L);
	}
	
	@RequestMapping(value="/getAllFactureEcheances",method=RequestMethod.GET)
	public Collection<FactureEcheance> getAllFactureEcheances() {
		
		return factureEcheanceRepository.getFactureEcheance(480636L);
	}
	
	@RequestMapping(value = "/refreshContrats", method = RequestMethod.GET)
	public String refreshProjects() {
		//commandeFournisseurService.loadCommandeFournisseurFromSap();
		
		System.out.println("start refresh");
		CompletableFuture<String> contrats = contratService.loadContratFromSap();
		CompletableFuture<String> pieces = contratService.loadContratPieceSap();
		CompletableFuture<String> commandes =commandeFournisseurService.loadCommandeFournisseurFromSap();
		CompletableFuture<String> factures = factureService.loadFactureFromSap();
		
		CompletableFuture.allOf(contrats,pieces,commandes,factures).join();
		System.out.println("end refresh");
		
		
		return "{'statut':'ok'}";
	}
	
	@RequestMapping(value = "/addCommentaires/{numContrat}", method = RequestMethod.PUT)
	public Contrat updateContrat(@PathVariable("numContrat") final Long numContrat,@RequestBody List<CommentaireContrat> commentaires) {

		

		return contratService.addCommentaires(numContrat,commentaires);
	}
	
	@RequestMapping(value = "/contratsFilter", method = RequestMethod.GET)
	public Collection<Contrat> getContratByPredicate(
			@RequestParam(name = "numMarche", required = false) String numMarche,
			@RequestParam(name = "pilote", required = false) String pilote,
			@RequestParam(name = "nomPartenaire", required = false) String nomPartenaire, 
			@RequestParam(name = "sousTraiter", required = false) Boolean sousTraiter){
		
		ContratSearch cs = new ContratSearch();
		cs.setNomPartenaire(nomPartenaire);
		cs.setPilote(pilote);
		cs.setSousTraiter(sousTraiter);
		cs.setNumMarche(numMarche);
		
		return contratService.getContratByPredicate(cs);
		
	}
	
	
	@RequestMapping(value = "/updateEcheance", method = RequestMethod.PUT)
	public Echeance updateEcheance(
			@RequestBody Echeance echeance){
	
		return echeanceService.updateEcheance(echeance.getId(), echeance.getCommentaire());
		
	}
	
	@PostMapping("/exportPiece")
	@ResponseBody
	public void exportPiece(HttpServletResponse response,@RequestBody String fullPath)  {
		    
		//URI uri =new URL("file://130.24.31.12//attachments//SDS_AV%20N1_CT%20N-MUIS-SDS%2001-180001.pdf").toURI();
		System.out.println("fulpath " +fullPath.replace("\\", "/").replace(" ", "%20"));
		try {
			URI uri =new URL("file:"+fullPath.replace("\\", "/").replace(" ", "%20")).toURI();
			Resource file = new UrlResource(uri);
		      
		      response.setContentType("application/pdf");
		      response.setHeader("Content-disposition", "attachment; filename=\"" + file.getFilename() + "\"");

		      OutputStream out;
			out = response.getOutputStream();
			
			
			 FileInputStream in = new FileInputStream(file.getFile());

		      // copy from in to out
		      IOUtils.copy(in,out);

		      out.close();
		      in.close();
		      
		} catch (Exception e) {
			response.setStatus(404);
			response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE);
			e.printStackTrace();
		}
	}
	

}
