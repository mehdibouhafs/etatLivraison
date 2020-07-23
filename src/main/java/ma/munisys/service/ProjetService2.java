package ma.munisys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.munisys.dao.ProjetRepository;
import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.Projet;

@Service
public class ProjetService2 implements EtatProjetService2 {
	
	@Autowired
	ProjetRepository projetRepository;
	
	public ProjetService2() {
		
	}
	
	
	
	public Projet declotureProjet(Projet c) {
		// TODO Auto-generated method stub
		
		
		//int i = projetRepository.updateStatutProjet(false, false, true, p.getCodeProjet());
		
		////System.out.println("i " + i);
		
		Projet p =projetRepository.findById(c.getCodeProjet()).get();
		p.setCloture(false);
		p.setDecloturedByUser(true);
		p.setCloturedByUser(false);
		
		
		
		return projetRepository.save(p);
		 
		
	}
	

	public Projet clotureProjet(Projet c) {
		// TODO Auto-generated method stub
		

		//int i = projetRepository.updateStatutProjetMontant(true, true, false, 100.00, 0.00, p.getMontantCmd(), 0.00, p.getCodeProjet());
		
		
		Projet p =projetRepository.findById(c.getCodeProjet()).get();
		
		p.setCloture(true);
		p.setDecloturedByUser(false);
		p.setCloturedByUser(true);
		p.setFacturation(100.00);
		p.setRestAlivrer(0.0);
		p.setLivrerNonFacture(0.0);
		p.setLivreFacturePayer(p.getMontantCmd());
		return projetRepository.save(p);
		//projetRepository.flush();
		////System.out.println("cloture projet " + c.isCloture());
		//return c;
	
	}

}
