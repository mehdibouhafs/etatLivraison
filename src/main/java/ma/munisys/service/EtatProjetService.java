package ma.munisys.service;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.Header;
import ma.munisys.entities.Projet;



public interface EtatProjetService {
		
	public  Map<String,String>  importInfoFournisseurFromSAP();
	
	public Collection<Projet> getProjetsByPredicate(Long idEtatProjet,
			Boolean cloturer,
			 String bu1, String bu2,
			 String statut,
			 String commercial,String chefProjet,String client,String affectationChefProjet);
	//public void checkIfProjetClotured(Projet projet);
	
	public Page<Projet> getProjetsFromEtatProjet(Boolean cloture,int page,int size);
	
	public Collection<Projet> getProjetFromEtatProjet(Boolean cloture,String bu1,String bu2);
	
	public Collection<Projet> getProjetFromEtatProjet(Boolean cloture,String bu1,String bu2,String statut);
	
	public Collection<Projet> getAllProjets();
	
	public Set<Projet> getProjetsFromInputFile(String fileName);
	
	public void addOrUpdateEtatProjet(EtatProjet etatProjet);
	
	public void addOrUpdateProjet(EtatProjet etatProjet,Projet projet);
	
	public Projet findProjetFromEtatProjet(EtatProjet etatProjet,String codeProjet);
	
	
	public void deleteHeaderFromEtatProjet(Long idEtatProjet, Header header);
	
	public Projet updateProjet(String idProjet, Projet projet);
	
	public Projet declotureProjet(Projet idProjet);
	
	public Projet clotureProjet(Projet idProjet);
	
	public List<Projet> findAllProjetsByDateSup(Boolean cloturer, Date dateCmd);
	
	public List<Projet> findAllProjetsByDateInf(Boolean cloturer, Date dateCmd);
	
	
	public Double getTotalLnf();
	
	
	public Double getTotalRal();
	
	public Double getTotalRalPlusLnfBeforeSevenMonth();
	
	public List<Projet> findAllProjet();
	
	public EtatProjet getEtatProjet();
	
	public Collection<Projet> getProjets(Boolean cloturer);
	
	public void updateProjetsFromSAp(final EtatProjet newEtatProjet);
	
	public  void loadProjetsFromSap();
	
	public Collection<Projet> getProjetsByBu( Boolean cloturer,String bu1,String bu2);

	
	public Collection<Projet> getProjetsByStatut( Boolean cloturer,String statut);

	
	public Collection<Projet> getProjetsByChefDeProjetIsNull( Boolean cloturer);
	
	
	public Collection<Projet> getProjetsByChefDeProjetNotNull( Boolean cloturer);


	
	public Collection<Projet> getProjetsByBuAndStatut( Boolean cloturer,String bu1,String bu2,String statut1);
	
	
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndBu( Boolean cloturer,String bu1,String bu2);
	

	
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndBu( Boolean cloturer,  String bu,String bu2);
	
	 
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndStatut( Boolean cloturer,String statut1);
	
	
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndStatut( Boolean cloturer,String statut1);
	
	
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndBuAndStatut( Boolean cloturer,String bu1,String bu2,String statut);
	
	
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndBuAndStatut( Boolean cloturer,String bu1,String bu2,String statut1);
	

	public Collection<Projet> getProjetsByBuAndCommercial(Boolean cloturer, String bu1, String bu2, String commercial) ;
	

	public Collection<Projet> getProjetsByBuAndChefProjet(Boolean cloturer, String bu1, String bu2, String chefProjet) ;
	

	public Collection<Projet> getProjetsByStatutAndCommercial(Boolean cloturer, String statut, String commercial) ;
	

	public Collection<Projet> getProjetsByStatutAndChefProjet(Boolean cloturer, String statut, String chefProjet) ;
		

	public Collection<Projet> getProjetsByBuAndStatutAndCommercial(Boolean cloturer, String bu1, String bu2,
			String statut, String commercial) ;

	public Collection<Projet> getProjetsByBuAndStatutAndChefProjet(Boolean cloturer, String bu1, String bu2,
			String statut, String chefProjet) ;

	public Collection<Projet> getAllProjetsByCommercialOrChefProjet(Boolean cloturer, String commercialOrChefProjet) ;

	public List<String> getDistinctClient();
	
	
	public List<String> getDistinctCommercial();
	
	
	public List<String> getDistinctChefProjet();
	


}
