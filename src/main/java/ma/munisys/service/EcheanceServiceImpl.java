package ma.munisys.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.text.SimpleDateFormat;

import ma.munisys.dao.ContratModelRepository;
import ma.munisys.dao.ContratRepository;
import ma.munisys.dao.EcheanceRepository;
import ma.munisys.dao.FactureEcheanceRepository;
import ma.munisys.entities.CommentaireEcheance;
import ma.munisys.entities.Contrat;
import ma.munisys.entities.ContratModel;
import ma.munisys.entities.Echeance;
import ma.munisys.entities.FactureEcheance;

@Service
public class EcheanceServiceImpl implements EcheanceService {

	private static final Logger LOGGER = LogManager.getLogger(EcheanceServiceImpl.class);

	@Autowired
	private EcheanceRepository echeanceRepository;

	@Autowired
	private FactureEcheanceRepository factureEcheanceRepository;

	@Autowired
	private FactureService factureService;
	
	@Autowired
	private ContratRepository contratRepository;

	@Autowired
	private ContratModelRepository contratModelRepository;

	public EcheanceServiceImpl() {

	}

	@Override
	public Echeance updateEcheance(Long id, Long idCommentaire) {
		LOGGER.info("add commentaire to echeance id " + id);
		Echeance c = echeanceRepository.getOne(id);

		if (idCommentaire == 0) {
			c.setCommentaire(null);
			;
		} else {
			c.setCommentaire(new CommentaireEcheance(idCommentaire));
		}
		return echeanceRepository.save(c);
	}

	@Override
	public int addNewContratModel(Long numContrat, Echeance e) {
		int i = 0;
		Contrat c = new Contrat();
		c.setNumContrat(numContrat);
		e.setContrat(c);

		ContratModel cm = new ContratModel();
		cm.setDu(e.getDu());
		cm.setAu(e.getAu());
		cm.setCloture(false);
		cm.setName(e.getNomModele());
		cm.setPeriodeFacturation(e.getPeriodeFacturation());
		if (e.getPeriodeFacturation() != null)
			cm.setPeriodeFacturationLabel(e.getPeriodeFacturation().toString());
		if (e.getOccurenceFacturation() != null) {
			cm.setOccurenceFacturation(e.getOccurenceFacturation());
			cm.setOccurenceFacturationLabel(e.getOccurenceFacturation().toString());
		}
		cm.setMontant(e.getMontant());
		cm.setMontantPrevisionel(e.getMontantPrevision());
		cm.setContrat(c);

		Collection<ContratModel> contratsModels = contratModelRepository.getAllContratModel(numContrat);
		boolean modelExist = false;
		for (ContratModel cm1 : contratsModels) {
			if (cm1.equals(cm)) {
				modelExist = true;
				break;
			}
		}

		if (!modelExist) {

			ContratModel cm1 = contratModelRepository.save(cm);

			List<Echeance> ecs = cm1.generateEcheanceModele();
			
			List<Echeance> newEcheances =new ArrayList<Echeance>();

			Collection<Echeance> echeances = echeanceRepository.getAllEcheancesFromContrat(numContrat);

			if (ecs != null && !ecs.isEmpty()) {
				
				for (Echeance ec : ecs) {
					boolean add =true;
					for (Echeance e1 : echeances) {
						
						if (e1.getDu() != null && e1.getAu() != null && ec.getDu() != null && ec.getDu() != null
								&& e1.getDu().compareTo(ec.getDu()) == 0 && e1.getAu().compareTo(ec.getAu()) == 0) {
							if ((e1.getMontant() != null && ec.getMontant() != null
									&& Double.compare(e1.getMontant(), ec.getMontant()) == 0)
									|| (e1.getMontant() == null && ec.getMontant() == null)) {

								add = false;
								break;

							}
							
							if ((e1.getMontantPrevision() != null && ec.getMontantPrevision() != null
									&& Double.compare(e1.getMontantPrevision(), ec.getMontantPrevision()) == 0)
									|| (e1.getMontantPrevision() == null && ec.getMontantPrevision() == null)) {
								add = false;
								break;
							}
						}
						
						
					}
					
					if(add) {
						newEcheances.add(ec);
					}
					
				}
				
			}

			echeanceRepository.saveAll(newEcheances);

			factureService.loadFactureFromSapByContrat(numContrat);
			
			Calendar ca= Calendar.getInstance();
			int currentYear =ca.get(Calendar.YEAR);
			
			Contrat contrat = this.contratRepository.findById(numContrat).orElse(null);
			if(contrat!=null) {
				int nbEcheances = this.echeanceRepository.getNbEcheanceAFacturer(numContrat, currentYear);
				contrat.setNbEcheancesNonFactureEnRetard(nbEcheances);
			}
			this.contratRepository.save(contrat);
			i = 1;
		} else {
			throw new RuntimeException("Echeance existe dejà !");
		}

		return i;

	}

	@Override
	public void deleteEcheance(Long idEcheance) {

		Collection<FactureEcheance> factureEcheances = factureEcheanceRepository.getFactureEcheance(idEcheance);

		Collection<FactureEcheance> newFactureEcheances = new ArrayList<FactureEcheance>(factureEcheances);

		factureEcheanceRepository.deleteAll(factureEcheances);

		for (FactureEcheance fe : newFactureEcheances) {
			fe.setId(fe.getFacture().getNumFacture() + "/" + fe.getContrat().getNumContrat() + "");
			fe.setEcheance(null);
			fe.setAffectedByUser(false);
			factureEcheanceRepository.save(fe);
		}

		Echeance e = echeanceRepository.findById(idEcheance).orElse(null);

		if (e != null) {
			e.setCloture(true);
			e.setDeletedByUser(true);
			echeanceRepository.save(e);
		}

	}

	@Override
	public Collection<Echeance> getAllEcheancesFromContrat(Long numContrat) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return echeanceRepository.getAllEcheancesFromContrat(numContrat, year);
	}

	@Override
	public Page<Echeance> getEcheance(Long numContrat, int page, int size, String sortBy, String sortType) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		if (sortBy != null) {

			if ("asc".equals(sortType)) {
				return echeanceRepository.getEcheanceWithoutOrder(numContrat, year,
						PageRequest.of(page, size, Sort.by(sortBy).ascending()));

			} else {
				return echeanceRepository.getEcheanceWithoutOrder(numContrat, year,
						PageRequest.of(page, size, Sort.by(sortBy).descending()));
			}

		} else {

			return echeanceRepository.getEcheance(numContrat, year, PageRequest.of(page, size));
		}
	}

	@Override
	public Echeance addNewEcheanceByUser(Long numContrat, Echeance echeance) {
		Contrat c = new Contrat();
		c.setNumContrat(numContrat);
		echeance.setContrat(c);
		echeance.setCloture(false);
		echeance.setAddedByUser(true);
		echeance.setMontantFacture(0.0);

		if (echeance.getMontant() != null) {
			echeance.setMontantRestFacture(echeance.getMontant());
		}
		if (echeance.getMontantPrevision() != null) {
			echeance.setMontantPrevision(echeance.getMontantPrevision());
		}

		return echeanceRepository.save(echeance);
	}

	@Override
	public Page<Echeance> getEcheanceNotLinked(Long numContrat, int page, int size, String sortBy, String sortType) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		if (sortBy != null) {

			if ("asc".equals(sortType)) {
				return echeanceRepository.getEcheanceNotLinkedWithoutOrder(numContrat, year,
						PageRequest.of(page, size, Sort.by(sortBy).ascending()));

			} else {
				return echeanceRepository.getEcheanceNotLinkedWithoutOrder(numContrat, year,
						PageRequest.of(page, size, Sort.by(sortBy).descending()));
			}

		} else {
			return echeanceRepository.getEcheanceNotLinked(numContrat, year, PageRequest.of(page, size));
		}
	}

	@Override
	public Page<Echeance> getEcheanceLinked(Long numContrat, int page, int size, String sortBy, String sortType) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		if (sortBy != null) {

			if ("asc".equals(sortType)) {
				return echeanceRepository.getEcheanceLinkedWithoutOrder(numContrat, year,
						PageRequest.of(page, size, Sort.by(sortBy).ascending()));

			} else {
				return echeanceRepository.getEcheanceLinkedWithoutOrder(numContrat, year,
						PageRequest.of(page, size, Sort.by(sortBy).descending()));
			}

		} else {
			return echeanceRepository.getEcheanceLinked(numContrat, year, PageRequest.of(page, size));
		}
	}

	@Override
	public Page<Echeance> getEcheancesNotLinked(String date,int page, int size, String sortBy, String sortType) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		
		DateFormat sourceFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
		Date date1 =null;
		
		if(date!=null && !date.isEmpty()) {
			try {
				 date1 = sourceFormat.parse(date);
				
			} catch (ParseException e) {
				date1=null;
			}
		}

		if(date1!=null ) {
			
			
			if (sortBy != null) {

				if ("asc".equals(sortType)) {
					return echeanceRepository.getEcheancesNotLinkedWithoutOrderDateParam( date1,
							PageRequest.of(page, size, Sort.by(sortBy).ascending()));

				} else {
					return echeanceRepository.getEcheancesNotLinkedWithoutOrderDateParam( date1,
							PageRequest.of(page, size, Sort.by(sortBy).descending()));
				}

			} else {
				return echeanceRepository.getEcheancesNotLinkedDateParam( date1, PageRequest.of(page, size));
			}
		}else {
			if (sortBy != null) {

				if ("asc".equals(sortType)) {
					return echeanceRepository.getEcheancesNotLinkedWithoutOrder( year,
							PageRequest.of(page, size, Sort.by(sortBy).ascending()));

				} else {
					return echeanceRepository.getEcheancesNotLinkedWithoutOrder( year,
							PageRequest.of(page, size, Sort.by(sortBy).descending()));
				}

			} else {
				return echeanceRepository.getEcheancesNotLinked( year, PageRequest.of(page, size));
			}
		}
		
		
	}

	

}
