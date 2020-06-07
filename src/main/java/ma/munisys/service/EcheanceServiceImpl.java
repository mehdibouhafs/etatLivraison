package ma.munisys.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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

import ma.munisys.dao.ContratModelRepository;
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
	private ContratModelRepository contratModelRepository;

	public EcheanceServiceImpl() {
		
	}

	@Override
	public Echeance updateEcheance(Long id, Long idCommentaire) {
		LOGGER.info("add commentaire to echeance id " + id);
		Echeance c = echeanceRepository.getOne(id);
		
		if(idCommentaire==0) {
			c.setCommentaire(null);;
		}else{
			c.setCommentaire(new CommentaireEcheance(idCommentaire));
		}
		return echeanceRepository.save(c);
	}

	@Override
	public int addNewContratModel(Long numContrat,Echeance e) {
		int i=0;
		Contrat c = new Contrat();
		c.setNumContrat(numContrat);
		e.setContrat(c);
		
		ContratModel cm = new ContratModel();
		cm.setDu(e.getDu());
		cm.setAu(e.getAu());
		cm.setPeriodeFacturation(e.getPeriodeFacturation());
		cm.setPeriodeFacturationLabel(e.getPeriodeFacturation().toString());
		cm.setOccurenceFacturation(e.getOccurenceFacturation());
		cm.setOccurenceFacturationLabel(e.getOccurenceFacturation().toString());
		cm.setMontant(e.getMontant());
		cm.setMontantPrevisionel(e.getMontantPrevision());
		cm.setContrat(c);
		
		
		Collection<ContratModel> contratsModels = contratModelRepository.getAllContratModel(numContrat);
		boolean modelExist=false;
		for(ContratModel cm1:contratsModels) {
			if(cm1.equals(cm)) {
				modelExist=true;
				break;
			}
		}
		
		if(!modelExist) {
		
		ContratModel cm1 =contratModelRepository.save(cm);
		
		List<Echeance> echeances = cm1.generateEcheanceModele();
		
		for(Echeance e1 : echeances) {
			e1.calculMontantFacture();
		}
		
		echeanceRepository.saveAll(echeances);
		
		factureService.loadFactureFromSapByContrat(numContrat);
		i=1;
		}else {
			throw new RuntimeException("Echeance existe dejà !");
		}
		
		
		return i;
		
	}

	@Override
	public void deleteEcheance(Long idEcheance) {
		
		Collection<FactureEcheance> factureEcheances = factureEcheanceRepository.getFactureEcheance(idEcheance);
		
		
		Collection<FactureEcheance> newFactureEcheances = new ArrayList<FactureEcheance>(factureEcheances);
		
		factureEcheanceRepository.deleteAll(factureEcheances);
		
		
		for(FactureEcheance fe:newFactureEcheances) {
			fe.setId(fe.getFacture().getNumFacture()+"/"+ fe.getContrat().getNumContrat()+"");
			fe.setEcheance(null);
			fe.setAffectedByUser(false);
			factureEcheanceRepository.save(fe);
		}
		
		Echeance e = echeanceRepository.findById(idEcheance).orElse(null);
		
		if(e!=null) {
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
		return echeanceRepository.getAllEcheancesFromContrat(numContrat,year);
	}

	@Override
	public Page<Echeance> getEcheance(Long numContrat, int page, int size,String sortBy,String sortType) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		
		
		if (sortBy != null) {

			if ("asc".equals(sortType)) {
				return echeanceRepository.getEcheance(numContrat,year, PageRequest.of(page, size,Sort.by(sortBy).ascending()));

			} else {
				return echeanceRepository.getEcheance(numContrat,year, PageRequest.of(page, size,Sort.by(sortBy).descending()));
			}

		} else {
		
		return echeanceRepository.getEcheance(numContrat,year, PageRequest.of(page, size));
		}
	}

	@Override
	public Echeance addNewEcheanceByUser(Long numContrat, Echeance echeance) {
		Contrat c = new Contrat();
		c.setNumContrat(numContrat);
		echeance.setContrat(c);
		echeance.setCloture(false);
		echeance.setAddedByUser(true);
		
		if(echeance.getMontant()!=null) {
			echeance.setMontantRestFacture(echeance.getMontant());
		}
		if(echeance.getMontantPrevision()!=null) {
			echeance.setMontantPrevision(echeance.getMontantPrevision());
		}
		
		return echeanceRepository.save(echeance);
	}
	
	
	
	

	

	

}
