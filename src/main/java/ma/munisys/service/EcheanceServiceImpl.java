package ma.munisys.service;

import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ma.munisys.dao.EcheanceRepository;
import ma.munisys.dao.FactureEcheanceRepository;
import ma.munisys.entities.CommentaireEcheance;
import ma.munisys.entities.Contrat;
import ma.munisys.entities.Echeance;
import ma.munisys.entities.FactureEcheance;



@Service
public class EcheanceServiceImpl implements EcheanceService {
	
	private static final Logger LOGGER = LogManager.getLogger(EcheanceServiceImpl.class);
	
	@Autowired
	private EcheanceRepository echeanceRepository;
	
	@Autowired
	private FactureEcheanceRepository factureEcheanceRepository;

	public EcheanceServiceImpl() {
		// TODO Auto-generated constructor stub
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
	public Echeance editEcheance(Long numContrat,Echeance e) {
		Contrat c = new Contrat();
		c.setNumContrat(numContrat);
		e.setContrat(c);
		e.setAddedByUser(true);
		return echeanceRepository.save(e);
	}

	@Override
	public void deleteEcheance(Long idEcheance) {
		
		Collection<FactureEcheance> factureEcheances = factureEcheanceRepository.getFactureEcheance(idEcheance);
		
		for(FactureEcheance fe:factureEcheances) {
			fe.setEcheance(null);
			factureEcheanceRepository.save(fe);
		}
		
		Echeance e = echeanceRepository.findById(idEcheance).orElse(null);
		
		if(e!=null) {
			e.setCloture(true);
			e.setDeletedByUser(true);
			echeanceRepository.save(e);
		}
		
		
	}
	
	
	
	

	

	

}
