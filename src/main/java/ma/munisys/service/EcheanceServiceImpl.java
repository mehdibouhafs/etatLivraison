package ma.munisys.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ma.munisys.dao.EcheanceRepository;
import ma.munisys.entities.Echeance;



@Service
public class EcheanceServiceImpl implements EcheanceService {
	
	private static final Logger LOGGER = LogManager.getLogger(EcheanceServiceImpl.class);
	
	@Autowired
	private EcheanceRepository echeanceRepository;

	public EcheanceServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Echeance updateEcheance(Long id, String commentaire) {
		LOGGER.info("add commentaire to echeance id " + id);
		Echeance c = echeanceRepository.getOne(id);
		c.setCommentaire(commentaire);
		return echeanceRepository.save(c);
	}
	
	
	
	

	

	

}
