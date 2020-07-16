package ma.munisys.web;

import java.util.Collection;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ma.munisys.dao.FournisseuRepository;
import ma.munisys.dto.Analyse;
import ma.munisys.entities.Commentaire;
import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.Fournisseur;
import ma.munisys.entities.Projet;
import ma.munisys.entities.Reunion;
import ma.munisys.service.EtatProjetService;
import ma.munisys.service.EtatProjetService2;
import ma.munisys.service.ReunionService;

@RestController
@CrossOrigin(origins = "*")
public class EtatProjetController {
	
	private static final Logger LOGGER = LogManager.getLogger(EtatProjetController.class);
	

	@Autowired
	private EtatProjetService etatProjetService;
	
	@Autowired
	private EtatProjetService2 etatProjetService2;
	
	@Autowired
	private FournisseuRepository fournisseuRepository;

	@RequestMapping(value="/getDistinctClientProjet",method=RequestMethod.GET)
	public List<String> getDistinctClient() {
		return etatProjetService.getDistinctClient();
	}

	@RequestMapping(value="/getDistinctCommercialProjet",method=RequestMethod.GET)
	public List<String> getDistinctCommercial() {
		return etatProjetService.getDistinctCommercial();
	}

	@RequestMapping(value="/getDistinctChefProjetProjet",method=RequestMethod.GET)
	public List<String> getDistinctChefProjet() {
		return etatProjetService.getDistinctChefProjet();
	}


	@RequestMapping(value = "/getProjetsByBu", method = RequestMethod.GET)
	public Collection<Projet> getProjetsByBu(Boolean cloturer, @RequestParam(name = "bu1") String bu1,
			@RequestParam(name = "bu2") String bu2) {
		return etatProjetService.getProjetsByBu(cloturer, bu1, bu2);
	}
	

	@RequestMapping(value = "/getProjetsByStatut", method = RequestMethod.GET)
	public Collection<Projet> getProjetsByStatut(
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer,
			@RequestParam(name = "statut") String statut) {
		return etatProjetService.getProjetsByStatut(cloturer, statut);
	}

	@RequestMapping(value = "/getProjetsByChefDeProjetIsNull", method = RequestMethod.GET)
	public Collection<Projet> getProjetsByChefDeProjetIsNull(
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer) {
		return etatProjetService.getProjetsByChefDeProjetIsNull(cloturer);
	}

	@RequestMapping(value = "/getProjetsByChefDeProjetNotNull", method = RequestMethod.GET)
	public Collection<Projet> getProjetsByChefDeProjetNotNull(
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer) {
		return etatProjetService.getProjetsByChefDeProjetNotNull(cloturer);
	}

	@RequestMapping(value = "/getProjetsByBuAndStatut", method = RequestMethod.GET)
	public Collection<Projet> getProjetsByBuAndStatut(
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer,
			@RequestParam(name = "bu1") String bu1, @RequestParam(name = "bu2") String bu2,
			@RequestParam(name = "statut1") String statut1) {
		return etatProjetService.getProjetsByBuAndStatut(cloturer, bu1, bu2, statut1);
	}

	@RequestMapping(value = "/getProjetsByChefDeProjetNotNullAndBu", method = RequestMethod.GET)
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndBu(
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer,
			@RequestParam(name = "bu1") String bu1, @RequestParam(name = "bu2") String bu2) {
		return etatProjetService.getProjetsByChefDeProjetNotNullAndBu(cloturer, bu1, bu2);
	}

	@RequestMapping(value = "/getProjetsByChefDeProjetIsNullAndBu", method = RequestMethod.GET)
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndBu(
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer,
			@RequestParam(name = "bu1") String bu, @RequestParam(name = "bu2") String bu2) {
		return etatProjetService.getProjetsByChefDeProjetIsNullAndBu(cloturer, bu, bu2);
	}

	@RequestMapping(value = "/getProjetsByChefDeProjetIsNullAndStatut", method = RequestMethod.GET)
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndStatut(
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer,
			@RequestParam(name = "statut") String statut1) {
		return etatProjetService.getProjetsByChefDeProjetIsNullAndStatut(cloturer, statut1);
	}

	@RequestMapping(value = "/getProjetsByChefDeProjetNotNullAndStatut", method = RequestMethod.GET)
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndStatut(
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer,
			@RequestParam(name = "statut") String statut1) {
		return etatProjetService.getProjetsByChefDeProjetNotNullAndStatut(cloturer, statut1);
	}

	@RequestMapping(value = "/getProjectsByChefProjetNotNull", method = RequestMethod.GET)
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndBuAndStatut(
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer,
			@RequestParam(name = "bu1") String bu1, @RequestParam(name = "bu2") String bu2,
			@RequestParam(name = "statut1") String statut1) {
		return etatProjetService.getProjetsByChefDeProjetNotNullAndBuAndStatut(cloturer, bu1, bu2, statut1);
	}

	@RequestMapping(value = "/getProjectsByChefProjetNull", method = RequestMethod.GET)
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndBuAndStatut(
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer,
			@RequestParam(name = "bu1") String bu1, @RequestParam(name = "bu2") String bu2,
			@RequestParam(name = "statut1") String statut1) {
		return etatProjetService.getProjetsByChefDeProjetIsNullAndBuAndStatut(cloturer, bu1, bu2, statut1);
	}

	@RequestMapping(value = "/getEtatProjet", method = RequestMethod.GET)
	public EtatProjet getEtatProjet() {
		return etatProjetService.getEtatProjet();
	}

	@RequestMapping(value = "/getStatistics", method = RequestMethod.GET)
	public Analyse getStatistics() {
		Analyse analyse = new Analyse();
		analyse.setTotalLnf(etatProjetService.getTotalLnf());
		analyse.setTotalRal(etatProjetService.getTotalRal());
		analyse.setTotalLnfRalSevenMonth(etatProjetService.getTotalRalPlusLnfBeforeSevenMonth());
		return analyse;
	}

	@Autowired
	private ReunionService reunionService;

	@RequestMapping(value = "/getProjets", method = RequestMethod.GET)
	public Page<Projet> getProjets(@RequestParam(name = "idEtatProjet", defaultValue = "1") Long idEtatProjet,
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		return etatProjetService.getProjetsFromEtatProjet(cloturer, page, size);
	}

	@RequestMapping(value = "/getProjects", method = RequestMethod.GET)
	public Collection<Projet> getProjetsWithoutPagination(
			@RequestParam(name = "idEtatProjet", defaultValue = "1") Long idEtatProjet,
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer,
			@RequestParam(name = "bu1") String bu1, @RequestParam(name = "bu2") String bu2) {

		if (bu1.equals("undefined")) {
			return etatProjetService.getProjets(cloturer);
		} else {
			return etatProjetService.getProjetFromEtatProjet(cloturer, bu1, bu2);
		}

	}

	@RequestMapping(value = "/getProjectsWithStatut", method = RequestMethod.GET)
	public Collection<Projet> getProjetsWithoutPagination(
			@RequestParam(name = "idEtatProjet", defaultValue = "1") Long idEtatProjet,
			@RequestParam(name = "cloturer", defaultValue = "false") Boolean cloturer,
			@RequestParam(name = "bu1") String bu1, @RequestParam(name = "bu2") String bu2,
			@RequestParam(name = "statut") String statut, 
			@RequestParam(name = "commercial") String commercial,@RequestParam(name = "client") String client,@RequestParam(name = "chefProjet") String chefProjet
			,@RequestParam(name="affectationChefProjet") String affectationChefProjet) {
		Collection<Projet> projets = null;
		
		return this.etatProjetService.getProjetsByPredicate(idEtatProjet, cloturer, bu1, bu2, statut, commercial, chefProjet, client,affectationChefProjet);
		

		/*if (!commercialOrChefProjet.equals("undefined")) {
			if (!bu1.equals("undefined") && !statut.equals("undefined")) {
				projets = etatProjetService.getProjetsByBuAndStatutAndCommercial(cloturer, bu1, bu2, statut,
						commercialOrChefProjet);
			} else {
				if (!bu1.equals("undefined") && statut.equals("undefined")) {
					projets = etatProjetService.getProjetsByBuAndCommercial(cloturer, bu1, bu2, commercialOrChefProjet);
				} else {
					if (bu1.equals("undefined") && !statut.equals("undefined")) {
						projets = etatProjetService.getProjetsByStatutAndCommercial(cloturer, statut,
								commercialOrChefProjet);
					}else {
						projets = etatProjetService.getAllProjetsByCommercialOrChefProjet(cloturer, commercialOrChefProjet);
					}
				}
			}
		} else {

			// search by bu and statut and exist chef projet
			if (!bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("true")) {
				projets = etatProjetService.getProjetsByChefDeProjetIsNullAndBuAndStatut(cloturer, bu1, bu2, statut);
			} else {
				// search by bu and statut and not exist chef projet
				if (!bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("false")) {
					projets = etatProjetService.getProjetsByChefDeProjetNotNullAndBuAndStatut(cloturer, bu1, bu2,
							statut);
				} else {
					// search by bu and chef projet
					if (!bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("true")) {
						projets = etatProjetService.getProjetsByChefDeProjetIsNullAndBu(cloturer, bu1, bu2);
					} else {
						// search by bu and no chef projet
						if (!bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("false")) {
							projets = etatProjetService.getProjetsByChefDeProjetNotNullAndBu(cloturer, bu1, bu2);
						} else {

							// searche by statut and chef projet
							if (bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("true")) {
								projets = etatProjetService.getProjetsByChefDeProjetIsNullAndStatut(cloturer, statut);
							} else {
								// searche by statut and no chef projet
								if (bu1.equals("undefined") && !statut.equals("undefined")
										&& chefProjet.equals("false")) {
									projets = etatProjetService.getProjetsByChefDeProjetNotNullAndStatut(cloturer,
											statut);
								} else {
									// search by bu an statut
									if (!bu1.equals("undefined") && !statut.equals("undefined")
											&& chefProjet.equals("undefined")) {
										projets = etatProjetService.getProjetsByBuAndStatut(cloturer, bu1, bu2, statut);
									} else {
										// search by bu
										if (!bu1.equals("undefined") && statut.equals("undefined")
												&& chefProjet.equals("undefined")) {
											projets = etatProjetService.getProjetsByBu(cloturer, bu1, bu2);
										} else {
											// search by statut
											if (bu1.equals("undefined") && !statut.equals("undefined")
													&& chefProjet.equals("undefined")) {
												projets = etatProjetService.getProjetsByStatut(cloturer, statut);
											} else {
												// search by chefProjet
												if (bu1.equals("undefined") && statut.equals("undefined")
														&& chefProjet.equals("true")) {
													projets = etatProjetService
															.getProjetsByChefDeProjetIsNull(cloturer);
												} else {
													// search by not chef projet
													if (bu1.equals("undefined") && statut.equals("undefined")
															&& chefProjet.equals("false")) {
														projets = etatProjetService
																.getProjetsByChefDeProjetNotNull(cloturer);
													} else {
														// search by all
														if (bu1.equals("undefined") && statut.equals("undefined")
																&& chefProjet.equals("undefined")) {
															projets = etatProjetService.getProjets(cloturer);
														}
													}
												}
											}
										}
									}

								}

							}

						}
					}

				}

			}
		}*/
		
	

	}

	/*
	 * if(bu1.equals("undefined") ) { return etatProjetService.getProjets(cloturer);
	 * }else { return etatProjetService.getProjetFromEtatProjet(cloturer,bu1,bu2); }
	 * /* if(bu1.equals("undefined") && statut1.equals("undefined")) { return
	 * etatProjetService.getProjets(cloturer); }else { return
	 * etatProjetService.getProjetFromEtatProjet(cloturer,bu1,bu2,statut1,statut2);
	 * }
	 */

	@RequestMapping(value = "/projets", method = RequestMethod.PUT)
	public Projet updateProjets(@RequestBody Projet projet,Authentication authentication) {
		LOGGER.info("Updatating projet " + projet.getCodeProjet() + " by "+ authentication.getName());
		EtatProjet etatProjet = new EtatProjet();
		etatProjet.setId(1L);
		projet.setEtatProjet(etatProjet);

		for (Commentaire c : projet.getCommentaires()) {
			c.setProjet(projet);
		}

		return etatProjetService.updateProjet(projet.getCodeProjet(), projet);
	}
	
	@RequestMapping(value = "/declotureProjet", method = RequestMethod.PUT)
	public Projet declotureProjet(@RequestBody Projet projet,Authentication authentication) {
		
		return etatProjetService2.declotureProjet(projet );
	}
	
	@RequestMapping(value = "/clotureProjet", method = RequestMethod.PUT)
	public Projet clotureProjet(@RequestBody Projet projet,Authentication authentication) {
		
		return etatProjetService2.clotureProjet(projet );
	}
	
	
	@RequestMapping(value = "/updateProjetFromSAP", method = RequestMethod.POST)
	public Projet clotureProjet(@RequestBody String codeProjet,Authentication authentication) {
		
		return etatProjetService.loadSingleProjetFromSap(codeProjet);
	}

	@RequestMapping(value = "/reunions", method = RequestMethod.GET)
	public Collection<Reunion> getAllReunions(Authentication authentication) {
		return reunionService.getAllReunions();
	}
	
	@RequestMapping(value = "/fournisseurs", method = RequestMethod.GET)
	public Collection<Fournisseur> getAllFournisseurs() {
		return fournisseuRepository.getAllFournisseurs();
	}

	@RequestMapping(value = "/reunions", method = RequestMethod.POST)
	public Reunion addReunion(@RequestBody Reunion reunion) {
		return reunionService.addReunion(reunion);
	}

	@RequestMapping(value = "/reunions/{idReunion}", method = RequestMethod.DELETE)
	public void deleteReunion(@PathVariable("idReunion") String idReunion,Authentication authentication) {
		LOGGER.info("Request for deleting reunion  by" +authentication.getName() );
		Long idReunion2 = Long.parseLong(idReunion);
		reunionService.deleteReunion(idReunion2);
	}

	@RequestMapping(value = "/reunions/{idReunion}", method = RequestMethod.PUT)
	public Reunion modifierReunion(@PathVariable("idReunion") String idReunion, @RequestBody Reunion reunion, Authentication authentication) {
		LOGGER.info("Request for updating reunion  by" +authentication.getName() );
		Long idReunion2 = Long.parseLong(idReunion);
		return reunionService.modifierReunion(idReunion2, reunion);
	}

	@RequestMapping(value = "/refreshProjets", method = RequestMethod.GET)
	public String refreshProjects(Authentication authentication) {
		LOGGER.info("Request for refresh projects sap  by" +authentication.getName() );
		etatProjetService.loadProjetsFromSap();
		return "{'statut':'ok'}";
	}

	public FournisseuRepository getFournisseuRepository() {
		return fournisseuRepository;
	}

	public void setFournisseuRepository(FournisseuRepository fournisseuRepository) {
		this.fournisseuRepository = fournisseuRepository;
	}
	
	
	
	

}
