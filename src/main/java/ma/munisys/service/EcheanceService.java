package ma.munisys.service;


import java.util.Collection;

import org.springframework.data.domain.Page;

import ma.munisys.entities.Echeance;



public interface EcheanceService {
	
	
	
	
	public Echeance updateEcheance(Long id, Long idCommentaire);
	
	
	public int addNewContratModel(Long numContrat, Echeance e);
	
	public void deleteEcheance(Long idEcheance);
	
	public Collection<Echeance> getAllEcheancesFromContrat(Long numContrat);
	
	public Page<Echeance> getEcheance(Long numContrat ,int page,int size,String sortBy,String sortType);

	
	public Page<Echeance> getEcheanceNotLinked(Long numContrat ,int page,int size,String sortBy,String sortType);

	public Page<Echeance> getEcheancesNotLinked(String date,int page,int size,String sortBy,String sortType);

	public Page<Echeance> getEcheanceLinked(Long numContrat ,int page,int size,String sortBy,String sortType);


	public Echeance addNewEcheanceByUser(Long numContrat, Echeance echeance);

	
	
	
	

}
