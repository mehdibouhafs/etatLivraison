package ma.munisys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ma.munisys.dao.EtatProjetRepository;
import ma.munisys.entities.EtatProjet;


@Service
@Transactional // spring pas javax
public class EtatProjetServiceImpl implements EtatProjetService {
	
	
	@Autowired
	private EtatProjetRepository etatProjetRepository;
	
	
	
	@Override
	public EtatProjet saveEtatProjet(EtatProjet etatProjet) {
		// TODO Auto-generated method stub
		return etatProjetRepository.save(etatProjet);
	}



	@Override
	public EtatProjet getEtatProjet(Long id) {
		// TODO Auto-generated method stub
		return etatProjetRepository.getOne(id);
	}

	

	
	

}
