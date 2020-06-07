package ma.munisys.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

import ma.munisys.dao.CommandeFournisseurRepository;
import ma.munisys.dao.CommandeFournisseurSpecification;
import ma.munisys.dao.CommentaireContratRepository;
import ma.munisys.dao.ContratRepository;
import ma.munisys.dao.EcheanceRepository;
import ma.munisys.dao.FactureEcheanceRepository;
import ma.munisys.dao.FactureRepository;
import ma.munisys.dao.PieceRepository;
import ma.munisys.dto.CommandeFournisseurSearch;
import ma.munisys.dto.ContratSearch;
import ma.munisys.dto.StatisticContrat;
import ma.munisys.entities.CommandeFournisseur;
import ma.munisys.entities.CommentaireContrat;
import ma.munisys.entities.Contrat;
import ma.munisys.entities.Echeance;
import ma.munisys.entities.FactureEcheance;
import ma.munisys.entities.Piece;
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
	private CommandeFournisseurRepository commandeFournisseurRepository;
	@Autowired
	private ContratRepository contratRepository;

	@Autowired
	private EcheanceRepository echeanceRepository;

	@Autowired
	private EcheanceService echeanceService;

	@Autowired
	private PieceRepository pieceRepository;

	@Autowired
	private CommentaireContratRepository commentaireContratRepository;

	public ContratController() {

	}

	@RequestMapping(value = "/getAllContrat", method = RequestMethod.GET)
	public Collection<Contrat> getAllContrats() {

		return contratService.getAllContrats();
	}

	@RequestMapping(value = "/getContrat", method = RequestMethod.GET)
	public Contrat getContrat(@RequestParam(name = "numContrat") long numContrat) {

		return contratService.getContrat(numContrat);
	}

	@RequestMapping(value = "/getPieces", method = RequestMethod.GET)
	public Page<Piece> getPieces(@RequestParam(name = "numContrat") long numContrat,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "sortType", required = false) String sortType) {
		if (sortBy != null) {

			if ("asc".equals(sortType)) {
				return pieceRepository.getPieces(numContrat,
						PageRequest.of(page - 1, size, Sort.by(sortBy).ascending()));

			} else {
				return pieceRepository.getPieces(numContrat,
						PageRequest.of(page - 1, size, Sort.by(sortBy).descending()));
			}

		} else {
			return pieceRepository.getPieces(numContrat, PageRequest.of(page - 1, size));
		}
	}

	@RequestMapping(value = "/getEcheance", method = RequestMethod.GET)
	public Page<Echeance> getEcheance(@RequestParam(name = "numContrat") long numContrat,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "sortType", required = false) String sortType) {
		
		

		return echeanceService.getEcheance(numContrat, page-1, size,sortBy,sortType);
		
	}

	@RequestMapping(value = "/getFactureEcheance", method = RequestMethod.GET)
	public Page<FactureEcheance> getFactureEcheance(@RequestParam(name = "numContrat") long numContrat,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "sortType", required = false) String sortType) {

		return factureService.getFactureEcheance(numContrat, page-1, size,sortBy,sortType);
	}

	@RequestMapping(value = "/getCommandeFournisseur", method = RequestMethod.GET)
	public Page<CommandeFournisseur> getCommandeFournisseur(@RequestParam(name = "mc", required = false) String mc,
			@RequestParam(name = "numContrat") long numContrat,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "sortType", required = false) String sortType) {

		CommandeFournisseurSearch cfs = new CommandeFournisseurSearch();
		cfs.setMotCle(mc);
		cfs.setNumContrat(numContrat);
		CommandeFournisseurSpecification cfSpec = new CommandeFournisseurSpecification(cfs);

		if (sortBy != null) {

			if ("asc".equals(sortType)) {
				return commandeFournisseurRepository.findAll(cfSpec, PageRequest.of(page - 1, size,Sort.by(sortBy).ascending()));

			} else {
				return commandeFournisseurRepository.findAll(cfSpec, PageRequest.of(page - 1, size,Sort.by(sortBy).descending()));
			}

		} else {
			return commandeFournisseurRepository.findAll(cfSpec, PageRequest.of(page - 1, size));
		}

	}

	@RequestMapping(value = "/getAllContrats", method = RequestMethod.GET)
	public Page<Contrat> getAllContrats(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		return contratService.getAllContrats(page, size);
	}

	@RequestMapping(value = "/getAllEcheancesForContrat", method = RequestMethod.GET)
	public Collection<Echeance> getAllEcheances(@RequestParam(name = "numContrat") Long numContrat) {

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		
		return echeanceRepository.getAllEcheancesFromContrat(numContrat,year);
	}

	@RequestMapping(value = "/refreshContrats", method = RequestMethod.GET)
	public String refreshProjects() {

		System.out.println("start refresh");

		contratService.loadContratFromSap();

		contratService.loadContratPieceSap();

		factureService.loadFactureFromSap();

		commandeFournisseurService.loadCommandeFournisseurFromSap();

		System.out.println("end refresh");

		return "{'statut':'ok'}";
	}

	@RequestMapping(value = "/addCommentaire/{numContrat}", method = RequestMethod.PUT)
	public CommentaireContrat addCommentaire(@PathVariable("numContrat") final Long numContrat,
			@RequestBody CommentaireContrat commentaire) {
		return contratService.addCommentaire(numContrat, commentaire);
	}
	
	@RequestMapping(value = "/deleteCommentaire/{idCommentaire}", method = RequestMethod.DELETE)
	public void addCommentaire(@PathVariable("idCommentaire") final Long idCommentaire) {
		 contratService.deleteCommentaire(idCommentaire);
	}
	
	

	/*
	 * @RequestMapping(value = "/deleteCommentaire/{idCommentaire}", method =
	 * RequestMethod.DELETE) public void
	 * deleteCommentaire(@PathVariable("idCommentaire") final Long idCommentaire) {
	 * commentaireContratRepository.deleteById(idCommentaire); }
	 */

	@RequestMapping(value = "/contratsFilter", method = RequestMethod.GET)
	public Collection<Contrat> getContratByPredicate(
			@RequestParam(name = "numMarche", required = false) String numMarche,
			@RequestParam(name = "pilote", required = false) String pilote,
			@RequestParam(name = "nomPartenaire", required = false) String nomPartenaire,
			@RequestParam(name = "sousTraiter", required = false) Boolean sousTraiter) {

		ContratSearch cs = new ContratSearch();
		cs.setNomPartenaire(nomPartenaire);
		cs.setPilote(pilote);
		cs.setSousTraiter(sousTraiter);
		cs.setNumMarche(numMarche);

		return contratService.getContratByPredicate(cs);

	}

	@RequestMapping(value = "/getAllClients", method = RequestMethod.GET)
	public Collection<String> getAllClients() {

		return contratRepository.getDistinctClients();

	}

	@RequestMapping(value = "/getCommentairesContrat", method = RequestMethod.GET)
	public Page<CommentaireContrat> getCommentairesContrat(@RequestParam(name = "numContrat") Long numContrat,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {

		return commentaireContratRepository.getCommentairesContrat(numContrat, PageRequest.of(page - 1, size));

	}

	@RequestMapping(value = "/getAllNumMarches", method = RequestMethod.GET)
	public Collection<String> getAllNumMarches() {

		return contratRepository.getDistinctNumMarche();

	}

	@RequestMapping(value = "/getPilotes", method = RequestMethod.GET)
	public Collection<String> getAllPilotes() {

		return contratRepository.getDistinctPilotes();

	}

	@RequestMapping(value = "/contratsFilter2", method = RequestMethod.GET)
	public Page<Contrat> getContratByPredicate2(@RequestParam(name = "mc", required = false) String motCle,
			@RequestParam(name = "numMarche", required = false) String numMarche,
			@RequestParam(name = "pilote", required = false) String pilote,
			@RequestParam(name = "nomPartenaire", required = false) String nomPartenaire,
			@RequestParam(name = "sousTraiter", required = false) Boolean sousTraiter,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "sortType", required = false) String sortType) {

		ContratSearch cs = new ContratSearch();
		cs.setMotCle(motCle);
		cs.setNomPartenaire(nomPartenaire);
		cs.setPilote(pilote);
		cs.setSousTraiter(sousTraiter);
		cs.setNumMarche(numMarche);

		return contratService.getContratByPredicate2(cs, page, size, sortBy, sortType);

	}

	@RequestMapping(value = "/getStatisticsContrat", method = RequestMethod.GET)
	public StatisticContrat getStatisticsContrat(@RequestParam(name = "mc", required = false) String motCle,
			@RequestParam(name = "numMarche", required = false) String numMarche,
			@RequestParam(name = "pilote", required = false) String pilote,
			@RequestParam(name = "nomPartenaire", required = false) String nomPartenaire,
			@RequestParam(name = "sousTraiter", required = false) Boolean sousTraiter) {

		ContratSearch cs = new ContratSearch();
		cs.setMotCle(motCle);
		cs.setNomPartenaire(nomPartenaire);
		cs.setPilote(pilote);
		cs.setSousTraiter(sousTraiter);
		cs.setNumMarche(numMarche);

		Collection<Contrat> contrats = contratService.getAllContratsWithPredicate(cs);

		StatisticContrat sc = new StatisticContrat();

		for (Contrat c : contrats) {
			sc.setTotalMontantAnnuel(sc.getTotalMontantAnnuel() + c.getMontantContrat());
			sc.setTotalMontantFactureAn(sc.getTotalMontantFactureAn() + c.getMontantFactureAn());
			sc.setTotalMontantAFactureAn(sc.getTotalMontantAFactureAn() + c.getMontantRestFactureAn());
			sc.setTotalMontantProvisionFactureAn(
					sc.getTotalMontantProvisionFactureAn() + c.getMontantProvisionFactureInfAnneeEnCours());
			sc.setTotalMontantProvisionAFactureAn(
					sc.getTotalMontantProvisionAFactureAn() + c.getMontantProvisionAFactureInfAnneeEnCours());
		}
		return sc;

	}

	@RequestMapping(value = "/updateEcheance", method = RequestMethod.PUT)
	public Echeance updateEcheance(@RequestBody Echeance echeance) {

		return echeanceService.updateEcheance(echeance.getId(), echeance.getCommentaire().getId());

	}

	@RequestMapping(value = "/editEcheance/{numContrat}", method = RequestMethod.PUT)
	public int updateEcheance2(@PathVariable("numContrat") final Long numContrat, @RequestBody Echeance echeance) {
		 return echeanceService.addNewContratModel(numContrat, echeance);

	}
	
	@RequestMapping(value = "/addEcheance/{numContrat}", method = RequestMethod.POST)
	public Echeance addEcheanceByUser(@PathVariable("numContrat") final Long numContrat, @RequestBody Echeance echeance) {
		 return echeanceService.addNewEcheanceByUser(numContrat, echeance);

	}

	@RequestMapping(value = "/deleteEcheance/{idEcheance}", method = RequestMethod.DELETE)
	public void deleteEcheance(@PathVariable("idEcheance") String idEcheance) {
		echeanceService.deleteEcheance(Long.parseLong(idEcheance));

	}

	@RequestMapping(value = "/editFactureEcheance/{numContrat}", method = RequestMethod.PUT)
	public FactureEcheance updateFactureEcheance(@PathVariable("numContrat") final Long numContrat,
			@RequestBody FactureEcheance factureEcheance) {
		return factureService.updateFactureEcheance(numContrat, factureEcheance);
	}

	@PostMapping("/exportPiece")
	@ResponseBody
	public void exportPiece(HttpServletResponse response, @RequestBody String fullPath) {

		// URI uri =new
		// URL("file://130.24.31.12//attachments//SDS_AV%20N1_CT%20N-MUIS-SDS%2001-180001.pdf").toURI();
		System.out.println("fulpath " + fullPath.replace("\\", "/").replace(" ", "%20"));
		try {
			URI uri = new URL("file:" + fullPath.replace("\\", "/").replace(" ", "%20")).toURI();
			Resource file = new UrlResource(uri);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=\"" + file.getFilename() + "\"");

			OutputStream out;
			out = response.getOutputStream();

			FileInputStream in = new FileInputStream(file.getFile());

			// copy from in to out
			IOUtils.copy(in, out);

			out.close();
			in.close();

		} catch (Exception e) {
			response.setStatus(404);
			response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE);
			e.printStackTrace();
		}
	}

}
